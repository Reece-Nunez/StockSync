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
  totalProducts: number = 0;
  totalQuantity: number = 0;

  constructor(private productService: ProductService, private router: Router) {}

  ngOnInit(): void {
    this.productService.getProducts().subscribe({
      next: (data: Product[]) => {
        this.products = data;
        this.calculateTotals();
      },
      error: (error) => {
        console.error('There was an error!', error);
      },
      complete: () => console.log('Product fetching completed') // Optional
    });

  }

  private calculateTotals(): void {
    this.totalProducts = this.products.length;
    this.totalQuantity = this.products.reduce((acc, product) => {
      return acc + (product.quantity !== null ? product.quantity : 0);
    }, 0);
  }

  deleteProduct(product: Product): void {
    if (!product.id) return;
    this.productService.deleteProduct(product.id).subscribe({
      next: () => {
        alert('Product deleted successfully!');
        this.products = this.products.filter(p => p.id!== product.id);
      },
      error: (error) => console.error(error),
    })
  }

  updateProduct(product: Product): void {
    this.router.navigate(['/products/update', product.id]);  }
}
