import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { UserService } from '../service/user/UserService';
import {UserDTO} from "../model/UserDTO.model";

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
  userForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    public dialogRef: MatDialogRef<EditUserComponent>,
    @Inject(MAT_DIALOG_DATA) public user: UserDTO
  ) {}

  ngOnInit(): void {
    this.initForm();
    console.log("User data received in dialog:", this.user);
    this.setRoleEditPermissions();
  }

  initForm(): void {
    this.userForm = this.fb.group({
      username: [this.user.username, [Validators.required]],
      email: [this.user.email, [Validators.required, Validators.email]],
      phoneNumber: [this.user.phoneNumber, [Validators.required]],
      firstName: [this.user.firstName, Validators.required],
      lastName: [this.user.lastName, Validators.required],
      role: [this.user.role, Validators.required] // Assuming 'currentRole' is passed in the data to indicate the role of the user doing the editing
    });
  }

  setRoleEditPermissions(): void {
    const currentUserRole = localStorage.getItem('role');
    if (currentUserRole !== 'OWNER') {
      this.userForm.get('role')?.disable();
    }
  }

  saveUser(): void {
    if (this.userForm.valid) {
      const updatedUserData = {...this.userForm.value, id: this.user.id};
      this.userService.updateUser(updatedUserData).subscribe({
        next: () => {
          console.log('Updated user data: ' + updatedUserData);
          this.dialogRef.close(true); // Close the dialog and indicate success
        },
        error: (error) => {
          console.error('Error updating user:', error);
        }
      });
    }
  }

  cancel(): void {
    this.dialogRef.close(); // Close the dialog without doing anything
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
