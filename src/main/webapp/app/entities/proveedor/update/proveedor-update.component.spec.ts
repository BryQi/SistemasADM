import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProveedorFormService } from './proveedor-form.service';
import { ProveedorService } from '../service/proveedor.service';
import { IProveedor } from '../proveedor.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { ProveedorUpdateComponent } from './proveedor-update.component';

describe('Proveedor Management Update Component', () => {
  let comp: ProveedorUpdateComponent;
  let fixture: ComponentFixture<ProveedorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let proveedorFormService: ProveedorFormService;
  let proveedorService: ProveedorService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProveedorUpdateComponent],
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
      .overrideTemplate(ProveedorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProveedorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    proveedorFormService = TestBed.inject(ProveedorFormService);
    proveedorService = TestBed.inject(ProveedorService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call User query and add missing value', () => {
      const proveedor: IProveedor = { id: 456 };
      const user: IUser = { id: 88138 };
      proveedor.user = user;

      const userCollection: IUser[] = [{ id: 96911 }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ proveedor });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const proveedor: IProveedor = { id: 456 };
      const user: IUser = { id: 70574 };
      proveedor.user = user;

      activatedRoute.data = of({ proveedor });
      comp.ngOnInit();

      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.proveedor).toEqual(proveedor);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProveedor>>();
      const proveedor = { id: 123 };
      jest.spyOn(proveedorFormService, 'getProveedor').mockReturnValue(proveedor);
      jest.spyOn(proveedorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ proveedor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: proveedor }));
      saveSubject.complete();

      // THEN
      expect(proveedorFormService.getProveedor).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(proveedorService.update).toHaveBeenCalledWith(expect.objectContaining(proveedor));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProveedor>>();
      const proveedor = { id: 123 };
      jest.spyOn(proveedorFormService, 'getProveedor').mockReturnValue({ id: null });
      jest.spyOn(proveedorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ proveedor: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: proveedor }));
      saveSubject.complete();

      // THEN
      expect(proveedorFormService.getProveedor).toHaveBeenCalled();
      expect(proveedorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProveedor>>();
      const proveedor = { id: 123 };
      jest.spyOn(proveedorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ proveedor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(proveedorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
