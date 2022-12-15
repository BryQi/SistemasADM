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
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';

import { DespliegueInfraestructuraTroncalDistribucionUpdateComponent } from './despliegue-infraestructura-troncal-distribucion-update.component';

describe('DespliegueInfraestructuraTroncalDistribucion Management Update Component', () => {
  let comp: DespliegueInfraestructuraTroncalDistribucionUpdateComponent;
  let fixture: ComponentFixture<DespliegueInfraestructuraTroncalDistribucionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let despliegueInfraestructuraTroncalDistribucionFormService: DespliegueInfraestructuraTroncalDistribucionFormService;
  let despliegueInfraestructuraTroncalDistribucionService: DespliegueInfraestructuraTroncalDistribucionService;
  let pozoService: PozoService;
  let proveedorService: ProveedorService;

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
    proveedorService = TestBed.inject(ProveedorService);

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

    it('Should call Proveedor query and add missing value', () => {
      const despliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion = { id: 456 };
      const razonSocial: IProveedor = { id: 80003 };
      despliegueInfraestructuraTroncalDistribucion.razonSocial = razonSocial;

      const proveedorCollection: IProveedor[] = [{ id: 37359 }];
      jest.spyOn(proveedorService, 'query').mockReturnValue(of(new HttpResponse({ body: proveedorCollection })));
      const additionalProveedors = [razonSocial];
      const expectedCollection: IProveedor[] = [...additionalProveedors, ...proveedorCollection];
      jest.spyOn(proveedorService, 'addProveedorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ despliegueInfraestructuraTroncalDistribucion });
      comp.ngOnInit();

      expect(proveedorService.query).toHaveBeenCalled();
      expect(proveedorService.addProveedorToCollectionIfMissing).toHaveBeenCalledWith(
        proveedorCollection,
        ...additionalProveedors.map(expect.objectContaining)
      );
      expect(comp.proveedorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const despliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion = { id: 456 };
      const pozo: IPozo = { id: 70073 };
      despliegueInfraestructuraTroncalDistribucion.pozos = [pozo];
      const razonSocial: IProveedor = { id: 25603 };
      despliegueInfraestructuraTroncalDistribucion.razonSocial = razonSocial;

      activatedRoute.data = of({ despliegueInfraestructuraTroncalDistribucion });
      comp.ngOnInit();

      expect(comp.pozosSharedCollection).toContain(pozo);
      expect(comp.proveedorsSharedCollection).toContain(razonSocial);
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
