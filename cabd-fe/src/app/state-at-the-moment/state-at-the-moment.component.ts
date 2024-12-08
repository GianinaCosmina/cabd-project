import { Component, OnInit } from '@angular/core';
import { HomepageService } from '../services/homepage-service';
import { ItemHistoryRecord } from '../services/model';

@Component({
  selector: 'app-state-at-the-moment',
  templateUrl: './state-at-the-moment.component.html',
  styleUrls: ['./state-at-the-moment.component.css']
})
export class StateAtTheMomentComponent implements OnInit {

  reports: ItemHistoryRecord[] = [];
  errorMessage?: string = undefined;
  filterItemId: string = '';
  filteredReports: ItemHistoryRecord[] = [];
  timestamp: string = '';

  constructor(private homepageService: HomepageService) {}

  ngOnInit(): void {
  }

  fetchData(): void {
    this.homepageService.getStateAtTheMoment(this.formatDateForBackend(this.timestamp)).subscribe((data: ItemHistoryRecord[]) => {
      this.reports = data; 
      this.errorMessage = undefined;
      this.filteredReports= data;
    }, (error: string) => (this.errorMessage = error));
  }

  formatDateForBackend(date: string): string {
    const dateObject = new Date(date);
    console.log(date)
    if (isNaN(dateObject.getTime())) {
      return '';
    }

    const year = dateObject.getFullYear();
    const month = ('0' + (dateObject.getMonth() + 1)).slice(-2);
    const day = ('0' + dateObject.getDate()).slice(-2);
    const hours = ('0' + dateObject.getHours()).slice(-2);
    const minutes = ('0' + dateObject.getMinutes()).slice(-2);
    const seconds = ('0' + dateObject.getSeconds()).slice(-2);
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  }


  formatTimestamp(timestamp: string): string {
    const date = new Date(timestamp);
    return timestamp === null ? 'N/A' : date.toLocaleString();
  }  

  formatPrice(price: number | null): string {
    return price === null ? 'N/A' : `$ ${price.toFixed(2)}`;
  }
}
