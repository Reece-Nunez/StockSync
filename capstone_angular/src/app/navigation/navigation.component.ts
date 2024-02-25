import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../service/auth/AuthService";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent {
  username:string = '';
  password:string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);
        this.router.navigate(['/inventory']);
      },
      error: (error) => {
        console.error('Login failed:', error);
      }
    });
  }

  createUser() {

  }

  deleteUser() {

  }
}
