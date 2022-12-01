import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InfraestructuraDetailComponent } from './infraestructura-detail.component';

describe('Infraestructura Management Detail Component', () => {
  let comp: InfraestructuraDetailComponent;
  let fixture: ComponentFixture<InfraestructuraDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InfraestructuraDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ infraestructura: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(InfraestructuraDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InfraestructuraDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load infraestructura on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.infraestructura).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
