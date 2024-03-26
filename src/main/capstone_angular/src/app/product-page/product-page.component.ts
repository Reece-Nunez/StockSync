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
    const currentUserRole = localStorage.getItem('role');
    if (currentUserRole === 'ADMIN' || currentUserRole === 'OWNER') {
      const confirmDelete = confirm(`Are you sure you want to delete ${product.name}?`);
      if (confirmDelete && product.id !== undefined) {
        this.productService.deleteProduct(product.id).subscribe({
          next: () => {
            alert('Product deleted successfully!');
            this.products = this.products.filter(p => p.id !== product.id);
          },
          error: (error) => {
            console.error('Error deleting the product:', error);
            alert('Failed to delete the product.');
          }
        });
      }
    } else {
      alert('You do not have permission to delete products.');
    }
  }

  updateProduct(product: Product): void {
    // Navigate to the update product page with the product ID
    if(product.id !== undefined) {
      this.router.navigate(['/products/update', product.id]);
    } else {
      alert('Product ID is undefined, cannot navigate to update page.');
    }
  }
}
