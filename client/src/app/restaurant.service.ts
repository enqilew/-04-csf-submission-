import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  constructor(private http: HttpClient) { }

  // TODO: Task 2.2
  // You change the method's signature but not the name
  getMenuItems(): Observable<any[]> {
    return this.http.get<any[]>('/api/menu'); 
  }

  // TODO: Task 3.2
  placeOrder(orderData: any): Observable<any> {
    return this.http.post<any>('/api/food_order', orderData);
  }
}
