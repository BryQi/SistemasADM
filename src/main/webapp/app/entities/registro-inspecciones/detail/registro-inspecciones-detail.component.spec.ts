import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RegistroInspeccionesDetailComponent } from './registro-inspecciones-detail.component';

describe('RegistroInspecciones Management Detail Component', () => {
  let comp: RegistroInspeccionesDetailComponent;
  let fixture: ComponentFixture<RegistroInspeccionesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegistroInspeccionesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ registroInspecciones: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RegistroInspeccionesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RegistroInspeccionesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load registroInspecciones on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.registroInspecciones).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
