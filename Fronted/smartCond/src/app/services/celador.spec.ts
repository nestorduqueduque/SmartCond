import { TestBed } from '@angular/core/testing';

import { Celador } from './celador';

describe('Celador', () => {
  let service: Celador;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Celador);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
