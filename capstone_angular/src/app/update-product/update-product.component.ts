import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ProductService} from "../service/product/product.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Product} from "../model/product.model";

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css']
})
export class UpdateProductComponent {
  productForm!: FormGroup;
  productId!: number;
  isLoading = true;

  constructor(private formBuilder: FormBuilder, private productService: ProductService, protected router: Router, private route: ActivatedRoute) {}

  ngOnInit() {
    this.productId = this.route.snapshot.params['id'];
    this.productForm = this.formBuilder.group({
      name:['', [ Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      description:['', [Validators.maxLength(200)]],
      price:['', [Validators.required]],
      quantity:['', [Validators.required]]
    });

    this.productService.getProductById(this.productId).subscribe({
      next: (product: Product) => {
        this.isLoading = false;
        this.productForm.patchValue(product);
      },
      error: () => {
        this.isLoading = false;
      }
    });
  }

  updateProduct(){
    if (this.productForm.valid) {
      this.productService.editProduct(this.productId, this.productForm.value).subscribe({
        next: () => {
          this.router.navigate(['/products']);
        },
        error: () => {
          alert('There was an error!');
        }
      });
    }
  }
}
