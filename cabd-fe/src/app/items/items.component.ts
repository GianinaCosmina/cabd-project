import { Component, OnInit } from '@angular/core';
import { HomepageService } from '../services/homepage-service';

@Component({
  selector: 'app-items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.css'],
})
export class ItemsComponent implements OnInit {
  items: any[] = [];
  isEditModalOpen = false;
  isCreateMode = false;
  currentItem: {
    id?: number,
    price?: number,
    name?: string,
    description?: string
    } = {
    id: undefined,
    price: undefined,
    name: undefined,
    description: undefined
  };
  errorMessage?: string = undefined;

  constructor(private homepageService: HomepageService) {}

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    this.homepageService.getItems().subscribe((data) => {
      this.items = data; 
      this.errorMessage = undefined;
    }, (error: string) => (this.errorMessage = error));
  }

  openEditModal(item: any): void {
    this.currentItem = { ...item };
    this.isEditModalOpen = true;
    this.errorMessage = undefined;
    this.isCreateMode = false;
  }

  // Close the edit modal
  closeEditModal(): void {
    this.isEditModalOpen = false;
    this.currentItem = {
      id: undefined,
      price: undefined,
      name: undefined,
      description: undefined
    };
    this.errorMessage = undefined;
  }

  // Delete an item
  deleteItem(itemId: number): void {
    this.homepageService.deleteItem(itemId).subscribe(() => {
      this.fetchData();
    }, (error: string) => (this.errorMessage = error));
  }

    // Open the modal in create mode
  openCreateModal(): void {
    this.currentItem = { name: '', description: '', price: undefined }; // Initialize with empty fields
    this.isCreateMode = true; // Set to create mode
    this.isEditModalOpen = true;
  }

  saveItem(): void {
    if (this.isCreateMode) {
      // Handle creating a new item
      this.homepageService.createItem({name: this.currentItem.name, description: this.currentItem.description, price: this.currentItem.price})
      .subscribe(() => {
        this.fetchData(); // Refresh the items table
        this.closeEditModal(); // Close the modal
      },
      (error: string) => {
        this.errorMessage = error;
      });
    } else {
      // Handle editing an existing item
      this.homepageService.updateItem(this.currentItem.id || -1, this.currentItem).subscribe(() => {
        this.fetchData(); // Refresh the items table
        this.closeEditModal(); // Close the modal
      }, (error: string) => (this.errorMessage = error));
    }
  }
}
