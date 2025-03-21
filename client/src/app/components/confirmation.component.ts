import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-confirmation',
  standalone: false,
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.css'
})
export class ConfirmationComponent implements OnInit {
  orderId: string = '';
  paymentId: string = '';
  total: number = 0;
  timestamp: string = '';

  // TODO: Task 5

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['orderId'] && params['paymentId']) {
        this.orderId = params['orderId'];
        this.paymentId = params['paymentId'];
        this.total = params['total'];
        this.timestamp = new Date(+params['timestamp']).toLocaleString();
      } else {
        this.router.navigate(['/']); // Redirect to home if no data
      }
    });
  }
}
