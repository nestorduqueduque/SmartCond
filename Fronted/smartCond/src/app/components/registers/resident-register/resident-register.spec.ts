import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResidentRegister } from './resident-register';

describe('ResidentRegister', () => {
  let component: ResidentRegister;
  let fixture: ComponentFixture<ResidentRegister>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResidentRegister]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResidentRegister);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
