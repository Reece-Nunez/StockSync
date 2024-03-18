import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProductCreateComponent } from './product-create.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ProductService } from "../service/product/product.service";
import { Router } from "@angular/router";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {of} from "rxjs";

describe('ProductCreateComponent Simplified', () => {
  let component: ProductCreateComponent;
  let fixture: ComponentFixture<ProductCreateComponent>;
  let productServiceMock: any;
  let router: Router;

  beforeEach(async () => {
    productServiceMock = {
      createProduct: jasmine.createSpy('createProduct').and.returnValue(of({
        id: 1,
        name: 'Test Product',
        description: 'Test Description',
        price: '20.00',
        quantity: 10
      }))
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
        HttpClientTestingModule
      ],
      declarations: [ ProductCreateComponent ],
      providers: [
        { provide: ProductService, useValue: productServiceMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ProductCreateComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });


  it('should submit product and navigate to dashboard', (done) => {
    spyOn(router, 'navigate');

    component.product = ({
      name: 'Test Product',
      description: 'Test Description',
      price: '20.00',
      quantity: 10
    });

    component.submitProduct();

    fixture.whenStable().then(() => {
      expect(productServiceMock.createProduct).toHaveBeenCalled();
      expect(router.navigate).toHaveBeenCalledWith(['/dashboard']);
      done();
    })
  });
});
