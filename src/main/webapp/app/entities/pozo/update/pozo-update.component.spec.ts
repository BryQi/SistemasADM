import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PozoFormService } from './pozo-form.service';
import { PozoService } from '../service/pozo.service';
import { IPozo } from '../pozo.model';

import { PozoUpdateComponent } from './pozo-update.component';

describe('Pozo Management Update Component', () => {
  let comp: PozoUpdateComponent;
  let fixture: ComponentFixture<PozoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pozoFormService: PozoFormService;
  let pozoService: PozoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PozoUpdateComponent],
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
      .overrideTemplate(PozoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PozoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pozoFormService = TestBed.inject(PozoFormService);
    pozoService = TestBed.inject(PozoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const pozo: IPozo = { id: 456 };

      activatedRoute.data = of({ pozo });
      comp.ngOnInit();

      expect(comp.pozo).toEqual(pozo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPozo>>();
      const pozo = { id: 123 };
      jest.spyOn(pozoFormService, 'getPozo').mockReturnValue(pozo);
      jest.spyOn(pozoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pozo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pozo }));
      saveSubject.complete();

      // THEN
      expect(pozoFormService.getPozo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pozoService.update).toHaveBeenCalledWith(expect.objectContaining(pozo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPozo>>();
      const pozo = { id: 123 };
      jest.spyOn(pozoFormService, 'getPozo').mockReturnValue({ id: null });
      jest.spyOn(pozoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pozo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pozo }));
      saveSubject.complete();

      // THEN
      expect(pozoFormService.getPozo).toHaveBeenCalled();
      expect(pozoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPozo>>();
      const pozo = { id: 123 };
      jest.spyOn(pozoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pozo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pozoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
