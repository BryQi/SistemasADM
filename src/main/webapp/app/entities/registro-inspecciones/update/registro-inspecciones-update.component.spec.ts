import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RegistroInspeccionesFormService } from './registro-inspecciones-form.service';
import { RegistroInspeccionesService } from '../service/registro-inspecciones.service';
import { IRegistroInspecciones } from '../registro-inspecciones.model';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { PozoService } from 'app/entities/pozo/service/pozo.service';

import { RegistroInspeccionesUpdateComponent } from './registro-inspecciones-update.component';

describe('RegistroInspecciones Management Update Component', () => {
  let comp: RegistroInspeccionesUpdateComponent;
  let fixture: ComponentFixture<RegistroInspeccionesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let registroInspeccionesFormService: RegistroInspeccionesFormService;
  let registroInspeccionesService: RegistroInspeccionesService;
  let proveedorService: ProveedorService;
  let pozoService: PozoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RegistroInspeccionesUpdateComponent],
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
      .overrideTemplate(RegistroInspeccionesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RegistroInspeccionesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    registroInspeccionesFormService = TestBed.inject(RegistroInspeccionesFormService);
    registroInspeccionesService = TestBed.inject(RegistroInspeccionesService);
    proveedorService = TestBed.inject(ProveedorService);
    pozoService = TestBed.inject(PozoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Proveedor query and add missing value', () => {
      const registroInspecciones: IRegistroInspecciones = { id: 456 };
      const razonSocial: IProveedor = { id: 37353 };
      registroInspecciones.razonSocial = razonSocial;

      const proveedorCollection: IProveedor[] = [{ id: 28029 }];
      jest.spyOn(proveedorService, 'query').mockReturnValue(of(new HttpResponse({ body: proveedorCollection })));
      const additionalProveedors = [razonSocial];
      const expectedCollection: IProveedor[] = [...additionalProveedors, ...proveedorCollection];
      jest.spyOn(proveedorService, 'addProveedorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ registroInspecciones });
      comp.ngOnInit();

      expect(proveedorService.query).toHaveBeenCalled();
      expect(proveedorService.addProveedorToCollectionIfMissing).toHaveBeenCalledWith(
        proveedorCollection,
        ...additionalProveedors.map(expect.objectContaining)
      );
      expect(comp.proveedorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Pozo query and add missing value', () => {
      const registroInspecciones: IRegistroInspecciones = { id: 456 };
      const numeropozo: IPozo = { id: 38130 };
      registroInspecciones.numeropozo = numeropozo;

      const pozoCollection: IPozo[] = [{ id: 14501 }];
      jest.spyOn(pozoService, 'query').mockReturnValue(of(new HttpResponse({ body: pozoCollection })));
      const additionalPozos = [numeropozo];
      const expectedCollection: IPozo[] = [...additionalPozos, ...pozoCollection];
      jest.spyOn(pozoService, 'addPozoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ registroInspecciones });
      comp.ngOnInit();

      expect(pozoService.query).toHaveBeenCalled();
      expect(pozoService.addPozoToCollectionIfMissing).toHaveBeenCalledWith(
        pozoCollection,
        ...additionalPozos.map(expect.objectContaining)
      );
      expect(comp.pozosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const registroInspecciones: IRegistroInspecciones = { id: 456 };
      const razonSocial: IProveedor = { id: 1886 };
      registroInspecciones.razonSocial = razonSocial;
      const numeropozo: IPozo = { id: 47125 };
      registroInspecciones.numeropozo = numeropozo;

      activatedRoute.data = of({ registroInspecciones });
      comp.ngOnInit();

      expect(comp.proveedorsSharedCollection).toContain(razonSocial);
      expect(comp.pozosSharedCollection).toContain(numeropozo);
      expect(comp.registroInspecciones).toEqual(registroInspecciones);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRegistroInspecciones>>();
      const registroInspecciones = { id: 123 };
      jest.spyOn(registroInspeccionesFormService, 'getRegistroInspecciones').mockReturnValue(registroInspecciones);
      jest.spyOn(registroInspeccionesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ registroInspecciones });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: registroInspecciones }));
      saveSubject.complete();

      // THEN
      expect(registroInspeccionesFormService.getRegistroInspecciones).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(registroInspeccionesService.update).toHaveBeenCalledWith(expect.objectContaining(registroInspecciones));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRegistroInspecciones>>();
      const registroInspecciones = { id: 123 };
      jest.spyOn(registroInspeccionesFormService, 'getRegistroInspecciones').mockReturnValue({ id: null });
      jest.spyOn(registroInspeccionesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ registroInspecciones: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: registroInspecciones }));
      saveSubject.complete();

      // THEN
      expect(registroInspeccionesFormService.getRegistroInspecciones).toHaveBeenCalled();
      expect(registroInspeccionesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRegistroInspecciones>>();
      const registroInspecciones = { id: 123 };
      jest.spyOn(registroInspeccionesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ registroInspecciones });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(registroInspeccionesService.update).toHaveBeenCalled();
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

    describe('comparePozo', () => {
      it('Should forward to pozoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pozoService, 'comparePozo');
        comp.comparePozo(entity, entity2);
        expect(pozoService.comparePozo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
