import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportComponent } from './report.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {RouterTestingModule} from "@angular/router/testing";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatCardModule} from "@angular/material/card";
import {DataSource} from "@angular/cdk/collections";
import {BrowserDynamicTestingModule} from "@angular/platform-browser-dynamic/testing";
import {MatTableModule} from "@angular/material/table";

describe('ReportComponent', () => {
  let component: ReportComponent;
  let fixture: ComponentFixture<ReportComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        MatFormFieldModule,
        MatInputModule,
        RouterTestingModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        FormsModule,
        MatTableModule,
        MatCardModule,
        BrowserDynamicTestingModule,
        HttpClientTestingModule
      ],
      declarations: [ReportComponent]
    });
    fixture = TestBed.createComponent(ReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
