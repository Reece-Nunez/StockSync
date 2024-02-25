import { Component } from '@angular/core';
import {UserService} from "../service/UserService";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  user = {
    username: '',
    password: '',
    roleName: ''
  }

  constructor(private userService: UserService, private router: Router) { }

  register(): void {
    this.userService.createUser(this.user).subscribe({
      next: (data) => {
        console.log(data);
        alert('User created successfully');
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.log('There was an error!', error);
        alert('Failed to create user');
      }
    })
  }

}
