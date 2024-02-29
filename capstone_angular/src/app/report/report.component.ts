import {Component, OnInit} from '@angular/core';
import {Product} from "../model/product.model";
import {ProductService} from "../service/product/product.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
  products: Product[] = [];
  displayedColumns: string[] = ['name', 'price', 'quantity','dateCreated'];

  constructor(private productService: ProductService, protected router: Router) {}

  ngOnInit(): void {
    this.productService.getProducts().subscribe((data: Product[]) => {
      this.products = data.map(product => {
        const storedProductData = localStorage.getItem(`product_${product.id}`);
        if (storedProductData) {
          const additionalData = JSON.parse(storedProductData);
          return {...product, dateCreated: additionalData.dateCreated};
        }
        return product;
      })
    });
  }
}
