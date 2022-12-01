import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DespliegueInfraestructuraTroncalDistribucionDetailComponent } from './despliegue-infraestructura-troncal-distribucion-detail.component';

describe('DespliegueInfraestructuraTroncalDistribucion Management Detail Component', () => {
  let comp: DespliegueInfraestructuraTroncalDistribucionDetailComponent;
  let fixture: ComponentFixture<DespliegueInfraestructuraTroncalDistribucionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DespliegueInfraestructuraTroncalDistribucionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ despliegueInfraestructuraTroncalDistribucion: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DespliegueInfraestructuraTroncalDistribucionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DespliegueInfraestructuraTroncalDistribucionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load despliegueInfraestructuraTroncalDistribucion on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.despliegueInfraestructuraTroncalDistribucion).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
