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
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';
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
  let proveedorService: ProveedorService;
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
    proveedorService = TestBed.inject(ProveedorService);
    despliegueInfraestructuraTroncalDistribucionService = TestBed.inject(DespliegueInfraestructuraTroncalDistribucionService);
    despliegueinfraestructuradispersionService = TestBed.inject(DespliegueinfraestructuradispersionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Proveedor query and add missing value', () => {
      const pago: IPago = { id: 456 };
      const razonSocial: IProveedor = { id: 69587 };
      pago.razonSocial = razonSocial;

      const proveedorCollection: IProveedor[] = [{ id: 37700 }];
      jest.spyOn(proveedorService, 'query').mockReturnValue(of(new HttpResponse({ body: proveedorCollection })));
      const additionalProveedors = [razonSocial];
      const expectedCollection: IProveedor[] = [...additionalProveedors, ...proveedorCollection];
      jest.spyOn(proveedorService, 'addProveedorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pago });
      comp.ngOnInit();

      expect(proveedorService.query).toHaveBeenCalled();
      expect(proveedorService.addProveedorToCollectionIfMissing).toHaveBeenCalledWith(
        proveedorCollection,
        ...additionalProveedors.map(expect.objectContaining)
      );
      expect(comp.proveedorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DespliegueInfraestructuraTroncalDistribucion query and add missing value', () => {
      const pago: IPago = { id: 456 };
      const calculoValorPago: IDespliegueInfraestructuraTroncalDistribucion = { id: 57471 };
      pago.calculoValorPago = calculoValorPago;

      const despliegueInfraestructuraTroncalDistribucionCollection: IDespliegueInfraestructuraTroncalDistribucion[] = [{ id: 43382 }];
      jest
        .spyOn(despliegueInfraestructuraTroncalDistribucionService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: despliegueInfraestructuraTroncalDistribucionCollection })));
      const additionalDespliegueInfraestructuraTroncalDistribucions = [calculoValorPago];
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
      const calculoValorPagoD: IDespliegueinfraestructuradispersion = { id: 97415 };
      pago.calculoValorPagoD = calculoValorPagoD;

      const despliegueinfraestructuradispersionCollection: IDespliegueinfraestructuradispersion[] = [{ id: 53413 }];
      jest
        .spyOn(despliegueinfraestructuradispersionService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: despliegueinfraestructuradispersionCollection })));
      const additionalDespliegueinfraestructuradispersions = [calculoValorPagoD];
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
      const razonSocial: IProveedor = { id: 8694 };
      pago.razonSocial = razonSocial;
      const calculoValorPago: IDespliegueInfraestructuraTroncalDistribucion = { id: 66497 };
      pago.calculoValorPago = calculoValorPago;
      const calculoValorPagoD: IDespliegueinfraestructuradispersion = { id: 35896 };
      pago.calculoValorPagoD = calculoValorPagoD;

      activatedRoute.data = of({ pago });
      comp.ngOnInit();

      expect(comp.proveedorsSharedCollection).toContain(razonSocial);
      expect(comp.despliegueInfraestructuraTroncalDistribucionsSharedCollection).toContain(calculoValorPago);
      expect(comp.despliegueinfraestructuradispersionsSharedCollection).toContain(calculoValorPagoD);
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
    describe('compareProveedor', () => {
      it('Should forward to proveedorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(proveedorService, 'compareProveedor');
        comp.compareProveedor(entity, entity2);
        expect(proveedorService.compareProveedor).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
