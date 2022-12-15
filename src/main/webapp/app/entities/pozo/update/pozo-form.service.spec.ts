import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../pozo.test-samples';

import { PozoFormService } from './pozo-form.service';

describe('Pozo Form Service', () => {
  let service: PozoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PozoFormService);
  });

  describe('Service methods', () => {
    describe('createPozoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPozoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numeropozo: expect.any(Object),
            direccion: expect.any(Object),
            tipopozo: expect.any(Object),
            createdAt: expect.any(Object),
            longitud: expect.any(Object),
            latitud: expect.any(Object),
          })
        );
      });

      it('passing IPozo should create a new form with FormGroup', () => {
        const formGroup = service.createPozoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numeropozo: expect.any(Object),
            direccion: expect.any(Object),
            tipopozo: expect.any(Object),
            createdAt: expect.any(Object),
            longitud: expect.any(Object),
            latitud: expect.any(Object),
          })
        );
      });
    });

    describe('getPozo', () => {
      it('should return NewPozo for default Pozo initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPozoFormGroup(sampleWithNewData);

        const pozo = service.getPozo(formGroup) as any;

        expect(pozo).toMatchObject(sampleWithNewData);
      });

      it('should return NewPozo for empty Pozo initial value', () => {
        const formGroup = service.createPozoFormGroup();

        const pozo = service.getPozo(formGroup) as any;

        expect(pozo).toMatchObject({});
      });

      it('should return IPozo', () => {
        const formGroup = service.createPozoFormGroup(sampleWithRequiredData);

        const pozo = service.getPozo(formGroup) as any;

        expect(pozo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPozo should not enable id FormControl', () => {
        const formGroup = service.createPozoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPozo should disable id FormControl', () => {
        const formGroup = service.createPozoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
