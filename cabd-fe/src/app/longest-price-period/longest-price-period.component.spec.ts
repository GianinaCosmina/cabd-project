import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LongestPricePeriodComponent } from './longest-price-period.component';

describe('LongestPricePeriodComponent', () => {
  let component: LongestPricePeriodComponent;
  let fixture: ComponentFixture<LongestPricePeriodComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LongestPricePeriodComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LongestPricePeriodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
