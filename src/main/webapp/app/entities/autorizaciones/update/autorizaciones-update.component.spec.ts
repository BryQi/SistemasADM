import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AutorizacionesFormService } from './autorizaciones-form.service';
import { AutorizacionesService } from '../service/autorizaciones.service';
import { IAutorizaciones } from '../autorizaciones.model';
import { IRegistroInspecciones } from 'app/entities/registro-inspecciones/registro-inspecciones.model';
import { RegistroInspeccionesService } from 'app/entities/registro-inspecciones/service/registro-inspecciones.service';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';

import { AutorizacionesUpdateComponent } from './autorizaciones-update.component';

describe('Autorizaciones Management Update Component', () => {
  let comp: AutorizacionesUpdateComponent;
  let fixture: ComponentFixture<AutorizacionesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let autorizacionesFormService: AutorizacionesFormService;
  let autorizacionesService: AutorizacionesService;
  let registroInspeccionesService: RegistroInspeccionesService;
  let proveedorService: ProveedorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AutorizacionesUpdateComponent],
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
      .overrideTemplate(AutorizacionesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AutorizacionesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    autorizacionesFormService = TestBed.inject(AutorizacionesFormService);
    autorizacionesService = TestBed.inject(AutorizacionesService);
    registroInspeccionesService = TestBed.inject(RegistroInspeccionesService);
    proveedorService = TestBed.inject(ProveedorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RegistroInspecciones query and add missing value', () => {
      const autorizaciones: IAutorizaciones = { id: 456 };
      const registroInspecciones: IRegistroInspecciones = { id: 45869 };
      autorizaciones.registroInspecciones = registroInspecciones;

      const registroInspeccionesCollection: IRegistroInspecciones[] = [{ id: 42018 }];
      jest.spyOn(registroInspeccionesService, 'query').mockReturnValue(of(new HttpResponse({ body: registroInspeccionesCollection })));
      const additionalRegistroInspecciones = [registroInspecciones];
      const expectedCollection: IRegistroInspecciones[] = [...additionalRegistroInspecciones, ...registroInspeccionesCollection];
      jest.spyOn(registroInspeccionesService, 'addRegistroInspeccionesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ autorizaciones });
      comp.ngOnInit();

      expect(registroInspeccionesService.query).toHaveBeenCalled();
      expect(registroInspeccionesService.addRegistroInspeccionesToCollectionIfMissing).toHaveBeenCalledWith(
        registroInspeccionesCollection,
        ...additionalRegistroInspecciones.map(expect.objectContaining)
      );
      expect(comp.registroInspeccionesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Proveedor query and add missing value', () => {
      const autorizaciones: IAutorizaciones = { id: 456 };
      const idProveedor: IProveedor = { id: 12675 };
      autorizaciones.idProveedor = idProveedor;

      const proveedorCollection: IProveedor[] = [{ id: 91927 }];
      jest.spyOn(proveedorService, 'query').mockReturnValue(of(new HttpResponse({ body: proveedorCollection })));
      const additionalProveedors = [idProveedor];
      const expectedCollection: IProveedor[] = [...additionalProveedors, ...proveedorCollection];
      jest.spyOn(proveedorService, 'addProveedorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ autorizaciones });
      comp.ngOnInit();

      expect(proveedorService.query).toHaveBeenCalled();
      expect(proveedorService.addProveedorToCollectionIfMissing).toHaveBeenCalledWith(
        proveedorCollection,
        ...additionalProveedors.map(expect.objectContaining)
      );
      expect(comp.proveedorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const autorizaciones: IAutorizaciones = { id: 456 };
      const registroInspecciones: IRegistroInspecciones = { id: 71331 };
      autorizaciones.registroInspecciones = registroInspecciones;
      const idProveedor: IProveedor = { id: 68432 };
      autorizaciones.idProveedor = idProveedor;

      activatedRoute.data = of({ autorizaciones });
      comp.ngOnInit();

      expect(comp.registroInspeccionesSharedCollection).toContain(registroInspecciones);
      expect(comp.proveedorsSharedCollection).toContain(idProveedor);
      expect(comp.autorizaciones).toEqual(autorizaciones);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutorizaciones>>();
      const autorizaciones = { id: 123 };
      jest.spyOn(autorizacionesFormService, 'getAutorizaciones').mockReturnValue(autorizaciones);
      jest.spyOn(autorizacionesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autorizaciones });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: autorizaciones }));
      saveSubject.complete();

      // THEN
      expect(autorizacionesFormService.getAutorizaciones).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(autorizacionesService.update).toHaveBeenCalledWith(expect.objectContaining(autorizaciones));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutorizaciones>>();
      const autorizaciones = { id: 123 };
      jest.spyOn(autorizacionesFormService, 'getAutorizaciones').mockReturnValue({ id: null });
      jest.spyOn(autorizacionesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autorizaciones: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: autorizaciones }));
      saveSubject.complete();

      // THEN
      expect(autorizacionesFormService.getAutorizaciones).toHaveBeenCalled();
      expect(autorizacionesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAutorizaciones>>();
      const autorizaciones = { id: 123 };
      jest.spyOn(autorizacionesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ autorizaciones });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(autorizacionesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRegistroInspecciones', () => {
      it('Should forward to registroInspeccionesService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(registroInspeccionesService, 'compareRegistroInspecciones');
        comp.compareRegistroInspecciones(entity, entity2);
        expect(registroInspeccionesService.compareRegistroInspecciones).toHaveBeenCalledWith(entity, entity2);
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
