import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CeladorRegister } from './celador-register';

describe('CeladorRegister', () => {
  let component: CeladorRegister;
  let fixture: ComponentFixture<CeladorRegister>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CeladorRegister]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CeladorRegister);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
