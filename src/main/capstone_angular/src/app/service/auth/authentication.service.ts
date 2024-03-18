import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Location, LocationStrategy} from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private authUrl: string;

  constructor(private http: HttpClient, private location: Location) {
    this.authUrl = this.location.prepareExternalUrl('/api/users');
  }

  login(credentials: { username: string, password: string }): Observable<any> {
    return this.http.post(`${this.authUrl}/login`, credentials, { observe: 'response' });
  }

  logout(): Observable<any> {
    return this.http.post(`${this.authUrl}/logout`, null, { observe:'response' });
  }
  register(user: { username: string, password: string }): Observable<any> {
    return this.http.post(`${this.authUrl}/register`, user, { observe: 'response' });
  }
}
