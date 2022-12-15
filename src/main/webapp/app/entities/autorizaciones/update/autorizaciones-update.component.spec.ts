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
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { PozoService } from 'app/entities/pozo/service/pozo.service';

import { AutorizacionesUpdateComponent } from './autorizaciones-update.component';

describe('Autorizaciones Management Update Component', () => {
  let comp: AutorizacionesUpdateComponent;
  let fixture: ComponentFixture<AutorizacionesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let autorizacionesFormService: AutorizacionesFormService;
  let autorizacionesService: AutorizacionesService;
  let proveedorService: ProveedorService;
  let pozoService: PozoService;

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
    proveedorService = TestBed.inject(ProveedorService);
    pozoService = TestBed.inject(PozoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Proveedor query and add missing value', () => {
      const autorizaciones: IAutorizaciones = { id: 456 };
      const razonSocial: IProveedor = { id: 12675 };
      autorizaciones.razonSocial = razonSocial;

      const proveedorCollection: IProveedor[] = [{ id: 91927 }];
      jest.spyOn(proveedorService, 'query').mockReturnValue(of(new HttpResponse({ body: proveedorCollection })));
      const additionalProveedors = [razonSocial];
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

    it('Should call Pozo query and add missing value', () => {
      const autorizaciones: IAutorizaciones = { id: 456 };
      const numeropozo: IPozo = { id: 80601 };
      autorizaciones.numeropozo = numeropozo;

      const pozoCollection: IPozo[] = [{ id: 42451 }];
      jest.spyOn(pozoService, 'query').mockReturnValue(of(new HttpResponse({ body: pozoCollection })));
      const additionalPozos = [numeropozo];
      const expectedCollection: IPozo[] = [...additionalPozos, ...pozoCollection];
      jest.spyOn(pozoService, 'addPozoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ autorizaciones });
      comp.ngOnInit();

      expect(pozoService.query).toHaveBeenCalled();
      expect(pozoService.addPozoToCollectionIfMissing).toHaveBeenCalledWith(
        pozoCollection,
        ...additionalPozos.map(expect.objectContaining)
      );
      expect(comp.pozosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const autorizaciones: IAutorizaciones = { id: 456 };
      const razonSocial: IProveedor = { id: 68432 };
      autorizaciones.razonSocial = razonSocial;
      const numeropozo: IPozo = { id: 46250 };
      autorizaciones.numeropozo = numeropozo;

      activatedRoute.data = of({ autorizaciones });
      comp.ngOnInit();

      expect(comp.proveedorsSharedCollection).toContain(razonSocial);
      expect(comp.pozosSharedCollection).toContain(numeropozo);
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
