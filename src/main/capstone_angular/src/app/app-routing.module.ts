import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {ProductCreateComponent} from "./product-create/product-create.component";
import {SearchResultsComponent} from "./search-results/search-results.component";
import {UpdateProductComponent} from "./update-product/update-product.component";
import {ReportComponent} from "./report/report.component";
import {ProductPageComponent} from "./product-page/product-page.component";
import {AuthGuard} from './guards/auth.guard';
import {ProfileComponent} from "./profile/profile.component";

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'create-user',
    component: RegisterComponent
  },
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'products/create',
    component: ProductCreateComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'search-results',
    component: SearchResultsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'products/update/:id',
    component: UpdateProductComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'report',
    component: ReportComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'product-page',
    component: ProductPageComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
