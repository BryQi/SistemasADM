import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InfraestructuraFormService } from './infraestructura-form.service';
import { InfraestructuraService } from '../service/infraestructura.service';
import { IInfraestructura } from '../infraestructura.model';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { PozoService } from 'app/entities/pozo/service/pozo.service';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';

import { InfraestructuraUpdateComponent } from './infraestructura-update.component';

describe('Infraestructura Management Update Component', () => {
  let comp: InfraestructuraUpdateComponent;
  let fixture: ComponentFixture<InfraestructuraUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let infraestructuraFormService: InfraestructuraFormService;
  let infraestructuraService: InfraestructuraService;
  let pozoService: PozoService;
  let proveedorService: ProveedorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InfraestructuraUpdateComponent],
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
      .overrideTemplate(InfraestructuraUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InfraestructuraUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    infraestructuraFormService = TestBed.inject(InfraestructuraFormService);
    infraestructuraService = TestBed.inject(InfraestructuraService);
    pozoService = TestBed.inject(PozoService);
    proveedorService = TestBed.inject(ProveedorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pozo query and add missing value', () => {
      const infraestructura: IInfraestructura = { id: 456 };
      const pozos: IPozo[] = [{ id: 78503 }];
      infraestructura.pozos = pozos;

      const pozoCollection: IPozo[] = [{ id: 49994 }];
      jest.spyOn(pozoService, 'query').mockReturnValue(of(new HttpResponse({ body: pozoCollection })));
      const additionalPozos = [...pozos];
      const expectedCollection: IPozo[] = [...additionalPozos, ...pozoCollection];
      jest.spyOn(pozoService, 'addPozoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ infraestructura });
      comp.ngOnInit();

      expect(pozoService.query).toHaveBeenCalled();
      expect(pozoService.addPozoToCollectionIfMissing).toHaveBeenCalledWith(
        pozoCollection,
        ...additionalPozos.map(expect.objectContaining)
      );
      expect(comp.pozosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Proveedor query and add missing value', () => {
      const infraestructura: IInfraestructura = { id: 456 };
      const idProveedor: IProveedor = { id: 82963 };
      infraestructura.idProveedor = idProveedor;

      const proveedorCollection: IProveedor[] = [{ id: 95486 }];
      jest.spyOn(proveedorService, 'query').mockReturnValue(of(new HttpResponse({ body: proveedorCollection })));
      const additionalProveedors = [idProveedor];
      const expectedCollection: IProveedor[] = [...additionalProveedors, ...proveedorCollection];
      jest.spyOn(proveedorService, 'addProveedorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ infraestructura });
      comp.ngOnInit();

      expect(proveedorService.query).toHaveBeenCalled();
      expect(proveedorService.addProveedorToCollectionIfMissing).toHaveBeenCalledWith(
        proveedorCollection,
        ...additionalProveedors.map(expect.objectContaining)
      );
      expect(comp.proveedorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const infraestructura: IInfraestructura = { id: 456 };
      const pozo: IPozo = { id: 85630 };
      infraestructura.pozos = [pozo];
      const idProveedor: IProveedor = { id: 66994 };
      infraestructura.idProveedor = idProveedor;

      activatedRoute.data = of({ infraestructura });
      comp.ngOnInit();

      expect(comp.pozosSharedCollection).toContain(pozo);
      expect(comp.proveedorsSharedCollection).toContain(idProveedor);
      expect(comp.infraestructura).toEqual(infraestructura);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInfraestructura>>();
      const infraestructura = { id: 123 };
      jest.spyOn(infraestructuraFormService, 'getInfraestructura').mockReturnValue(infraestructura);
      jest.spyOn(infraestructuraService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ infraestructura });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: infraestructura }));
      saveSubject.complete();

      // THEN
      expect(infraestructuraFormService.getInfraestructura).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(infraestructuraService.update).toHaveBeenCalledWith(expect.objectContaining(infraestructura));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInfraestructura>>();
      const infraestructura = { id: 123 };
      jest.spyOn(infraestructuraFormService, 'getInfraestructura').mockReturnValue({ id: null });
      jest.spyOn(infraestructuraService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ infraestructura: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: infraestructura }));
      saveSubject.complete();

      // THEN
      expect(infraestructuraFormService.getInfraestructura).toHaveBeenCalled();
      expect(infraestructuraService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInfraestructura>>();
      const infraestructura = { id: 123 };
      jest.spyOn(infraestructuraService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ infraestructura });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(infraestructuraService.update).toHaveBeenCalled();
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