import { Component } from '@angular/core';
import {ProductService} from "../service/product/product.service";
import {Router} from "@angular/router";
import {CategoryService} from "../service/category/category.service";
import {Product} from "../model/product.model";
import {Observable} from "rxjs";

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css']
})
export class ProductCreateComponent {
  product = {
    name: '',
    description: '',
    price: '0.00',
    quantity: null,
    category: undefined
  };

  categories: any[] = [];

  constructor(private productService: ProductService, private categoryService: CategoryService, private router: Router) { }

  ngOnInit(): void {
    this.fetchCategories();
  }

  fetchCategories(): void {
    this.categoryService.getCategories().subscribe({
      next: (categories) => this.categories = categories,
      error: (error) => console.error(error)
    });
  }

  createProduct(product: Partial<Product>): Observable<any> {
    // Check if the category is new and needs to be added to the database
    if (!this.categories.find(c => c.name === this.product.category)) {
      this.categoryService.addCategory({ name: this.product.category }).subscribe({
        next: (category) => {
          this.product.category = category.id; // Use the new category's ID
          this.submitProduct(); // Now submit the product with the new category
        },
        error: (error) => console.error(error)
      });
    } else {
      this.submitProduct(); // Submit the product if the category already exists
    }
  }

  submitProduct() {
    this.productService.createProduct(this.product as Product).subscribe({
      next: (data) => {
        console.log(data);
        alert('Product created successfully!');
        this.router.navigate(['/dashboard']);
      },
      error: (error) => console.error(error)
    });
  }

  validateNumber(event: KeyboardEvent) {
    const allowedKeys = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'Backspace', 'ArrowUp', 'ArrowDown', 'ArrowLeft', 'ArrowRight', 'Delete', 'Enter', 'Tab'];
    if (!allowedKeys.includes(event.key) && !event.key.match(/^\d$/)) {
      event.preventDefault();
    }
  }

  formatPrice(event: any) {
    let value = event.target.value.replace(/\D/g, ''); // Remove non-digit characters
    if (value === '') value = '0';
    let intValue = parseInt(value, 10);
    let formattedValue = (intValue / 100).toFixed(2); // Convert to decimal format
    this.product.price = formattedValue;
    event.target.value = formattedValue; // Update input field with formatted value
  }
}
