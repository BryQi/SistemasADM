import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DespliegueinfraestructuradispersionDetailComponent } from './despliegueinfraestructuradispersion-detail.component';

describe('Despliegueinfraestructuradispersion Management Detail Component', () => {
  let comp: DespliegueinfraestructuradispersionDetailComponent;
  let fixture: ComponentFixture<DespliegueinfraestructuradispersionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DespliegueinfraestructuradispersionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ despliegueinfraestructuradispersion: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DespliegueinfraestructuradispersionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DespliegueinfraestructuradispersionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load despliegueinfraestructuradispersion on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.despliegueinfraestructuradispersion).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
