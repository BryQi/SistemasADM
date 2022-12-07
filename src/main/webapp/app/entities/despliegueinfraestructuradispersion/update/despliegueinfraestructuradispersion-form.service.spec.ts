import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../despliegueinfraestructuradispersion.test-samples';

import { DespliegueinfraestructuradispersionFormService } from './despliegueinfraestructuradispersion-form.service';

describe('Despliegueinfraestructuradispersion Form Service', () => {
  let service: DespliegueinfraestructuradispersionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DespliegueinfraestructuradispersionFormService);
  });

  describe('Service methods', () => {
    describe('createDespliegueinfraestructuradispersionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDespliegueinfraestructuradispersionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreCliente: expect.any(Object),
            direccion: expect.any(Object),
            fechaInstalacion: expect.any(Object),
            origen: expect.any(Object),
            destino: expect.any(Object),
            descripcionDePozosUsadosRuta: expect.any(Object),
            metrajeInicial: expect.any(Object),
            metrajeFinal: expect.any(Object),
            calculoValorPago: expect.any(Object),
            createdAt: expect.any(Object),
            valorMetro: expect.any(Object),
            pozos: expect.any(Object),
            idDespliegueInfraestructuraTroncalDistribucion: expect.any(Object),
            idProveedor: expect.any(Object),
          })
        );
      });

      it('passing IDespliegueinfraestructuradispersion should create a new form with FormGroup', () => {
        const formGroup = service.createDespliegueinfraestructuradispersionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombreCliente: expect.any(Object),
            direccion: expect.any(Object),
            fechaInstalacion: expect.any(Object),
            origen: expect.any(Object),
            destino: expect.any(Object),
            descripcionDePozosUsadosRuta: expect.any(Object),
            metrajeInicial: expect.any(Object),
            metrajeFinal: expect.any(Object),
            calculoValorPago: expect.any(Object),
            createdAt: expect.any(Object),
            valorMetro: expect.any(Object),
            pozos: expect.any(Object),
            idDespliegueInfraestructuraTroncalDistribucion: expect.any(Object),
            idProveedor: expect.any(Object),
          })
        );
      });
    });

    describe('getDespliegueinfraestructuradispersion', () => {
      it('should return NewDespliegueinfraestructuradispersion for default Despliegueinfraestructuradispersion initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDespliegueinfraestructuradispersionFormGroup(sampleWithNewData);

        const despliegueinfraestructuradispersion = service.getDespliegueinfraestructuradispersion(formGroup) as any;

        expect(despliegueinfraestructuradispersion).toMatchObject(sampleWithNewData);
      });

      it('should return NewDespliegueinfraestructuradispersion for empty Despliegueinfraestructuradispersion initial value', () => {
        const formGroup = service.createDespliegueinfraestructuradispersionFormGroup();

        const despliegueinfraestructuradispersion = service.getDespliegueinfraestructuradispersion(formGroup) as any;

        expect(despliegueinfraestructuradispersion).toMatchObject({});
      });

      it('should return IDespliegueinfraestructuradispersion', () => {
        const formGroup = service.createDespliegueinfraestructuradispersionFormGroup(sampleWithRequiredData);

        const despliegueinfraestructuradispersion = service.getDespliegueinfraestructuradispersion(formGroup) as any;

        expect(despliegueinfraestructuradispersion).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDespliegueinfraestructuradispersion should not enable id FormControl', () => {
        const formGroup = service.createDespliegueinfraestructuradispersionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDespliegueinfraestructuradispersion should disable id FormControl', () => {
        const formGroup = service.createDespliegueinfraestructuradispersionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
