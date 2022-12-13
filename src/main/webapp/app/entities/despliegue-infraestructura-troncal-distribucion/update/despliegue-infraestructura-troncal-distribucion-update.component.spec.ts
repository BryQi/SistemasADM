import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DespliegueInfraestructuraTroncalDistribucionFormService } from './despliegue-infraestructura-troncal-distribucion-form.service';
import { DespliegueInfraestructuraTroncalDistribucionService } from '../service/despliegue-infraestructura-troncal-distribucion.service';
import { IDespliegueInfraestructuraTroncalDistribucion } from '../despliegue-infraestructura-troncal-distribucion.model';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { PozoService } from 'app/entities/pozo/service/pozo.service';
import { IInfraestructura } from 'app/entities/infraestructura/infraestructura.model';
import { InfraestructuraService } from 'app/entities/infraestructura/service/infraestructura.service';

import { DespliegueInfraestructuraTroncalDistribucionUpdateComponent } from './despliegue-infraestructura-troncal-distribucion-update.component';

describe('DespliegueInfraestructuraTroncalDistribucion Management Update Component', () => {
  let comp: DespliegueInfraestructuraTroncalDistribucionUpdateComponent;
  let fixture: ComponentFixture<DespliegueInfraestructuraTroncalDistribucionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let despliegueInfraestructuraTroncalDistribucionFormService: DespliegueInfraestructuraTroncalDistribucionFormService;
  let despliegueInfraestructuraTroncalDistribucionService: DespliegueInfraestructuraTroncalDistribucionService;
  let pozoService: PozoService;
  let infraestructuraService: InfraestructuraService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DespliegueInfraestructuraTroncalDistribucionUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DespliegueInfraestructuraTroncalDistribucionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DespliegueInfraestructuraTroncalDistribucionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    despliegueInfraestructuraTroncalDistribucionFormService = TestBed.inject(DespliegueInfraestructuraTroncalDistribucionFormService);
    despliegueInfraestructuraTroncalDistribucionService = TestBed.inject(DespliegueInfraestructuraTroncalDistribucionService);
    pozoService = TestBed.inject(PozoService);
    infraestructuraService = TestBed.inject(InfraestructuraService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pozo query and add missing value', () => {
      const despliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion = { id: 456 };
      const pozos: IPozo[] = [{ id: 88240 }];
      despliegueInfraestructuraTroncalDistribucion.pozos = pozos;

      const pozoCollection: IPozo[] = [{ id: 68910 }];
      jest.spyOn(pozoService, 'query').mockReturnValue(of(new HttpResponse({ body: pozoCollection })));
      const additionalPozos = [...pozos];
      const expectedCollection: IPozo[] = [...additionalPozos, ...pozoCollection];
      jest.spyOn(pozoService, 'addPozoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ despliegueInfraestructuraTroncalDistribucion });
      comp.ngOnInit();

      expect(pozoService.query).toHaveBeenCalled();
      expect(pozoService.addPozoToCollectionIfMissing).toHaveBeenCalledWith(
        pozoCollection,
        ...additionalPozos.map(expect.objectContaining)
      );
      expect(comp.pozosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Infraestructura query and add missing value', () => {
      const despliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion = { id: 456 };
      const infraestructura: IInfraestructura = { id: 13653 };
      despliegueInfraestructuraTroncalDistribucion.infraestructura = infraestructura;

      const infraestructuraCollection: IInfraestructura[] = [{ id: 98994 }];
      jest.spyOn(infraestructuraService, 'query').mockReturnValue(of(new HttpResponse({ body: infraestructuraCollection })));
      const additionalInfraestructuras = [infraestructura];
      const expectedCollection: IInfraestructura[] = [...additionalInfraestructuras, ...infraestructuraCollection];
      jest.spyOn(infraestructuraService, 'addInfraestructuraToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ despliegueInfraestructuraTroncalDistribucion });
      comp.ngOnInit();

      expect(infraestructuraService.query).toHaveBeenCalled();
      expect(infraestructuraService.addInfraestructuraToCollectionIfMissing).toHaveBeenCalledWith(
        infraestructuraCollection,
        ...additionalInfraestructuras.map(expect.objectContaining)
      );
      expect(comp.infraestructurasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const despliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion = { id: 456 };
      const pozo: IPozo = { id: 70073 };
      despliegueInfraestructuraTroncalDistribucion.pozos = [pozo];
      const infraestructura: IInfraestructura = { id: 45134 };
      despliegueInfraestructuraTroncalDistribucion.infraestructura = infraestructura;

      activatedRoute.data = of({ despliegueInfraestructuraTroncalDistribucion });
      comp.ngOnInit();

      expect(comp.pozosSharedCollection).toContain(pozo);
      expect(comp.infraestructurasSharedCollection).toContain(infraestructura);
      expect(comp.despliegueInfraestructuraTroncalDistribucion).toEqual(despliegueInfraestructuraTroncalDistribucion);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDespliegueInfraestructuraTroncalDistribucion>>();
      const despliegueInfraestructuraTroncalDistribucion = { id: 123 };
      jest
        .spyOn(despliegueInfraestructuraTroncalDistribucionFormService, 'getDespliegueInfraestructuraTroncalDistribucion')
        .mockReturnValue(despliegueInfraestructuraTroncalDistribucion);
      jest.spyOn(despliegueInfraestructuraTroncalDistribucionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ despliegueInfraestructuraTroncalDistribucion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: despliegueInfraestructuraTroncalDistribucion }));
      saveSubject.complete();

      // THEN
      expect(despliegueInfraestructuraTroncalDistribucionFormService.getDespliegueInfraestructuraTroncalDistribucion).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(despliegueInfraestructuraTroncalDistribucionService.update).toHaveBeenCalledWith(
        expect.objectContaining(despliegueInfraestructuraTroncalDistribucion)
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDespliegueInfraestructuraTroncalDistribucion>>();
      const despliegueInfraestructuraTroncalDistribucion = { id: 123 };
      jest
        .spyOn(despliegueInfraestructuraTroncalDistribucionFormService, 'getDespliegueInfraestructuraTroncalDistribucion')
        .mockReturnValue({ id: null });
      jest.spyOn(despliegueInfraestructuraTroncalDistribucionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ despliegueInfraestructuraTroncalDistribucion: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: despliegueInfraestructuraTroncalDistribucion }));
      saveSubject.complete();

      // THEN
      expect(despliegueInfraestructuraTroncalDistribucionFormService.getDespliegueInfraestructuraTroncalDistribucion).toHaveBeenCalled();
      expect(despliegueInfraestructuraTroncalDistribucionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDespliegueInfraestructuraTroncalDistribucion>>();
      const despliegueInfraestructuraTroncalDistribucion = { id: 123 };
      jest.spyOn(despliegueInfraestructuraTroncalDistribucionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ despliegueInfraestructuraTroncalDistribucion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(despliegueInfraestructuraTroncalDistribucionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePozo', () => {
      it('Should forward to pozoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pozoService, 'comparePozo');
        comp.comparePozo(entity, entity2);
        expect(pozoService.comparePozo).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareInfraestructura', () => {
      it('Should forward to infraestructuraService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(infraestructuraService, 'compareInfraestructura');
        comp.compareInfraestructura(entity, entity2);
        expect(infraestructuraService.compareInfraestructura).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
