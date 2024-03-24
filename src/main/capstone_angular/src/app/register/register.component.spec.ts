import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterComponent } from './register.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {RouterTestingModule} from "@angular/router/testing";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatCardModule} from "@angular/material/card";
import {MatOptionModule} from "@angular/material/core";
import {MatTableModule} from "@angular/material/table";
import {MatSelectModule} from "@angular/material/select";
import {Router} from "@angular/router";
import {of} from "rxjs";
import {UserService} from "../service/user/UserService";

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let mockUserService: any;
  let router: Router;

  beforeEach(async () => {
    mockUserService = {
      createUser: jasmine.createSpy('createUser').and.returnValue(of({
        message: 'User created successfully'
      }))
    };


    await TestBed.configureTestingModule({
      imports: [
        MatFormFieldModule,
        MatInputModule,
        RouterTestingModule,
        BrowserAnimationsModule,
        MatOptionModule,
        MatTableModule,
        FormsModule,
        MatSelectModule,
        ReactiveFormsModule,
        FormsModule,
        MatCardModule,
        HttpClientTestingModule
      ],
      declarations: [RegisterComponent],
      providers: [
        {
          provide:
          UserService,
          useValue: mockUserService
        }]
    }).compileComponents();


    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('Should create a user and navigate to dashboard', (done) => {
    spyOn(router, 'navigate');

    component.user = {
      username: 'Test User',
      firstName: 'Test',
      lastName: 'User',
      phoneNumber: '1234567890',
      email: '<EMAIL>',
      password: 'password123',
      role: 'ADMIN'
    };

    component.register();

    fixture.whenStable().then(() => {
      expect(mockUserService.createUser).toHaveBeenCalledWith(component.user);
      expect(router.navigate).toHaveBeenCalledWith(['/dashboard']);
      done();
    })
  })
});
