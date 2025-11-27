import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResidentUpdate } from './resident-update';

describe('ResidentUpdate', () => {
  let component: ResidentUpdate;
  let fixture: ComponentFixture<ResidentUpdate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResidentUpdate]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResidentUpdate);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
