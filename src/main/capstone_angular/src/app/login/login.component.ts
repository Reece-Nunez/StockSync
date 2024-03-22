import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { AuthenticationService } from '../service/auth/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private authService: AuthenticationService, private router: Router) { }

  onSubmit(): void {
    this.authService.login({ username: this.username, password: this.password }).subscribe({
      next: (response) => {
        // Navigate to dashboard if login is successful
        // Note: Check or adjust this based on your actual response structure
        if (response.status === 200) {
          localStorage.setItem('isLoggedIn', 'true');
          localStorage.setItem('username', this.username);
          this.router.navigate(['/dashboard']);
          alert('Login successful, welcome: ' + this.username + '!');
        } else {
          // Optionally handle different conditions based on response or status
          alert('Login failed. Please check your credentials.');
        }
      },
      error: (error) => {
        console.error('Login failed:', error);
        alert('No user found with that username');
      }
    });
  }

  createUser(): void {
    // Navigate to create user page
    this.router.navigate(['create-user']);
  }

  logout(): void {
    // Clear authentication state on logout
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('username');
    this.router.navigate(['/login']);
    alert('You have been logged out.');
  }
}
