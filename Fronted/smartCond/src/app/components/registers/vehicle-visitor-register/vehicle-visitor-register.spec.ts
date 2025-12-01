import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VehicleVisitorRegister } from './vehicle-visitor-register';

describe('VehicleVisitorRegister', () => {
  let component: VehicleVisitorRegister;
  let fixture: ComponentFixture<VehicleVisitorRegister>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VehicleVisitorRegister]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VehicleVisitorRegister);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
