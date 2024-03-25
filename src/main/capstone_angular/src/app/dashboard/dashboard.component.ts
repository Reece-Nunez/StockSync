import { Component, OnInit } from '@angular/core';
import { ProductService } from '../service/product/product.service';
import { Product } from '../model/product.model';
import {Router} from "@angular/router";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  products: Product[] = [];
  allProducts: Product[] = [];
  totalProducts: number = 0;
  totalQuantity: number = 0;
  username: string | null = '';
  totalProductPrice: number = 0;
  productsOutOfStock: Product[] = [];
  lowStock: Product[] = [];
  role: string | null = '';
  firstName : string | null = '';

  constructor(private productService: ProductService, protected router: Router) {}

  ngOnInit(): void {
    this.username = localStorage.getItem('username');
    this.role = localStorage.getItem('role');
    this.firstName = localStorage.getItem('firstName');
    this.productService.getProducts().subscribe({
      next: (data: Product[]) => {
        this.allProducts = data;
        this.products = data;
        this.calculateTotals();
      },
      error: (error) => {
        console.error('There was an error!', error);
      },
      complete: () => console.log('Product fetching completed') // Optional
    });
  }

  applySearch(searchTerm: string): void {
    if (searchTerm) {
      this.router.navigate(['/search-results'], { queryParams: { term: searchTerm } });
    }
  }


  private calculateTotals(): void {
    this.totalProducts = this.products.length;
    this.totalQuantity = this.products.reduce((acc, product) => {
      return acc + (product.quantity !== null ? product.quantity : 0);
    }, 0);
    this.totalProductPrice = this.products.reduce((acc, product) => acc + ((+product.price || 0) * (product.quantity || 0)), 0);
    this.productsOutOfStock = this.products.filter(product => product.quantity === 0);
    this.lowStock = this.products.filter(product => (product.quantity ?? 0) <= 5);
  }

  deleteProduct(product: Product): void {
    const confirmDelete = confirm('Are you sure you want to delete '+ product.name + '?');

    if (!confirmDelete || !product.id) return;
    this.productService.deleteProduct(product.id).subscribe({
      next: () => {
        alert('Product deleted successfully!');
        this.products = this.products.filter(p => p.id!== product.id);
      },
      error: (error) => console.error(error),
    })
  }

  updateProduct(product: Product): void {
    this.router.navigate(['/products/update/', product.id]);  }

  onLogout() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
    alert('You have been logged out');
  }

}
