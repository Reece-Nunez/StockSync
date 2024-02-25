import { Component } from '@angular/core';
import {AuthService} from "../service/AuthService";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  login() {
    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        alert('Login successful')
      },
      error: (error) => {
        console.log('Login error', error);
        alert('Login failed');
      }
    });
  }

  createUser() {
    this.router.navigate(['create-user']);
  }

  deleteUser() {
    this.authService.deleteUser(this.username, this.password).subscribe({
      next: (response) => {
        alert('User deleted successfully')
      }
    })
  }

  onSubmit() {
    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.error('Login failed:', error);
      }
    });
  }
}
