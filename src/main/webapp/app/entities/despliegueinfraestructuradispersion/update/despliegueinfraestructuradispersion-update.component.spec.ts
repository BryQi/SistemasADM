import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DespliegueinfraestructuradispersionFormService } from './despliegueinfraestructuradispersion-form.service';
import { DespliegueinfraestructuradispersionService } from '../service/despliegueinfraestructuradispersion.service';
import { IDespliegueinfraestructuradispersion } from '../despliegueinfraestructuradispersion.model';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { PozoService } from 'app/entities/pozo/service/pozo.service';
import { IDespliegueInfraestructuraTroncalDistribucion } from 'app/entities/despliegue-infraestructura-troncal-distribucion/despliegue-infraestructura-troncal-distribucion.model';
import { DespliegueInfraestructuraTroncalDistribucionService } from 'app/entities/despliegue-infraestructura-troncal-distribucion/service/despliegue-infraestructura-troncal-distribucion.service';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';

import { DespliegueinfraestructuradispersionUpdateComponent } from './despliegueinfraestructuradispersion-update.component';

describe('Despliegueinfraestructuradispersion Management Update Component', () => {
  let comp: DespliegueinfraestructuradispersionUpdateComponent;
  let fixture: ComponentFixture<DespliegueinfraestructuradispersionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let despliegueinfraestructuradispersionFormService: DespliegueinfraestructuradispersionFormService;
  let despliegueinfraestructuradispersionService: DespliegueinfraestructuradispersionService;
  let pozoService: PozoService;
  let despliegueInfraestructuraTroncalDistribucionService: DespliegueInfraestructuraTroncalDistribucionService;
  let proveedorService: ProveedorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DespliegueinfraestructuradispersionUpdateComponent],
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
      .overrideTemplate(DespliegueinfraestructuradispersionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DespliegueinfraestructuradispersionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    despliegueinfraestructuradispersionFormService = TestBed.inject(DespliegueinfraestructuradispersionFormService);
    despliegueinfraestructuradispersionService = TestBed.inject(DespliegueinfraestructuradispersionService);
    pozoService = TestBed.inject(PozoService);
    despliegueInfraestructuraTroncalDistribucionService = TestBed.inject(DespliegueInfraestructuraTroncalDistribucionService);
    proveedorService = TestBed.inject(ProveedorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pozo query and add missing value', () => {
      const despliegueinfraestructuradispersion: IDespliegueinfraestructuradispersion = { id: 456 };
      const pozos: IPozo[] = [{ id: 94196 }];
      despliegueinfraestructuradispersion.pozos = pozos;

      const pozoCollection: IPozo[] = [{ id: 31601 }];
      jest.spyOn(pozoService, 'query').mockReturnValue(of(new HttpResponse({ body: pozoCollection })));
      const additionalPozos = [...pozos];
      const expectedCollection: IPozo[] = [...additionalPozos, ...pozoCollection];
      jest.spyOn(pozoService, 'addPozoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ despliegueinfraestructuradispersion });
      comp.ngOnInit();

      expect(pozoService.query).toHaveBeenCalled();
      expect(pozoService.addPozoToCollectionIfMissing).toHaveBeenCalledWith(
        pozoCollection,
        ...additionalPozos.map(expect.objectContaining)
      );
      expect(comp.pozosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DespliegueInfraestructuraTroncalDistribucion query and add missing value', () => {
      const despliegueinfraestructuradispersion: IDespliegueinfraestructuradispersion = { id: 456 };
      const idDespliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion = { id: 90277 };
      despliegueinfraestructuradispersion.idDespliegueInfraestructuraTroncalDistribucion = idDespliegueInfraestructuraTroncalDistribucion;

      const despliegueInfraestructuraTroncalDistribucionCollection: IDespliegueInfraestructuraTroncalDistribucion[] = [{ id: 70679 }];
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

      activatedRoute.data = of({ despliegueinfraestructuradispersion });
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

    it('Should call Proveedor query and add missing value', () => {
      const despliegueinfraestructuradispersion: IDespliegueinfraestructuradispersion = { id: 456 };
      const idProveedor: IProveedor = { id: 45443 };
      despliegueinfraestructuradispersion.idProveedor = idProveedor;

      const proveedorCollection: IProveedor[] = [{ id: 2544 }];
      jest.spyOn(proveedorService, 'query').mockReturnValue(of(new HttpResponse({ body: proveedorCollection })));
      const additionalProveedors = [idProveedor];
      const expectedCollection: IProveedor[] = [...additionalProveedors, ...proveedorCollection];
      jest.spyOn(proveedorService, 'addProveedorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ despliegueinfraestructuradispersion });
      comp.ngOnInit();

      expect(proveedorService.query).toHaveBeenCalled();
      expect(proveedorService.addProveedorToCollectionIfMissing).toHaveBeenCalledWith(
        proveedorCollection,
        ...additionalProveedors.map(expect.objectContaining)
      );
      expect(comp.proveedorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const despliegueinfraestructuradispersion: IDespliegueinfraestructuradispersion = { id: 456 };
      const pozo: IPozo = { id: 67104 };
      despliegueinfraestructuradispersion.pozos = [pozo];
      const idDespliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion = { id: 30329 };
      despliegueinfraestructuradispersion.idDespliegueInfraestructuraTroncalDistribucion = idDespliegueInfraestructuraTroncalDistribucion;
      const idProveedor: IProveedor = { id: 58191 };
      despliegueinfraestructuradispersion.idProveedor = idProveedor;

      activatedRoute.data = of({ despliegueinfraestructuradispersion });
      comp.ngOnInit();

      expect(comp.pozosSharedCollection).toContain(pozo);
      expect(comp.despliegueInfraestructuraTroncalDistribucionsSharedCollection).toContain(idDespliegueInfraestructuraTroncalDistribucion);
      expect(comp.proveedorsSharedCollection).toContain(idProveedor);
      expect(comp.despliegueinfraestructuradispersion).toEqual(despliegueinfraestructuradispersion);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDespliegueinfraestructuradispersion>>();
      const despliegueinfraestructuradispersion = { id: 123 };
      jest
        .spyOn(despliegueinfraestructuradispersionFormService, 'getDespliegueinfraestructuradispersion')
        .mockReturnValue(despliegueinfraestructuradispersion);
      jest.spyOn(despliegueinfraestructuradispersionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ despliegueinfraestructuradispersion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: despliegueinfraestructuradispersion }));
      saveSubject.complete();

      // THEN
      expect(despliegueinfraestructuradispersionFormService.getDespliegueinfraestructuradispersion).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(despliegueinfraestructuradispersionService.update).toHaveBeenCalledWith(
        expect.objectContaining(despliegueinfraestructuradispersion)
      );
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDespliegueinfraestructuradispersion>>();
      const despliegueinfraestructuradispersion = { id: 123 };
      jest.spyOn(despliegueinfraestructuradispersionFormService, 'getDespliegueinfraestructuradispersion').mockReturnValue({ id: null });
      jest.spyOn(despliegueinfraestructuradispersionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ despliegueinfraestructuradispersion: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: despliegueinfraestructuradispersion }));
      saveSubject.complete();

      // THEN
      expect(despliegueinfraestructuradispersionFormService.getDespliegueinfraestructuradispersion).toHaveBeenCalled();
      expect(despliegueinfraestructuradispersionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDespliegueinfraestructuradispersion>>();
      const despliegueinfraestructuradispersion = { id: 123 };
      jest.spyOn(despliegueinfraestructuradispersionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ despliegueinfraestructuradispersion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(despliegueinfraestructuradispersionService.update).toHaveBeenCalled();
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

    describe('compareProveedor', () => {
      it('Should forward to proveedorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(proveedorService, 'compareProveedor');
        comp.compareProveedor(entity, entity2);
        expect(proveedorService.compareProveedor).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
