import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CeladorDashboard } from './celador-dashboard';

describe('CeladorDashboard', () => {
  let component: CeladorDashboard;
  let fixture: ComponentFixture<CeladorDashboard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CeladorDashboard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CeladorDashboard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
