import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import {
  IDespliegueInfraestructuraTroncalDistribucion,
  NewDespliegueInfraestructuraTroncalDistribucion,
} from '../despliegue-infraestructura-troncal-distribucion.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDespliegueInfraestructuraTroncalDistribucion for edit and NewDespliegueInfraestructuraTroncalDistribucionFormGroupInput for create.
 */
type DespliegueInfraestructuraTroncalDistribucionFormGroupInput =
  | IDespliegueInfraestructuraTroncalDistribucion
  | PartialWithRequiredKeyOf<NewDespliegueInfraestructuraTroncalDistribucion>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDespliegueInfraestructuraTroncalDistribucion | NewDespliegueInfraestructuraTroncalDistribucion> = Omit<
  T,
  'createdAt'
> & {
  createdAt?: string | null;
};

type DespliegueInfraestructuraTroncalDistribucionFormRawValue = FormValueOf<IDespliegueInfraestructuraTroncalDistribucion>;

type NewDespliegueInfraestructuraTroncalDistribucionFormRawValue = FormValueOf<NewDespliegueInfraestructuraTroncalDistribucion>;

type DespliegueInfraestructuraTroncalDistribucionFormDefaults = Pick<
  NewDespliegueInfraestructuraTroncalDistribucion,
  'id' | 'createdAt' | 'pozos'
>;

type DespliegueInfraestructuraTroncalDistribucionFormGroupContent = {
  id: FormControl<DespliegueInfraestructuraTroncalDistribucionFormRawValue['id'] | NewDespliegueInfraestructuraTroncalDistribucion['id']>;
  nombreRuta: FormControl<DespliegueInfraestructuraTroncalDistribucionFormRawValue['nombreRuta']>;
  descripcionRuta: FormControl<DespliegueInfraestructuraTroncalDistribucionFormRawValue['descripcionRuta']>;
  metrajeInicial: FormControl<DespliegueInfraestructuraTroncalDistribucionFormRawValue['metrajeInicial']>;
  metrajeFinal: FormControl<DespliegueInfraestructuraTroncalDistribucionFormRawValue['metrajeFinal']>;
  calculoValorPago: FormControl<DespliegueInfraestructuraTroncalDistribucionFormRawValue['calculoValorPago']>;
  createdAt: FormControl<DespliegueInfraestructuraTroncalDistribucionFormRawValue['createdAt']>;
  pozos: FormControl<DespliegueInfraestructuraTroncalDistribucionFormRawValue['pozos']>;
};

export type DespliegueInfraestructuraTroncalDistribucionFormGroup = FormGroup<DespliegueInfraestructuraTroncalDistribucionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DespliegueInfraestructuraTroncalDistribucionFormService {
  createDespliegueInfraestructuraTroncalDistribucionFormGroup(
    despliegueInfraestructuraTroncalDistribucion: DespliegueInfraestructuraTroncalDistribucionFormGroupInput = { id: null }
  ): DespliegueInfraestructuraTroncalDistribucionFormGroup {
    const despliegueInfraestructuraTroncalDistribucionRawValue =
      this.convertDespliegueInfraestructuraTroncalDistribucionToDespliegueInfraestructuraTroncalDistribucionRawValue({
        ...this.getFormDefaults(),
        ...despliegueInfraestructuraTroncalDistribucion,
      });
    return new FormGroup<DespliegueInfraestructuraTroncalDistribucionFormGroupContent>({
      id: new FormControl(
        { value: despliegueInfraestructuraTroncalDistribucionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nombreRuta: new FormControl(despliegueInfraestructuraTroncalDistribucionRawValue.nombreRuta, {
        validators: [Validators.required],
      }),
      descripcionRuta: new FormControl(despliegueInfraestructuraTroncalDistribucionRawValue.descripcionRuta, {
        validators: [Validators.required],
      }),
      metrajeInicial: new FormControl(despliegueInfraestructuraTroncalDistribucionRawValue.metrajeInicial, {
        validators: [Validators.required],
      }),
      metrajeFinal: new FormControl(despliegueInfraestructuraTroncalDistribucionRawValue.metrajeFinal, {
        validators: [Validators.required],
      }),
      calculoValorPago: new FormControl(despliegueInfraestructuraTroncalDistribucionRawValue.calculoValorPago, {
        validators: [Validators.required],
      }),
      createdAt: new FormControl(despliegueInfraestructuraTroncalDistribucionRawValue.createdAt, {
        validators: [Validators.required],
      }),
      pozos: new FormControl(despliegueInfraestructuraTroncalDistribucionRawValue.pozos ?? []),
    });
  }

  getDespliegueInfraestructuraTroncalDistribucion(
    form: DespliegueInfraestructuraTroncalDistribucionFormGroup
  ): IDespliegueInfraestructuraTroncalDistribucion | NewDespliegueInfraestructuraTroncalDistribucion {
    return this.convertDespliegueInfraestructuraTroncalDistribucionRawValueToDespliegueInfraestructuraTroncalDistribucion(
      form.getRawValue() as
        | DespliegueInfraestructuraTroncalDistribucionFormRawValue
        | NewDespliegueInfraestructuraTroncalDistribucionFormRawValue
    );
  }

  resetForm(
    form: DespliegueInfraestructuraTroncalDistribucionFormGroup,
    despliegueInfraestructuraTroncalDistribucion: DespliegueInfraestructuraTroncalDistribucionFormGroupInput
  ): void {
    const despliegueInfraestructuraTroncalDistribucionRawValue =
      this.convertDespliegueInfraestructuraTroncalDistribucionToDespliegueInfraestructuraTroncalDistribucionRawValue({
        ...this.getFormDefaults(),
        ...despliegueInfraestructuraTroncalDistribucion,
      });
    form.reset(
      {
        ...despliegueInfraestructuraTroncalDistribucionRawValue,
        id: { value: despliegueInfraestructuraTroncalDistribucionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DespliegueInfraestructuraTroncalDistribucionFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      pozos: [],
    };
  }

  private convertDespliegueInfraestructuraTroncalDistribucionRawValueToDespliegueInfraestructuraTroncalDistribucion(
    rawDespliegueInfraestructuraTroncalDistribucion:
      | DespliegueInfraestructuraTroncalDistribucionFormRawValue
      | NewDespliegueInfraestructuraTroncalDistribucionFormRawValue
  ): IDespliegueInfraestructuraTroncalDistribucion | NewDespliegueInfraestructuraTroncalDistribucion {
    return {
      ...rawDespliegueInfraestructuraTroncalDistribucion,
      createdAt: dayjs(rawDespliegueInfraestructuraTroncalDistribucion.createdAt, DATE_TIME_FORMAT),
    };
  }

  private convertDespliegueInfraestructuraTroncalDistribucionToDespliegueInfraestructuraTroncalDistribucionRawValue(
    despliegueInfraestructuraTroncalDistribucion:
      | IDespliegueInfraestructuraTroncalDistribucion
      | (Partial<NewDespliegueInfraestructuraTroncalDistribucion> & DespliegueInfraestructuraTroncalDistribucionFormDefaults)
  ):
    | DespliegueInfraestructuraTroncalDistribucionFormRawValue
    | PartialWithRequiredKeyOf<NewDespliegueInfraestructuraTroncalDistribucionFormRawValue> {
    return {
      ...despliegueInfraestructuraTroncalDistribucion,
      createdAt: despliegueInfraestructuraTroncalDistribucion.createdAt
        ? despliegueInfraestructuraTroncalDistribucion.createdAt.format(DATE_TIME_FORMAT)
        : undefined,
      pozos: despliegueInfraestructuraTroncalDistribucion.pozos ?? [],
    };
  }
}
