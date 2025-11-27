import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CeladorUpdate } from './celador-update';

describe('CeladorUpdate', () => {
  let component: CeladorUpdate;
  let fixture: ComponentFixture<CeladorUpdate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CeladorUpdate]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CeladorUpdate);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
