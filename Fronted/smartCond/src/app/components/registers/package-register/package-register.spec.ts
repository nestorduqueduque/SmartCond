import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PackageRegister } from './package-register';

describe('PackageRegister', () => {
  let component: PackageRegister;
  let fixture: ComponentFixture<PackageRegister>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PackageRegister]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PackageRegister);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
