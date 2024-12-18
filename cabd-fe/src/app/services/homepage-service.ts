import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, of, throwError  } from 'rxjs';
import { catchError } from 'rxjs/operators';
import {PeriodReport, PriceDifferenceReport, ItemHistoryRecord} from './model';

@Injectable({
  providedIn: 'root',
})
export class HomepageService {
  private baseUrl = 'http://localhost:8080/api'; // Update this based on your backend URL

  constructor(private http: HttpClient) {}

  getCustomers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/customers`);
  }

  getItems(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/items`);
  }

  getOrders(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/orders`);
  }

  updateItem(itemId: number, itemData: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/items/${itemId}`, itemData);
  }

  deleteItem(itemId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/items/${itemId}`);
  }

  createItem(itemData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/items`, itemData, { headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })});
  }

  updateCustomer(customerId: number, customerData: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/customers/${customerId}`, customerData);
  }

  deleteCustomer(customerId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/customers/${customerId}`);
  }

  createCustomer(customerData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/customers`, customerData, { headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })});
  }

  updateOrder(orderId: number, orderData: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/orders`, orderData);
  }

  deleteOrder(orderId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/orders/${orderId}`);
  }

  createOrder(orderData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/orders`, orderData, { headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })});
  }

  getCurrentStateForItem(itemId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/reports/current-state/${itemId}`);
  }

  getLongestPricePeriod(): Observable<PeriodReport[]> {
    return this.http.get<PeriodReport[]>(`${this.baseUrl}/reports/longest-price-period`);
  }

  getPriceDifferenceReport(): Observable<PriceDifferenceReport[]> {
    return this.http.get<PriceDifferenceReport[]>(`${this.baseUrl}/reports/price-difference-report`);
  }

  getStateAtTheMoment(timestamp: string): Observable<ItemHistoryRecord[]> {
    return this.http.get<ItemHistoryRecord[]>(`${this.baseUrl}/reports/state-at-the-moment?timestamp=${encodeURIComponent(timestamp)}`);
  }

}
