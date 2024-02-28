import {Component, OnInit} from '@angular/core';
import {ProductService} from "../service/product/product.service";
import {Router} from "@angular/router";
import {Product} from "../model/product.model";
import {Observable} from "rxjs";

@Component({
  selector: 'app-product-create',
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css']
})
export class ProductCreateComponent implements OnInit {
  product: Product = {
    name: '',
    description: '',
    price: '0.00',
    quantity: null,
  };

  constructor(private productService: ProductService, private router: Router) { }

  ngOnInit(): void {
  }


  submitProduct(): void {
    // Directly use this.product, no need to create a new variable if you're not modifying it
    this.productService.createProduct(this.product).subscribe({
      next: (data) => {
        console.log(data);
        alert('Product created successfully!');
        this.router.navigate(['/dashboard']);
      },
      error: (error) => console.error(error)
    });
  }

  validateNumber(event: KeyboardEvent): void {
    const allowedKeys = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'Backspace', 'ArrowUp', 'ArrowDown', 'ArrowLeft', 'ArrowRight', 'Delete', 'Enter', 'Tab'];
    if (!allowedKeys.includes(event.key) && !event.key.match(/^\d$/)) {
      event.preventDefault();
    }
  }

  formatPrice(event: Event): void {
    const target = event.target as HTMLInputElement;
    let value = target.value.replace(/\D/g, ''); // Remove non-digit characters
    if (value === '') value = '0';
    let intValue = parseInt(value, 10);
    let formattedValue = (intValue / 100).toFixed(2); // Convert to decimal format
    this.product.price = formattedValue;
    target.value = formattedValue; // Update input field with formatted value
  }
}
