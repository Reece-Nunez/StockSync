// product-page.component.ts
import { Component, OnInit } from '@angular/core';
import { ProductService } from '../service/product/product.service';
import { Product } from '../model/product.model';
import { Router } from "@angular/router";

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css']
})
export class ProductPageComponent implements OnInit {
  products: Product[] = [];
  allProducts: Product[] = [];
  totalProducts: number = 0;
  totalQuantity: number = 0;
  username: string | null = '';

  constructor(private productService: ProductService, private router: Router) {}

  ngOnInit(): void {
    this.username = localStorage.getItem('username');
    this.productService.getProducts().subscribe({
      next: (data: Product[]) => {
        this.allProducts = data;
        this.products = data;
        this.calculateTotals();
      },
      error: (error) => {
        console.error('There was an error!', error);
      }
    });
  }

  applySearch(searchTerm: string): void {
    if (searchTerm) {
      this.router.navigate(['/search-results'], { queryParams: { term: searchTerm } });
    }
  }

  private calculateTotals(): void {
    this.totalProducts = this.products.length;
    this.totalQuantity = this.products.reduce((acc, product) => acc + (product.quantity || 0), 0);
  }

  deleteProduct(product: Product): void {
    // Implementation remains the same
  }

  updateProduct(product: Product): void {
    // Implementation remains the same
  }
}
