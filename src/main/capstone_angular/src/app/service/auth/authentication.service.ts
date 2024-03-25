import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {Location, LocationStrategy} from '@angular/common';
import {jwtDecode} from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private authUrl: string = 'http://localhost:8080/api/users';
  //private authUrl;
  constructor(private http: HttpClient, private location: Location) {
   // this.authUrl = this.location.prepareExternalUrl('/api/users');
  }

  login(credentials: { username: string, password: string}): Observable<any> {
    return this.http.post(`${this.authUrl}/login`, credentials, { observe: 'response' })
      .pipe(map(response => {
        const token = response.headers.get('Authorization')?.split(' ')[1];
        return { token, body: response.body };
      }));
  }

  logout(): Observable<any> {
    return this.http.post(`${this.authUrl}/logout`, null, { observe:'response' });
  }
  register(user: { username: string, password: string }): Observable<any> {
    return this.http.post(`${this.authUrl}/register`, user, { observe: 'response' });
  }

  isLoggedIn(): boolean {
    const token = localStorage.getItem('token');
    if (!token) {
      return false;
    }

    try {
      const decodedToken: any = jwtDecode(token);
      const isExpired = (decodedToken?.exp ?? 0) < Date.now() / 1000;
      return !isExpired;
    } catch (error) {
      console.error('Error decoding token:', error);
      return false;
    }
  }
}
