import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VehicleVisitorList } from './vehicle-visitor-list';

describe('VehicleVisitorList', () => {
  let component: VehicleVisitorList;
  let fixture: ComponentFixture<VehicleVisitorList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VehicleVisitorList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VehicleVisitorList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
