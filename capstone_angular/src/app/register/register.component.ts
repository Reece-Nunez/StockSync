import { Component } from '@angular/core';
import {UserService} from "../service/UserService";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  user = {
    username: '',
    password: ''
  }

  constructor(private userService: UserService) { }

  register(): void {
    this.userService.createUser(this.user).subscribe({
      next: (data) => {
        console.log(data);
        alert('User created successfully');
      },
      error: (error) => {
        console.log('There was an error!', error);
        alert('Failed to create user');
      }
    })
  }

}
