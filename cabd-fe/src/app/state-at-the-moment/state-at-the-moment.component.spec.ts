import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StateAtTheMomentComponent } from './state-at-the-moment.component';

describe('StateAtTheMomentComponent', () => {
  let component: StateAtTheMomentComponent;
  let fixture: ComponentFixture<StateAtTheMomentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StateAtTheMomentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StateAtTheMomentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
