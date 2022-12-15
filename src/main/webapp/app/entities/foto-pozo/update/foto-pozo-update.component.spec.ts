import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FotoPozoFormService } from './foto-pozo-form.service';
import { FotoPozoService } from '../service/foto-pozo.service';
import { IFotoPozo } from '../foto-pozo.model';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { PozoService } from 'app/entities/pozo/service/pozo.service';

import { FotoPozoUpdateComponent } from './foto-pozo-update.component';

describe('FotoPozo Management Update Component', () => {
  let comp: FotoPozoUpdateComponent;
  let fixture: ComponentFixture<FotoPozoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fotoPozoFormService: FotoPozoFormService;
  let fotoPozoService: FotoPozoService;
  let pozoService: PozoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FotoPozoUpdateComponent],
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
      .overrideTemplate(FotoPozoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FotoPozoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fotoPozoFormService = TestBed.inject(FotoPozoFormService);
    fotoPozoService = TestBed.inject(FotoPozoService);
    pozoService = TestBed.inject(PozoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pozo query and add missing value', () => {
      const fotoPozo: IFotoPozo = { id: 456 };
      const numeropozo: IPozo = { id: 65678 };
      fotoPozo.numeropozo = numeropozo;

      const pozoCollection: IPozo[] = [{ id: 69448 }];
      jest.spyOn(pozoService, 'query').mockReturnValue(of(new HttpResponse({ body: pozoCollection })));
      const additionalPozos = [numeropozo];
      const expectedCollection: IPozo[] = [...additionalPozos, ...pozoCollection];
      jest.spyOn(pozoService, 'addPozoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ fotoPozo });
      comp.ngOnInit();

      expect(pozoService.query).toHaveBeenCalled();
      expect(pozoService.addPozoToCollectionIfMissing).toHaveBeenCalledWith(
        pozoCollection,
        ...additionalPozos.map(expect.objectContaining)
      );
      expect(comp.pozosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const fotoPozo: IFotoPozo = { id: 456 };
      const numeropozo: IPozo = { id: 33652 };
      fotoPozo.numeropozo = numeropozo;

      activatedRoute.data = of({ fotoPozo });
      comp.ngOnInit();

      expect(comp.pozosSharedCollection).toContain(numeropozo);
      expect(comp.fotoPozo).toEqual(fotoPozo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFotoPozo>>();
      const fotoPozo = { id: 123 };
      jest.spyOn(fotoPozoFormService, 'getFotoPozo').mockReturnValue(fotoPozo);
      jest.spyOn(fotoPozoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoPozo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoPozo }));
      saveSubject.complete();

      // THEN
      expect(fotoPozoFormService.getFotoPozo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fotoPozoService.update).toHaveBeenCalledWith(expect.objectContaining(fotoPozo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFotoPozo>>();
      const fotoPozo = { id: 123 };
      jest.spyOn(fotoPozoFormService, 'getFotoPozo').mockReturnValue({ id: null });
      jest.spyOn(fotoPozoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoPozo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fotoPozo }));
      saveSubject.complete();

      // THEN
      expect(fotoPozoFormService.getFotoPozo).toHaveBeenCalled();
      expect(fotoPozoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFotoPozo>>();
      const fotoPozo = { id: 123 };
      jest.spyOn(fotoPozoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fotoPozo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fotoPozoService.update).toHaveBeenCalled();
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
  });
});
