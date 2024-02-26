import { Component, OnInit } from '@angular/core';
import { ProductService } from '../service/product/product.service';
import { Product } from '../model/product.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  products: Product[] = [];
  totalProducts: number = 0;
  totalQuantity: number = 0;

  constructor(private productService: ProductService) {}

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
    this.totalQuantity = this.products.reduce((acc, product) => acc + product.quantity, 0);
  }
}
