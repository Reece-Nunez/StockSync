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
    price: null,
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
}
