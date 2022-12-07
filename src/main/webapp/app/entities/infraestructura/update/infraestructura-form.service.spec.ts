import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../infraestructura.test-samples';

import { InfraestructuraFormService } from './infraestructura-form.service';

describe('Infraestructura Form Service', () => {
  let service: InfraestructuraFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InfraestructuraFormService);
  });

  describe('Service methods', () => {
    describe('createInfraestructuraFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInfraestructuraFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipo: expect.any(Object),
            createdAt: expect.any(Object),
            idProveedor: expect.any(Object),
            pozos: expect.any(Object),
          })
        );
      });

      it('passing IInfraestructura should create a new form with FormGroup', () => {
        const formGroup = service.createInfraestructuraFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipo: expect.any(Object),
            createdAt: expect.any(Object),
            idProveedor: expect.any(Object),
            pozos: expect.any(Object),
          })
        );
      });
    });

    describe('getInfraestructura', () => {
      it('should return NewInfraestructura for default Infraestructura initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createInfraestructuraFormGroup(sampleWithNewData);

        const infraestructura = service.getInfraestructura(formGroup) as any;

        expect(infraestructura).toMatchObject(sampleWithNewData);
      });

      it('should return NewInfraestructura for empty Infraestructura initial value', () => {
        const formGroup = service.createInfraestructuraFormGroup();

        const infraestructura = service.getInfraestructura(formGroup) as any;

        expect(infraestructura).toMatchObject({});
      });

      it('should return IInfraestructura', () => {
        const formGroup = service.createInfraestructuraFormGroup(sampleWithRequiredData);

        const infraestructura = service.getInfraestructura(formGroup) as any;

        expect(infraestructura).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInfraestructura should not enable id FormControl', () => {
        const formGroup = service.createInfraestructuraFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInfraestructura should disable id FormControl', () => {
        const formGroup = service.createInfraestructuraFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
