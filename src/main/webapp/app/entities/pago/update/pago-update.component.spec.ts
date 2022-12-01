import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PagoFormService } from './pago-form.service';
import { PagoService } from '../service/pago.service';
import { IPago } from '../pago.model';
import { IDespliegueInfraestructuraTroncalDistribucion } from 'app/entities/despliegue-infraestructura-troncal-distribucion/despliegue-infraestructura-troncal-distribucion.model';
import { DespliegueInfraestructuraTroncalDistribucionService } from 'app/entities/despliegue-infraestructura-troncal-distribucion/service/despliegue-infraestructura-troncal-distribucion.service';
import { IDespliegueinfraestructuradispersion } from 'app/entities/despliegueinfraestructuradispersion/despliegueinfraestructuradispersion.model';
import { DespliegueinfraestructuradispersionService } from 'app/entities/despliegueinfraestructuradispersion/service/despliegueinfraestructuradispersion.service';

import { PagoUpdateComponent } from './pago-update.component';

describe('Pago Management Update Component', () => {
  let comp: PagoUpdateComponent;
  let fixture: ComponentFixture<PagoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pagoFormService: PagoFormService;
  let pagoService: PagoService;
  let despliegueInfraestructuraTroncalDistribucionService: DespliegueInfraestructuraTroncalDistribucionService;
  let despliegueinfraestructuradispersionService: DespliegueinfraestructuradispersionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PagoUpdateComponent],
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
      .overrideTemplate(PagoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PagoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pagoFormService = TestBed.inject(PagoFormService);
    pagoService = TestBed.inject(PagoService);
    despliegueInfraestructuraTroncalDistribucionService = TestBed.inject(DespliegueInfraestructuraTroncalDistribucionService);
    despliegueinfraestructuradispersionService = TestBed.inject(DespliegueinfraestructuradispersionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DespliegueInfraestructuraTroncalDistribucion query and add missing value', () => {
      const pago: IPago = { id: 456 };
      const idDespliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion = { id: 57471 };
      pago.idDespliegueInfraestructuraTroncalDistribucion = idDespliegueInfraestructuraTroncalDistribucion;

      const despliegueInfraestructuraTroncalDistribucionCollection: IDespliegueInfraestructuraTroncalDistribucion[] = [{ id: 43382 }];
      jest
        .spyOn(despliegueInfraestructuraTroncalDistribucionService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: despliegueInfraestructuraTroncalDistribucionCollection })));
      const additionalDespliegueInfraestructuraTroncalDistribucions = [idDespliegueInfraestructuraTroncalDistribucion];
      const expectedCollection: IDespliegueInfraestructuraTroncalDistribucion[] = [
        ...additionalDespliegueInfraestructuraTroncalDistribucions,
        ...despliegueInfraestructuraTroncalDistribucionCollection,
      ];
      jest
        .spyOn(despliegueInfraestructuraTroncalDistribucionService, 'addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pago });
      comp.ngOnInit();

      expect(despliegueInfraestructuraTroncalDistribucionService.query).toHaveBeenCalled();
      expect(
        despliegueInfraestructuraTroncalDistribucionService.addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing
      ).toHaveBeenCalledWith(
        despliegueInfraestructuraTroncalDistribucionCollection,
        ...additionalDespliegueInfraestructuraTroncalDistribucions.map(expect.objectContaining)
      );
      expect(comp.despliegueInfraestructuraTroncalDistribucionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Despliegueinfraestructuradispersion query and add missing value', () => {
      const pago: IPago = { id: 456 };
      const idDespliegueinfraestructuradispersion: IDespliegueinfraestructuradispersion = { id: 97415 };
      pago.idDespliegueinfraestructuradispersion = idDespliegueinfraestructuradispersion;

      const despliegueinfraestructuradispersionCollection: IDespliegueinfraestructuradispersion[] = [{ id: 53413 }];
      jest
        .spyOn(despliegueinfraestructuradispersionService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: despliegueinfraestructuradispersionCollection })));
      const additionalDespliegueinfraestructuradispersions = [idDespliegueinfraestructuradispersion];
      const expectedCollection: IDespliegueinfraestructuradispersion[] = [
        ...additionalDespliegueinfraestructuradispersions,
        ...despliegueinfraestructuradispersionCollection,
      ];
      jest
        .spyOn(despliegueinfraestructuradispersionService, 'addDespliegueinfraestructuradispersionToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pago });
      comp.ngOnInit();

      expect(despliegueinfraestructuradispersionService.query).toHaveBeenCalled();
      expect(despliegueinfraestructuradispersionService.addDespliegueinfraestructuradispersionToCollectionIfMissing).toHaveBeenCalledWith(
        despliegueinfraestructuradispersionCollection,
        ...additionalDespliegueinfraestructuradispersions.map(expect.objectContaining)
      );
      expect(comp.despliegueinfraestructuradispersionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pago: IPago = { id: 456 };
      const idDespliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion = { id: 66497 };
      pago.idDespliegueInfraestructuraTroncalDistribucion = idDespliegueInfraestructuraTroncalDistribucion;
      const idDespliegueinfraestructuradispersion: IDespliegueinfraestructuradispersion = { id: 35896 };
      pago.idDespliegueinfraestructuradispersion = idDespliegueinfraestructuradispersion;

      activatedRoute.data = of({ pago });
      comp.ngOnInit();

      expect(comp.despliegueInfraestructuraTroncalDistribucionsSharedCollection).toContain(idDespliegueInfraestructuraTroncalDistribucion);
      expect(comp.despliegueinfraestructuradispersionsSharedCollection).toContain(idDespliegueinfraestructuradispersion);
      expect(comp.pago).toEqual(pago);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPago>>();
      const pago = { id: 123 };
      jest.spyOn(pagoFormService, 'getPago').mockReturnValue(pago);
      jest.spyOn(pagoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pago });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pago }));
      saveSubject.complete();

      // THEN
      expect(pagoFormService.getPago).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pagoService.update).toHaveBeenCalledWith(expect.objectContaining(pago));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPago>>();
      const pago = { id: 123 };
      jest.spyOn(pagoFormService, 'getPago').mockReturnValue({ id: null });
      jest.spyOn(pagoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pago: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pago }));
      saveSubject.complete();

      // THEN
      expect(pagoFormService.getPago).toHaveBeenCalled();
      expect(pagoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPago>>();
      const pago = { id: 123 };
      jest.spyOn(pagoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pago });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pagoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDespliegueInfraestructuraTroncalDistribucion', () => {
      it('Should forward to despliegueInfraestructuraTroncalDistribucionService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(despliegueInfraestructuraTroncalDistribucionService, 'compareDespliegueInfraestructuraTroncalDistribucion');
        comp.compareDespliegueInfraestructuraTroncalDistribucion(entity, entity2);
        expect(
          despliegueInfraestructuraTroncalDistribucionService.compareDespliegueInfraestructuraTroncalDistribucion
        ).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDespliegueinfraestructuradispersion', () => {
      it('Should forward to despliegueinfraestructuradispersionService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(despliegueinfraestructuradispersionService, 'compareDespliegueinfraestructuradispersion');
        comp.compareDespliegueinfraestructuradispersion(entity, entity2);
        expect(despliegueinfraestructuradispersionService.compareDespliegueinfraestructuradispersion).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
