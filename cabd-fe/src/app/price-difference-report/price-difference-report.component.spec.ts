import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PriceDifferenceReportComponent } from './price-difference-report.component';

describe('PriceDifferenceReportComponent', () => {
  let component: PriceDifferenceReportComponent;
  let fixture: ComponentFixture<PriceDifferenceReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PriceDifferenceReportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PriceDifferenceReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
