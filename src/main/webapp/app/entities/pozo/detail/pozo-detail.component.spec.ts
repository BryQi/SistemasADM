import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PozoDetailComponent } from './pozo-detail.component';

describe('Pozo Management Detail Component', () => {
  let comp: PozoDetailComponent;
  let fixture: ComponentFixture<PozoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PozoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ pozo: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PozoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PozoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load pozo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.pozo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
