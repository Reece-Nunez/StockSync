import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    if (localStorage.getItem('token')) {
      // Token found, user is logged in
      return true;
    } else {
      // No token found, redirect to login page
      this.router.navigate(['/login']);
      return false;
    }
  }
}
