import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RestaurantService } from '../restaurant.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-place-order',
  standalone: false,
  templateUrl: './place-order.component.html',
  styleUrl: './place-order.component.css'
})
export class PlaceOrderComponent implements OnInit {

  // TODO: Task 3

  cartItems: any[] = [];
  totalPrice: number = 0;
  orderForm: FormGroup;

  constructor(private router: Router, private restaurantService: RestaurantService) { 
    this.orderForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  ngOnInit(): void {
    const storedCartItems = localStorage.getItem('cartItems');
    if (storedCartItems) {
      this.cartItems = JSON.parse(storedCartItems);
    
    const groupedItems = this.groupItemsById(this.cartItems);
    this.cartItems = groupedItems;

    this.totalPrice = this.cartItems.reduce((total, item) => total + (item.price * item.quantity), 0);
    }
  }
  
  groupItemsById(items: any[]): any[] {
    const grouped: any = {};

    items.forEach(item => {
      if (grouped[item.id]) {
        grouped[item.id].quantity += item.quantity;
      } else {
        grouped[item.id] = {...item};
      }
    });

    return Object.values(grouped);
  }

  confirmOrder(): void {
    console.log("Order confirmation triggered.");
    if (this.orderForm.valid) {
      const orderData = {
        username: this.orderForm.value.username,
        password: this.orderForm.value.password,
        items: this.cartItems.map(item => ({
          id: item.id,
          price: item.price,
          quantity: item.quantity
        }))
      };

      this.restaurantService.placeOrder(orderData).subscribe(
        response => {
          console.log('Order confirmed:', response);
          this.router.navigate(['/confirmation']);
        },
        error => {
          console.error('Error placing order:', error);
          alert(error.error.message || 'An error occurred while placing the order.');
        }
      );
    }
  }


  startOver(): void {
    localStorage.removeItem('cartItems');
    this.router.navigate(['/']);
  }
}
