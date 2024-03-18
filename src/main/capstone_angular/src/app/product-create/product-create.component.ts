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
    const formattedDate = new Intl.DateTimeFormat('en-CA', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit'
    }).format(new Date());
    const productWithDate = {
      ...this.product,
      dateCreated: formattedDate
    };
    console.log(productWithDate);
    this.productService.createProduct(productWithDate).subscribe({
      next: (data) => {
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

  goHome(): void {
  this.router.navigate(['/dashboard']);
}}
