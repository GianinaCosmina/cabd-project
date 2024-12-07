import { Component, OnInit } from '@angular/core';
import { HomepageService } from '../services/homepage-service';

@Component({
  selector: 'app-customers',
  templateUrl: './customers.component.html',
  styleUrls: ['./customers.component.css']
})
export class CustomersComponent implements OnInit {
  customers: any[] = [];
  isEditModalOpen = false;
  isCreateMode = false;
  currentCustomer: {
    id?: number,
    name?: string,
    address?: string
    } = {
    id: undefined,
    name: undefined,
    address: undefined
  };
  errorMessage?: string = undefined;

  constructor(private homepageService: HomepageService) {}

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    this.homepageService.getCustomers().subscribe((data) => {
      this.customers = data; 
      this.errorMessage = undefined;
    }, (error: string) => (this.errorMessage = error));
  }

  openEditModal(customer: any): void {
    this.currentCustomer = { ...customer };
    this.isEditModalOpen = true;
    this.errorMessage = undefined;
    this.isCreateMode = false;
  }

  // Close the edit modal
  closeEditModal(): void {
    this.isEditModalOpen = false;
    this.currentCustomer = {
      id: undefined,
      name: undefined,
      address: undefined
    };
    this.errorMessage = undefined;
  }

  // Delete an customer
  deleteCustomer(customerId: number): void {
    this.homepageService.deleteCustomer(customerId).subscribe(() => {
      this.fetchData();
    }, (error: string) => (this.errorMessage = error));
  }

    // Open the modal in create mode
  openCreateModal(): void {
    this.currentCustomer = { name: '', address: '' }; // Initialize with empty fields
    this.isCreateMode = true; // Set to create mode
    this.isEditModalOpen = true;
  }

  saveCustomer(): void {
    if (this.isCreateMode) {
      // Handle creating a new customer
      this.homepageService.createCustomer({name: this.currentCustomer.name, address: this.currentCustomer.address})
      .subscribe(() => {
        this.fetchData(); // Refresh the customers table
        this.closeEditModal(); // Close the modal
      },
      (error: string) => {
        this.errorMessage = error;
      });
    } else {
      // Handle editing an existing customer
      this.homepageService.updateCustomer(this.currentCustomer.id || -1, this.currentCustomer).subscribe(() => {
        this.fetchData(); // Refresh the customers table
        this.closeEditModal(); // Close the modal
      }, (error: string) => (this.errorMessage = error));
    }
  }

}
