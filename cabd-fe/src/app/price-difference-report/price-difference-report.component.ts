import { Component, OnInit } from '@angular/core';
import { HomepageService } from '../services/homepage-service';
import { PriceDifferenceReport } from '../services/model';

@Component({
  selector: 'app-price-difference-report',
  templateUrl: './price-difference-report.component.html',
  styleUrls: ['./price-difference-report.component.css']
})
export class PriceDifferenceReportComponent implements OnInit {

  reports: PriceDifferenceReport[] = [];
  errorMessage?: string = undefined;
  filterItemId: string = '';
  filteredReports: PriceDifferenceReport[] = [];

  constructor(private homepageService: HomepageService) {}

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    this.homepageService.getPriceDifferenceReport().subscribe((data: PriceDifferenceReport[]) => {
      this.reports = data; 
      this.errorMessage = undefined;
      this.filteredReports= data;
    }, (error: string) => (this.errorMessage = error));
  }

  formatTimestamp(timestamp: string): string {
    if (!timestamp) {
      return 'N/A';
    }
    const date = new Date(timestamp);
    return date.toLocaleString(undefined, {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      timeZoneName: 'short'
    });
  }  

  formatPrice(price: number | null): string {
    return price === null ? 'N/A' : `$${price.toFixed(2)}`;
  }

  filterReports(): void {
    const filterValue = this.filterItemId?.trim();
    if (filterValue) {
      this.filteredReports = this.reports.filter((report) =>
        report.itemId.toString() === filterValue
      );
    } else {
      this.filteredReports = this.reports;
    }
  }
}
