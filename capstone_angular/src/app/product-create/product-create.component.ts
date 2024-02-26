import { Component } from '@angular/core';
import {ProductService} from "../service/product/product.service";
import {Router} from "@angular/router";

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
    category: ''
  };

  constructor(private productService: ProductService, private router: Router) { }

  createProduct() {
    this.productService.createProduct(this.product).subscribe({
      next: (data) => {
        console.log(data);
        alert('Product created successfully!');
        this.router.navigate(['/dashboard']);
      },
      error: (error) => console.error(error),
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
