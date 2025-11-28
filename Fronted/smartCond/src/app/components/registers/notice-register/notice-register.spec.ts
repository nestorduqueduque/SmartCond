import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoticeRegister } from './notice-register';

describe('NoticeRegister', () => {
  let component: NoticeRegister;
  let fixture: ComponentFixture<NoticeRegister>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NoticeRegister]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NoticeRegister);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
