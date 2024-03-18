import {Component, OnInit} from '@angular/core';
import {ProductService} from '../service/product/product.service';
import {Product} from '../model/product.model';
import {ActivatedRoute, Router} from "@angular/router";
import {last} from "rxjs";

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {
  products: Product[] = [];

  constructor(private route: ActivatedRoute, private productService: ProductService, private router: Router) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      const searchTerm = params['term'];
      if(searchTerm) {
        this.productService.searchProducts(searchTerm).subscribe({
          next:(data) => {
            console.log(data);
            this.products = data
          },
          error: (error) => console.error(error)
        });
      }
    });
  }

  goHome() {
    this.router.navigate(['/dashboard']);
  }

  protected readonly last = last;
}
