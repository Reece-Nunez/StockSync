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

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'create-user', component: RegisterComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent},
  { path: 'products/create', component: ProductCreateComponent},
  { path: 'search-results', component: SearchResultsComponent},
  { path: 'products/update/:id', component: UpdateProductComponent},
  { path: 'report', component: ReportComponent},
  { path: 'product-page', component: ProductPageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }