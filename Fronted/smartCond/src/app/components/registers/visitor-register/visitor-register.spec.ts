import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisitorRegister } from './visitor-register';

describe('VisitorRegister', () => {
  let component: VisitorRegister;
  let fixture: ComponentFixture<VisitorRegister>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VisitorRegister]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VisitorRegister);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
