import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../foto-pozo.test-samples';

import { FotoPozoFormService } from './foto-pozo-form.service';

describe('FotoPozo Form Service', () => {
  let service: FotoPozoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FotoPozoFormService);
  });

  describe('Service methods', () => {
    describe('createFotoPozoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFotoPozoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            foto: expect.any(Object),
            descripcion: expect.any(Object),
            createdAt: expect.any(Object),
            numeropozo: expect.any(Object),
          })
        );
      });

      it('passing IFotoPozo should create a new form with FormGroup', () => {
        const formGroup = service.createFotoPozoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            foto: expect.any(Object),
            descripcion: expect.any(Object),
            createdAt: expect.any(Object),
            numeropozo: expect.any(Object),
          })
        );
      });
    });

    describe('getFotoPozo', () => {
      it('should return NewFotoPozo for default FotoPozo initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFotoPozoFormGroup(sampleWithNewData);

        const fotoPozo = service.getFotoPozo(formGroup) as any;

        expect(fotoPozo).toMatchObject(sampleWithNewData);
      });

      it('should return NewFotoPozo for empty FotoPozo initial value', () => {
        const formGroup = service.createFotoPozoFormGroup();

        const fotoPozo = service.getFotoPozo(formGroup) as any;

        expect(fotoPozo).toMatchObject({});
      });

      it('should return IFotoPozo', () => {
        const formGroup = service.createFotoPozoFormGroup(sampleWithRequiredData);

        const fotoPozo = service.getFotoPozo(formGroup) as any;

        expect(fotoPozo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFotoPozo should not enable id FormControl', () => {
        const formGroup = service.createFotoPozoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFotoPozo should disable id FormControl', () => {
        const formGroup = service.createFotoPozoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
