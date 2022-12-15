import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../autorizaciones.test-samples';

import { AutorizacionesFormService } from './autorizaciones-form.service';

describe('Autorizaciones Form Service', () => {
  let service: AutorizacionesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AutorizacionesFormService);
  });

  describe('Service methods', () => {
    describe('createAutorizacionesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAutorizacionesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cliente: expect.any(Object),
            direccionOrigen: expect.any(Object),
            fechaOperacion: expect.any(Object),
            ventanaTrabajo: expect.any(Object),
            contactoTecnico: expect.any(Object),
            tipoTrabajo: expect.any(Object),
            observaciones: expect.any(Object),
            createdAt: expect.any(Object),
            direccionDestino: expect.any(Object),
            razonSocial: expect.any(Object),
            numeropozo: expect.any(Object),
          })
        );
      });

      it('passing IAutorizaciones should create a new form with FormGroup', () => {
        const formGroup = service.createAutorizacionesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cliente: expect.any(Object),
            direccionOrigen: expect.any(Object),
            fechaOperacion: expect.any(Object),
            ventanaTrabajo: expect.any(Object),
            contactoTecnico: expect.any(Object),
            tipoTrabajo: expect.any(Object),
            observaciones: expect.any(Object),
            createdAt: expect.any(Object),
            direccionDestino: expect.any(Object),
            razonSocial: expect.any(Object),
            numeropozo: expect.any(Object),
          })
        );
      });
    });

    describe('getAutorizaciones', () => {
      it('should return NewAutorizaciones for default Autorizaciones initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAutorizacionesFormGroup(sampleWithNewData);

        const autorizaciones = service.getAutorizaciones(formGroup) as any;

        expect(autorizaciones).toMatchObject(sampleWithNewData);
      });

      it('should return NewAutorizaciones for empty Autorizaciones initial value', () => {
        const formGroup = service.createAutorizacionesFormGroup();

        const autorizaciones = service.getAutorizaciones(formGroup) as any;

        expect(autorizaciones).toMatchObject({});
      });

      it('should return IAutorizaciones', () => {
        const formGroup = service.createAutorizacionesFormGroup(sampleWithRequiredData);

        const autorizaciones = service.getAutorizaciones(formGroup) as any;

        expect(autorizaciones).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAutorizaciones should not enable id FormControl', () => {
        const formGroup = service.createAutorizacionesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAutorizaciones should disable id FormControl', () => {
        const formGroup = service.createAutorizacionesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
