import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {RouterTestingModule} from "@angular/router/testing";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatCardModule} from "@angular/material/card";
import {MatToolbarModule} from "@angular/material/toolbar";
import {Router} from "@angular/router";
import {of} from "rxjs";

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let mockAuthService: any;
  let router: Router;

  beforeEach(async () => {
    mockAuthService = {
      login: jasmine.createSpy('login')
        .and.returnValue(of({ token: 'fake-token'}))
    };
    await TestBed.configureTestingModule({
      imports: [
        MatFormFieldModule,
        MatInputModule,
        RouterTestingModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        FormsModule,
        MatCardModule,
        MatToolbarModule,
        HttpClientTestingModule
      ],
      declarations: [LoginComponent],
      providers: [
        {
          useValue: mockAuthService
        }
      ]
    }).compileComponents();


    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    spyOn(router, 'navigate');
    spyOn(window, 'alert');
    fixture.detectChanges();
  });

 it('Should login successfully and navigate to dashboard', () => {
   component.username = 'testuser';
   component.password = 'password123';

   component.onSubmit();

   expect(mockAuthService.login).toHaveBeenCalledWith('testuser', 'password123');
   expect(localStorage.getItem('token')).toBe('fake-token');
   expect(router.navigate).toHaveBeenCalledWith(['/dashboard']);
   expect(window.alert).toHaveBeenCalledWith('Login successful');

 })
});
