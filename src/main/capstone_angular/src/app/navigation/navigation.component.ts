import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../service/auth/authentication.service";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent {
  username:string = '';
  password:string = '';

  constructor(private authenticationService: AuthenticationService, private router: Router) {}

  onSubmit() {
    this.authenticationService.login({username: this.username, password: this.password}).subscribe({
      next: (response) => {
        this.router.navigate(['/inventory']);
        this.username = '';
        this.password = '';
      },
      error: (error) => {
        console.error('Login failed:', error);
        alert('Login failed. Please check your username and password.');
      }
    });
  }

  createUser() {
    this.router.navigate(['/create-user']);
  }
}
