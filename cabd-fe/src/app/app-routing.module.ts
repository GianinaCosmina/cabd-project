import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { ItemsComponent } from './items/items.component';
import { CustomersComponent } from './customers/customers.component';
import { OrdersComponent } from './orders/orders.component';
import { LongestPricePeriodComponent } from './longest-price-period/longest-price-period.component';
import { PriceDifferenceReportComponent } from './price-difference-report/price-difference-report.component';
import { StateAtTheMomentComponent } from './state-at-the-moment/state-at-the-moment.component';


const routes: Routes = [
  { path: '', redirectTo: 'homepage', pathMatch: 'full' },
  {
    path: 'homepage',
    component: HomepageComponent,
  },
  {
    path: 'items',
    component: ItemsComponent,
  },
  {
    path: 'reports/longest-price-period',
    component: LongestPricePeriodComponent,
  },
  {
    path: 'reports/price-difference-report',
    component: PriceDifferenceReportComponent,
  },
  {
    path: 'reports/state-at-the-moment',
    component: StateAtTheMomentComponent,
  },
  {
    path: "customers",
    component: CustomersComponent,
  },
  {
    path: "orders",
    component: OrdersComponent,
  },
  { path: '**', redirectTo: 'homepage', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
