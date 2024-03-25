import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, pipe, throwError} from 'rxjs';
import { Product } from '../../model/product.model';
import {Location, LocationStrategy} from '@angular/common';
@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private productUrl: string = 'http://localhost:8080/api/products';
  private searchUrl: string = 'http://localhost:8080/api/search/results';
  //private productUrl;
  //private searchUrl;


  constructor(private http: HttpClient, private location: Location) {
    //this.productUrl = this.location.prepareExternalUrl('/api/products');
   // this.searchUrl = this.location.prepareExternalUrl('/api/search/results');
  }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.productUrl)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';
    if(error.error instanceof ErrorEvent) {
      errorMessage = `An error occurred: ${error.error.message}`;
    } else {
      errorMessage = `Server returned code: ${error.status}, ` + `error message is: ${error.message}`;
    }
    console.error(errorMessage);
    return throwError(() => errorMessage);
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.productUrl}/${id}`);
  }

  createProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(`${this.productUrl}/create`, product);
  }

  editProduct(id: number, product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.productUrl}/update/${id}`, product);
  }

  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.productUrl}/delete/${id}`);
  }

  searchProducts(term: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.searchUrl}?searchTerm=${term}`)
      .pipe(catchError(this.handleError));
  }
}
