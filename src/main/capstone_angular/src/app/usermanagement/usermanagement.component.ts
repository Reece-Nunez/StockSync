import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user/UserService';

@Component({
  selector: 'app-usermanagement',
  templateUrl: './usermanagement.component.html',
  styleUrls: ['./usermanagement.component.css']
})
export class UsermanagementComponent implements OnInit {
  users: any[] = [];
  currentUserRole: string | null = '';

  constructor (private userService: UserService) {}

  ngOnInit(): void {
      this.currentUserRole = localStorage.getItem('role');
      this.fetchUsers();
  }

  fetchUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (error) => console.error('Error fetching users: ', error)
    });
  }

  changeUserRole(userId: number, newRole: string): void {
    if(this.currentUserRole !== 'OWNER') {
      alert('Only users with the role of Owner can change user roles.');
      return;
    }

    this.userService.updateUserRole(userId, newRole).subscribe({
      next: () => {
        alert('User Role updated successfully.');
        this.fetchUsers();
      },
      error: (error) => console.error('Error updating user role: ', error)
    });
  }

}
