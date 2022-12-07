import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../despliegue-infraestructura-troncal-distribucion.test-samples';

import { DespliegueInfraestructuraTroncalDistribucionFormService } from './despliegue-infraestructura-troncal-distribucion-form.service';

describe('DespliegueInfraestructuraTroncalDistribucion Form Service', () => {
  let service: DespliegueInfraestructuraTroncalDistribucionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DespliegueInfraestructuraTroncalDistribucionFormService);
  });

  describe('Service methods', () => {
    describe('createDespliegueInfraestructuraTroncalDistribucionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDespliegueInfraestructuraTroncalDistribucionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreRuta: expect.any(Object),
            descripcionRuta: expect.any(Object),
            metrajeInicial: expect.any(Object),
            metrajeFinal: expect.any(Object),
            calculoValorPago: expect.any(Object),
            createdAt: expect.any(Object),
            valorMetro: expect.any(Object),
            pozos: expect.any(Object),
          })
        );
      });

      it('passing IDespliegueInfraestructuraTroncalDistribucion should create a new form with FormGroup', () => {
        const formGroup = service.createDespliegueInfraestructuraTroncalDistribucionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreRuta: expect.any(Object),
            descripcionRuta: expect.any(Object),
            metrajeInicial: expect.any(Object),
            metrajeFinal: expect.any(Object),
            calculoValorPago: expect.any(Object),
            createdAt: expect.any(Object),
            valorMetro: expect.any(Object),
            pozos: expect.any(Object),
          })
        );
      });
    });

    describe('getDespliegueInfraestructuraTroncalDistribucion', () => {
      it('should return NewDespliegueInfraestructuraTroncalDistribucion for default DespliegueInfraestructuraTroncalDistribucion initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDespliegueInfraestructuraTroncalDistribucionFormGroup(sampleWithNewData);

        const despliegueInfraestructuraTroncalDistribucion = service.getDespliegueInfraestructuraTroncalDistribucion(formGroup) as any;

        expect(despliegueInfraestructuraTroncalDistribucion).toMatchObject(sampleWithNewData);
      });

      it('should return NewDespliegueInfraestructuraTroncalDistribucion for empty DespliegueInfraestructuraTroncalDistribucion initial value', () => {
        const formGroup = service.createDespliegueInfraestructuraTroncalDistribucionFormGroup();

        const despliegueInfraestructuraTroncalDistribucion = service.getDespliegueInfraestructuraTroncalDistribucion(formGroup) as any;

        expect(despliegueInfraestructuraTroncalDistribucion).toMatchObject({});
      });

      it('should return IDespliegueInfraestructuraTroncalDistribucion', () => {
        const formGroup = service.createDespliegueInfraestructuraTroncalDistribucionFormGroup(sampleWithRequiredData);

        const despliegueInfraestructuraTroncalDistribucion = service.getDespliegueInfraestructuraTroncalDistribucion(formGroup) as any;

        expect(despliegueInfraestructuraTroncalDistribucion).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDespliegueInfraestructuraTroncalDistribucion should not enable id FormControl', () => {
        const formGroup = service.createDespliegueInfraestructuraTroncalDistribucionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDespliegueInfraestructuraTroncalDistribucion should disable id FormControl', () => {
        const formGroup = service.createDespliegueInfraestructuraTroncalDistribucionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
