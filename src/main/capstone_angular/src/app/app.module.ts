import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';
import {MatButtonModule} from "@angular/material/button";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule, MatLabel} from "@angular/material/form-field";
import {MatCardModule} from "@angular/material/card";
import { NavigationComponent } from './navigation/navigation.component';
import { RegisterComponent } from './register/register.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatSelectModule} from "@angular/material/select";
import {AuthInterceptor} from "./auth.interceptor";
import { DashboardComponent } from './dashboard/dashboard.component';
import {MatGridListModule} from "@angular/material/grid-list";
import {FlexModule} from "@angular/flex-layout";
import { ProductCreateComponent } from './product-create/product-create.component';
import { SearchResultsComponent } from './search-results/search-results.component';
import {MatListModule} from "@angular/material/list";
import { UpdateProductComponent } from './update-product/update-product.component';
import { ReportComponent } from './report/report.component';
import {MatTableModule} from "@angular/material/table";
import {MatSidenavModule} from "@angular/material/sidenav";
import { ProductPageComponent } from './product-page/product-page.component';
import {MatOptionModule} from "@angular/material/core";
import { UsermanagementComponent } from './usermanagement/usermanagement.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavigationComponent,
    RegisterComponent,
    DashboardComponent,
    ProductCreateComponent,
    SearchResultsComponent,
    UpdateProductComponent,
    ReportComponent,
    ProductPageComponent,
    UsermanagementComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatToolbarModule,
    MatInputModule,
    MatFormFieldModule,
    MatCardModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatGridListModule,
    FlexModule,
    MatListModule,
    MatTableModule,
    MatSidenavModule,
    MatOptionModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
