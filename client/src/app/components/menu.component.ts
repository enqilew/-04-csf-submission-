import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RestaurantService } from '../restaurant.service';

@Component({
  selector: 'app-menu',
  standalone: false,
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit {
  menuItems: any[] = [];
  cartItems: any[] = [];
  totalPrice: number = 0; 
  
  // TODO: Task 2
  constructor(private restaurantService: RestaurantService, private router: Router) { }

  ngOnInit(): void {
    this.restaurantService.getMenuItems().subscribe((data) => {
      this.menuItems = data.sort((a, b) => a.name.localeCompare(b.name));  // Sort items by name
    });
  }

  addItem(item: any): void {
    const existingItem = this.cartItems.find(cartItem => cartItem.id === item.id);

  if (existingItem) {
    existingItem.quantity += 1;
  } else {
    this.cartItems.push({...item, quantity: 1});
  }
  
  this.totalPrice = this.cartItems.reduce((total, item) => total + (item.price * item.quantity), 0);
  }

  removeItem(item: any): void {
    const index = this.cartItems.findIndex(cartItem => cartItem.id === item.id);
  if (index !== -1) {
    // If the quantity is more than 1, just reduce the quantity
    if (this.cartItems[index].quantity > 1) {
      this.cartItems[index].quantity -= 1;
    } else {
      // If the quantity is 1, remove the item from the cart
      this.cartItems.splice(index, 1);
    }
  }

  // Recalculate the total price
  this.totalPrice = this.cartItems.reduce((total, item) => total + (item.price * item.quantity), 0);
  }

  getQuantity(item: any): number {
    const cartItem = this.cartItems.find(cartItem => cartItem.id === item.id);
    return cartItem ? cartItem.quantity : 0;  // Return 0 if item is not found in cart
  }

  //getUniqueItemCount(): number {
  //  const uniqueItems = new Set(this.cartItems.map(item => item.id));  // Create a set of unique item IDs
  //  return uniqueItems.size;  // Return the size of the set (number of unique items)
  //}

  placeOrder(): void {
    if (this.cartItems.length > 0) {
      alert('Order placed!');
      
      localStorage.setItem('cartItems', JSON.stringify(this.cartItems));

      this.router.navigate(['/place-order']);
    }
  }

}
