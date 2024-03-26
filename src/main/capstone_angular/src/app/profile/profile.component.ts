import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup} from '@angular/forms';
import {UserService} from '../service/user/UserService';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profileForm: FormGroup;
  isEditMode: boolean = false;
  role: string | null = '';
  userId: string | null = '';
  titleName: string | null = '';


  constructor (private router: Router, private fb: FormBuilder, private userService: UserService) {
    this.profileForm = this.fb.group({
      username: [''],
      firstName: [''],
      lastName: [''],
      phoneNumber: [''],
      email: [''],
      role: [{value: '', disabled: true}]
    })
  }

  ngOnInit(): void {
    this.role = localStorage.getItem('role');
    this.userId = localStorage.getItem('userId');
    this.titleName = localStorage.getItem('firstName');
    this.loadProfile();
  }

  private loadProfile(): void {
    const profile = {
      username: localStorage.getItem('username'),
      firstName: localStorage.getItem('firstName'),
      lastName: localStorage.getItem('lastName'),
      phoneNumber: localStorage.getItem('phoneNumber'),
      email: localStorage.getItem('email'),
      role: this.role
    };
    this.profileForm.setValue(profile);
  }

  toggleEdit(): void {
    this.isEditMode = !this.isEditMode;
    // Retrieve the user's role from localStorage or another appropriate source
    const currentUserRole = localStorage.getItem('role');
    console.log(currentUserRole);

    if (this.isEditMode && currentUserRole === 'OWNER') {
      // Enable editing of all fields, including the role, only if the current user is an OWNER
      this.profileForm.enable();
    } else {
      // If not in edit mode or the user is not an OWNER, disable editing of the role field
      // but keep other fields editable according to your requirements
      this.profileForm.disable(); // This disables the entire form; adjust if needed
      this.profileForm.get('username')?.enable();
      this.profileForm.get('firstName')?.enable();
      this.profileForm.get('lastName')?.enable();
      this.profileForm.get('phoneNumber')?.enable();
      this.profileForm.get('email')?.enable();
      // Explicitly disable the role field to prevent editing
      this.profileForm.get('role')?.disable();
    }
  }



  saveProfile(): void {
    if (this.profileForm.valid && this.userId) {
      const userData = { ...this.profileForm.getRawValue(), id: this.userId }; // Use getRawValue to include disabled fields
      this.userService.updateUser(userData).subscribe({
        next: (response) => {
          alert('Profile updated successfully.');
          // Update localStorage for all fields
          localStorage.setItem('username', this.profileForm.value.username);
          localStorage.setItem('firstName', this.profileForm.value.firstName);
          localStorage.setItem('lastName', this.profileForm.value.lastName);
          localStorage.setItem('phoneNumber', this.profileForm.value.phoneNumber);
          localStorage.setItem('email', this.profileForm.value.email);
          // No need to update role here as it's not editable in this form
          this.loadProfile(); // Reload profile to reflect updated information
          this.toggleEdit(); // Turn off edit mode
        },
        error: (error) => {
          console.error('Error updating profile:', error);
          alert('Failed to update profile.');
        }
      });
    }
  }

  goHome(): void {
    this.router.navigate(['/dashboard']);
  }
}
