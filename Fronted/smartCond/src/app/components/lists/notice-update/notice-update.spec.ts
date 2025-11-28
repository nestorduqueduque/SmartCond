import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoticeUpdate } from './notice-update';

describe('NoticeUpdate', () => {
  let component: NoticeUpdate;
  let fixture: ComponentFixture<NoticeUpdate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NoticeUpdate]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NoticeUpdate);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
