import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CeladorList } from './celador-list';

describe('CeladorList', () => {
  let component: CeladorList;
  let fixture: ComponentFixture<CeladorList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CeladorList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CeladorList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
