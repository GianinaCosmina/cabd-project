import { Component, OnInit } from '@angular/core';
import { HomepageService } from '../services/homepage-service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  orders: any[] = [];
  isEditModalOpen = false;
  isCreateMode = false;
  currentOrder: {
    orderId?: number,
    itemId?: number,
    customerId?: number,
    quantity?: number,
    comments?: string
    } = {
    orderId: undefined,
    itemId: undefined,
    customerId: undefined,
    quantity: undefined,
    comments: undefined
  };
  errorMessage?: string = undefined;

  constructor(private homepageService: HomepageService) {}

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    this.homepageService.getOrders().subscribe((data) => {
      this.orders = data; 
      this.errorMessage = undefined;
    }, (error: string) => (this.errorMessage = error));
  }

  openEditModal(order: any): void {
    this.currentOrder = { ...order };
    this.isEditModalOpen = true;
    this.errorMessage = undefined;
    this.isCreateMode = false;
  }

  // Close the edit modal
  closeEditModal(): void {
    this.isEditModalOpen = false;
    this.currentOrder = {
      orderId: undefined,
      itemId: undefined,
      customerId: undefined,
      quantity: undefined,
      comments: undefined
    };
    this.errorMessage = undefined;
  }

  // Delete an order
  deleteOrder(orderId: number): void {
    this.homepageService.deleteOrder(orderId).subscribe(() => {
      this.fetchData();
    }, (error: string) => (this.errorMessage = error));
  }

    // Open the modal in create mode
  openCreateModal(): void {
    this.currentOrder = { quantity: undefined, comments: undefined, itemId: undefined, orderId: undefined, customerId: undefined }; // Initialize with empty fields
    this.isCreateMode = true; // Set to create mode
    this.isEditModalOpen = true;
  }

  saveOrder(): void {
    if (this.isCreateMode) {
      // Handle creating a new order
      this.homepageService.createOrder({
        orderId: this.currentOrder.orderId,
        quantity: this.currentOrder.quantity,
        comments: this.currentOrder.comments,
        itemId: this.currentOrder.itemId,
        customerId: this.currentOrder.customerId
        })
      .subscribe(() => {
        this.fetchData(); // Refresh the orders table
        this.closeEditModal(); // Close the modal
      },
      (error: string) => {
        this.errorMessage = error;
      });
    } else {
      // Handle editing an existing order
      this.homepageService.updateOrder(this.currentOrder.orderId || -1, this.currentOrder).subscribe(() => {
        this.fetchData(); // Refresh the orders table
        this.closeEditModal(); // Close the modal
      }, (error: string) => (this.errorMessage = error));
    }
  }

}
