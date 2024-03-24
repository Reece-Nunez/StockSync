import { Component } from '@angular/core';
import { UserService } from "../service/user/UserService";
import { Router } from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  // Updated user object to include new fields
  user = {
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    role: 'USER'
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
    });
  }

  goHome(): void {
    this.router.navigate(['/login']);
  }

  onPhoneNumberChange(event: any): void {
    let numbers = event.replace(/\D/g, ''); // Remove all non-digit characters
    // Define char with an index signature
    let char: { [key: string]: string } = { '0': '(', '3': ') ', '6': '-' };
    numbers = numbers.slice(0, 10); // Ensure a maximum of 10 digits
    let formattedNumber = '';
    for (let i = 0; i < numbers.length; i++) {
      // Use string keys directly, compatible with the index signature
      formattedNumber += (char[i.toString()] || '') + numbers[i];
    }
    this.user.phoneNumber = formattedNumber;
  }
}
