import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VehicleResidentList } from './vehicle-resident-list';

describe('VehicleResidentList', () => {
  let component: VehicleResidentList;
  let fixture: ComponentFixture<VehicleResidentList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VehicleResidentList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VehicleResidentList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
