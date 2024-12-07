import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { ItemsComponent } from './items/items.component';
import { CustomersComponent } from './customers/customers.component';
import { OrdersComponent } from './orders/orders.component';

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
  // {
  //   path: "item_history",
  //   loadChildren: () =>
  //     import("./item_history/item_history.module").then((m) => m.itemHistoryModule),
  // },
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
