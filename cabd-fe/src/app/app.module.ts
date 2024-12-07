import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navigation-bar/navigation-bar.component';
import { HttpClientModule } from '@angular/common/http';
import { HomepageComponent } from './homepage/homepage.component';
import { ItemsComponent } from './items/items.component';
import { FormsModule } from '@angular/forms'; 
import { HttpErrorInterceptor } from './services/http-error.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { CustomersComponent } from './customers/customers.component';
import { OrdersComponent } from './orders/orders.component';

@NgModule({
  declarations: [AppComponent, NavbarComponent, HomepageComponent, ItemsComponent, CustomersComponent, OrdersComponent],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true }  // Register interceptor
  ],  bootstrap: [AppComponent],
})
export class AppModule {}
