import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user/UserService';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog'; // If using dialogs for editing
import { EditUserComponent } from '../edit-user/edit-user.component';
import {UserDTO} from "../model/UserDTO.model";
import {Router} from "@angular/router"; // If using dialogs

@Component({
  selector: 'app-usermanagement',
  templateUrl: './usermanagement.component.html',
  styleUrls: ['./usermanagement.component.css']
})
export class UsermanagementComponent implements OnInit {
  users: UserDTO[] = [];
  displayedColumns: string[] = ['username', 'firstName', 'lastName','phoneNumber', 'email', 'role', 'actions']; // Columns in your mat-table
  currentUserRole: string | null = '';

  constructor(private userService: UserService, public dialog: MatDialog, public router: Router) {}

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

  openEditDialog(user: any): void {
    if (this.currentUserRole === 'ADMIN' && user.role === 'OWNER') {
      alert('Admins cannot edit Owner information.');
      return;
    }
    const dialogConfig = new MatDialogConfig();

    dialogConfig.width = '250px';
    dialogConfig.data = user;
    dialogConfig.panelClass = 'custom-center-dialog';

    const dialogRef = this.dialog.open(EditUserComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.fetchUsers(); // Refresh the list after editing
    });
  }

  changeUserRole(userId: number, newRole: string): void {
    // Similar to the initial implementation
  }
}
