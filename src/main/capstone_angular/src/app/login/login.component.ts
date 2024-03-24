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
          localStorage.setItem('isLoggedIn', 'true');
          localStorage.setItem('username', this.username);
          localStorage.setItem('token', response.token);
          this.router.navigate(['/dashboard']);
          alert('Login successful, welcome: ' + this.username + '!');
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
