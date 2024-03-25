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
        console.log('Login Response:', response); // Log the response object to console
        const token = response.body.token;

        if (token) {
          // Proceed with storing the token and other information
          localStorage.setItem('isLoggedIn', 'true');
          localStorage.setItem('username', this.username);
          localStorage.setItem('firstName', response.body.firstName);
          localStorage.setItem('lastName', response.body.lastName);
          localStorage.setItem('phoneNumber', response.body.phoneNumber);
          localStorage.setItem('email', response.body.email);
          localStorage.setItem('role', response.body.role);
          localStorage.setItem('token', token); // Save the extracted token
          alert('Login successful, welcome: ' + response.body.name + '!');
          this.router.navigate(['/dashboard']);
        } else {
          // Handle the missing token scenario
          console.error('No token provided in response');
        }
      },
      error: (error) => {
        console.error('Login failed:', error);
        alert('Login failed. Please check your credentials.');
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
    localStorage.removeItem('role');
    localStorage.removeItem('token');
    localStorage.removeItem('firstName');
    localStorage.removeItem('lastName');
    localStorage.removeItem('phoneNumber');
    localStorage.removeItem('email');
    this.router.navigate(['/login']);
    alert('You have been logged out.');
  }
}
