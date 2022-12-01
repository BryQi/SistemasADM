import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDespliegueinfraestructuradispersion, NewDespliegueinfraestructuradispersion } from '../despliegueinfraestructuradispersion.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDespliegueinfraestructuradispersion for edit and NewDespliegueinfraestructuradispersionFormGroupInput for create.
 */
type DespliegueinfraestructuradispersionFormGroupInput =
  | IDespliegueinfraestructuradispersion
  | PartialWithRequiredKeyOf<NewDespliegueinfraestructuradispersion>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDespliegueinfraestructuradispersion | NewDespliegueinfraestructuradispersion> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

type DespliegueinfraestructuradispersionFormRawValue = FormValueOf<IDespliegueinfraestructuradispersion>;

type NewDespliegueinfraestructuradispersionFormRawValue = FormValueOf<NewDespliegueinfraestructuradispersion>;

type DespliegueinfraestructuradispersionFormDefaults = Pick<NewDespliegueinfraestructuradispersion, 'id' | 'createdAt' | 'pozos'>;

type DespliegueinfraestructuradispersionFormGroupContent = {
  id: FormControl<DespliegueinfraestructuradispersionFormRawValue['id'] | NewDespliegueinfraestructuradispersion['id']>;
  nombreCliente: FormControl<DespliegueinfraestructuradispersionFormRawValue['nombreCliente']>;
  direccion: FormControl<DespliegueinfraestructuradispersionFormRawValue['direccion']>;
  fechaInstalacion: FormControl<DespliegueinfraestructuradispersionFormRawValue['fechaInstalacion']>;
  origen: FormControl<DespliegueinfraestructuradispersionFormRawValue['origen']>;
  destino: FormControl<DespliegueinfraestructuradispersionFormRawValue['destino']>;
  descripcionDePozosUsadosRuta: FormControl<DespliegueinfraestructuradispersionFormRawValue['descripcionDePozosUsadosRuta']>;
  metrajeInicial: FormControl<DespliegueinfraestructuradispersionFormRawValue['metrajeInicial']>;
  metrajeFinal: FormControl<DespliegueinfraestructuradispersionFormRawValue['metrajeFinal']>;
  calculoValorPago: FormControl<DespliegueinfraestructuradispersionFormRawValue['calculoValorPago']>;
  createdAt: FormControl<DespliegueinfraestructuradispersionFormRawValue['createdAt']>;
  pozos: FormControl<DespliegueinfraestructuradispersionFormRawValue['pozos']>;
  idDespliegueInfraestructuraTroncalDistribucion: FormControl<
    DespliegueinfraestructuradispersionFormRawValue['idDespliegueInfraestructuraTroncalDistribucion']
  >;
  idProveedor: FormControl<DespliegueinfraestructuradispersionFormRawValue['idProveedor']>;
};

export type DespliegueinfraestructuradispersionFormGroup = FormGroup<DespliegueinfraestructuradispersionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DespliegueinfraestructuradispersionFormService {
  createDespliegueinfraestructuradispersionFormGroup(
    despliegueinfraestructuradispersion: DespliegueinfraestructuradispersionFormGroupInput = { id: null }
  ): DespliegueinfraestructuradispersionFormGroup {
    const despliegueinfraestructuradispersionRawValue =
      this.convertDespliegueinfraestructuradispersionToDespliegueinfraestructuradispersionRawValue({
        ...this.getFormDefaults(),
        ...despliegueinfraestructuradispersion,
      });
    return new FormGroup<DespliegueinfraestructuradispersionFormGroupContent>({
      id: new FormControl(
        { value: despliegueinfraestructuradispersionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nombreCliente: new FormControl(despliegueinfraestructuradispersionRawValue.nombreCliente, {
        validators: [Validators.required],
      }),
      direccion: new FormControl(despliegueinfraestructuradispersionRawValue.direccion, {
        validators: [Validators.required],
      }),
      fechaInstalacion: new FormControl(despliegueinfraestructuradispersionRawValue.fechaInstalacion, {
        validators: [Validators.required],
      }),
      origen: new FormControl(despliegueinfraestructuradispersionRawValue.origen),
      destino: new FormControl(despliegueinfraestructuradispersionRawValue.destino, {
        validators: [Validators.required],
      }),
      descripcionDePozosUsadosRuta: new FormControl(despliegueinfraestructuradispersionRawValue.descripcionDePozosUsadosRuta, {
        validators: [Validators.required],
      }),
      metrajeInicial: new FormControl(despliegueinfraestructuradispersionRawValue.metrajeInicial, {
        validators: [Validators.required],
      }),
      metrajeFinal: new FormControl(despliegueinfraestructuradispersionRawValue.metrajeFinal, {
        validators: [Validators.required],
      }),
      calculoValorPago: new FormControl(despliegueinfraestructuradispersionRawValue.calculoValorPago, {
        validators: [Validators.required],
      }),
      createdAt: new FormControl(despliegueinfraestructuradispersionRawValue.createdAt, {
        validators: [Validators.required],
      }),
      pozos: new FormControl(despliegueinfraestructuradispersionRawValue.pozos ?? []),
      idDespliegueInfraestructuraTroncalDistribucion: new FormControl(
        despliegueinfraestructuradispersionRawValue.idDespliegueInfraestructuraTroncalDistribucion
      ),
      idProveedor: new FormControl(despliegueinfraestructuradispersionRawValue.idProveedor),
    });
  }

  getDespliegueinfraestructuradispersion(
    form: DespliegueinfraestructuradispersionFormGroup
  ): IDespliegueinfraestructuradispersion | NewDespliegueinfraestructuradispersion {
    return this.convertDespliegueinfraestructuradispersionRawValueToDespliegueinfraestructuradispersion(
      form.getRawValue() as DespliegueinfraestructuradispersionFormRawValue | NewDespliegueinfraestructuradispersionFormRawValue
    );
  }

  resetForm(
    form: DespliegueinfraestructuradispersionFormGroup,
    despliegueinfraestructuradispersion: DespliegueinfraestructuradispersionFormGroupInput
  ): void {
    const despliegueinfraestructuradispersionRawValue =
      this.convertDespliegueinfraestructuradispersionToDespliegueinfraestructuradispersionRawValue({
        ...this.getFormDefaults(),
        ...despliegueinfraestructuradispersion,
      });
    form.reset(
      {
        ...despliegueinfraestructuradispersionRawValue,
        id: { value: despliegueinfraestructuradispersionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DespliegueinfraestructuradispersionFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      pozos: [],
    };
  }

  private convertDespliegueinfraestructuradispersionRawValueToDespliegueinfraestructuradispersion(
    rawDespliegueinfraestructuradispersion:
      | DespliegueinfraestructuradispersionFormRawValue
      | NewDespliegueinfraestructuradispersionFormRawValue
  ): IDespliegueinfraestructuradispersion | NewDespliegueinfraestructuradispersion {
    return {
      ...rawDespliegueinfraestructuradispersion,
      createdAt: dayjs(rawDespliegueinfraestructuradispersion.createdAt, DATE_TIME_FORMAT),
    };
  }

  private convertDespliegueinfraestructuradispersionToDespliegueinfraestructuradispersionRawValue(
    despliegueinfraestructuradispersion:
      | IDespliegueinfraestructuradispersion
      | (Partial<NewDespliegueinfraestructuradispersion> & DespliegueinfraestructuradispersionFormDefaults)
  ): DespliegueinfraestructuradispersionFormRawValue | PartialWithRequiredKeyOf<NewDespliegueinfraestructuradispersionFormRawValue> {
    return {
      ...despliegueinfraestructuradispersion,
      createdAt: despliegueinfraestructuradispersion.createdAt
        ? despliegueinfraestructuradispersion.createdAt.format(DATE_TIME_FORMAT)
        : undefined,
      pozos: despliegueinfraestructuradispersion.pozos ?? [],
    };
  }
}
