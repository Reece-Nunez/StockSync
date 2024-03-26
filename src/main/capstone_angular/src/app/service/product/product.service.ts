import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Product } from '../../model/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private productUrl: string = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) {}

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.productUrl)
      .pipe(catchError(this.handleError));
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.productUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  createProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(`${this.productUrl}/create`, product)
      .pipe(catchError(this.handleError));
  }

  editProduct(id: number, product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.productUrl}/update/${id}`, product)
      .pipe(catchError(this.handleError));
  }

  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.productUrl}/delete/${id}`)
      .pipe(catchError(this.handleError));
  }

  searchProducts(term: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.productUrl}?search=${term}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any) {
    console.error('An error occurred:', error.error.message);
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }
}
