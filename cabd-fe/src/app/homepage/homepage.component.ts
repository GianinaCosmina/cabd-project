import { Component, OnInit } from '@angular/core';
import { HomepageService } from '../services/homepage-service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent implements OnInit {
  customers: any[] = [];
  items: any[] = [];
  orders: any[] = [];
  errorMessage?: string = undefined;

  constructor(private homepageService: HomepageService) {}

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    this.homepageService.getCustomers().subscribe((data) => (this.customers = data));
    this.homepageService.getItems().subscribe((data) => {
      this.items = data; 
      this.errorMessage = undefined;
    }, (error: string) => (this.errorMessage = error));
    this.homepageService.getOrders().subscribe((data) => (this.orders = data));
  }
}
