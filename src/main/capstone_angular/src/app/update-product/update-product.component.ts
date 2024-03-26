import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ProductService } from "../service/product/product.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Product } from "../model/product.model";

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css']
})
export class UpdateProductComponent implements OnInit {
  productForm!: FormGroup;
  productId!: number;
  isLoading = true; // Indicates if the page is loading

  constructor(
    private formBuilder: FormBuilder,
    private productService: ProductService,
    public router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    // Get the product ID from the route
    this.productId = this.route.snapshot.params['id'];

    // Initialize the form with validation rules
    this.productForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      description: ['', [Validators.maxLength(200)]],
      price: ['', [Validators.required, Validators.min(0)]], // Ensure price is not negative
      quantity: ['', [Validators.required, Validators.min(0)]] // Ensure quantity is not negative
    });

    // Fetch the product details and populate the form
    this.productService.getProductById(this.productId).subscribe({
      next: (product: Product) => {
        this.isLoading = false;
        this.productForm.patchValue(product); // Use patchValue to fill the form
      },
      error: () => {
        this.isLoading = false;
        // Optionally handle the error, e.g., redirect or show a message
        alert('Product not found!');
        this.router.navigate(['/products']);
      }
    });
  }

  updateProduct() {
    if (this.productForm.valid) {
      // Call the productService to update the product
      this.productService.editProduct(this.productId, this.productForm.value).subscribe({
        next: () => {
          alert('Product updated successfully!');
          this.router.navigate(['/products']); // Redirect to the product listing or detail page
        },
        error: () => {
          alert('There was an error updating the product. Please try again.');
        }
      });
    } else {
      alert('Please correct the errors in the form.');
    }
  }
}
