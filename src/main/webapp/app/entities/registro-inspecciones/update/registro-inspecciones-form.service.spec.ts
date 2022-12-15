import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../registro-inspecciones.test-samples';

import { RegistroInspeccionesFormService } from './registro-inspecciones-form.service';

describe('RegistroInspecciones Form Service', () => {
  let service: RegistroInspeccionesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegistroInspeccionesFormService);
  });

  describe('Service methods', () => {
    describe('createRegistroInspeccionesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRegistroInspeccionesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cumpleAutorizacion: expect.any(Object),
            numeroAutorizacion: expect.any(Object),
            cumpleSenaletica: expect.any(Object),
            cumpleConosSeguridad: expect.any(Object),
            cumpleEtiquetado: expect.any(Object),
            cumpleArregloCables: expect.any(Object),
            cumplelimpiezaOrdenPozo: expect.any(Object),
            createdAt: expect.any(Object),
            razonSocial: expect.any(Object),
            numeropozo: expect.any(Object),
          })
        );
      });

      it('passing IRegistroInspecciones should create a new form with FormGroup', () => {
        const formGroup = service.createRegistroInspeccionesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cumpleAutorizacion: expect.any(Object),
            numeroAutorizacion: expect.any(Object),
            cumpleSenaletica: expect.any(Object),
            cumpleConosSeguridad: expect.any(Object),
            cumpleEtiquetado: expect.any(Object),
            cumpleArregloCables: expect.any(Object),
            cumplelimpiezaOrdenPozo: expect.any(Object),
            createdAt: expect.any(Object),
            razonSocial: expect.any(Object),
            numeropozo: expect.any(Object),
          })
        );
      });
    });

    describe('getRegistroInspecciones', () => {
      it('should return NewRegistroInspecciones for default RegistroInspecciones initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRegistroInspeccionesFormGroup(sampleWithNewData);

        const registroInspecciones = service.getRegistroInspecciones(formGroup) as any;

        expect(registroInspecciones).toMatchObject(sampleWithNewData);
      });

      it('should return NewRegistroInspecciones for empty RegistroInspecciones initial value', () => {
        const formGroup = service.createRegistroInspeccionesFormGroup();

        const registroInspecciones = service.getRegistroInspecciones(formGroup) as any;

        expect(registroInspecciones).toMatchObject({});
      });

      it('should return IRegistroInspecciones', () => {
        const formGroup = service.createRegistroInspeccionesFormGroup(sampleWithRequiredData);

        const registroInspecciones = service.getRegistroInspecciones(formGroup) as any;

        expect(registroInspecciones).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRegistroInspecciones should not enable id FormControl', () => {
        const formGroup = service.createRegistroInspeccionesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRegistroInspecciones should disable id FormControl', () => {
        const formGroup = service.createRegistroInspeccionesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
