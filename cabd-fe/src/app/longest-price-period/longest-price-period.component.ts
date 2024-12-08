import { Component, OnInit } from '@angular/core';
import { HomepageService } from '../services/homepage-service';
import { PeriodReport } from '../services/model';

@Component({
  selector: 'app-longest-price-period',
  templateUrl: './longest-price-period.component.html',
  styleUrls: ['./longest-price-period.component.css']
})
export class LongestPricePeriodComponent implements OnInit {
  reports: PeriodReport[] = [];
  errorMessage?: string = undefined;

  constructor(private homepageService: HomepageService) {}

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    this.homepageService.getLongestPricePeriod().subscribe((data: PeriodReport[]) => {
      this.reports = data; 
      this.errorMessage = undefined;
    }, (error: string) => (this.errorMessage = error));
  }

  formatTimestamp(timestamp: string): string {
    return new Date(timestamp).toLocaleString(); // Format timestamp for display
  }

}
