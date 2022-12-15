import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAutorizaciones, NewAutorizaciones } from '../autorizaciones.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAutorizaciones for edit and NewAutorizacionesFormGroupInput for create.
 */
type AutorizacionesFormGroupInput = IAutorizaciones | PartialWithRequiredKeyOf<NewAutorizaciones>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAutorizaciones | NewAutorizaciones> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

type AutorizacionesFormRawValue = FormValueOf<IAutorizaciones>;

type NewAutorizacionesFormRawValue = FormValueOf<NewAutorizaciones>;

type AutorizacionesFormDefaults = Pick<NewAutorizaciones, 'id' | 'createdAt'>;

type AutorizacionesFormGroupContent = {
  id: FormControl<AutorizacionesFormRawValue['id'] | NewAutorizaciones['id']>;
  cliente: FormControl<AutorizacionesFormRawValue['cliente']>;
  direccionOrigen: FormControl<AutorizacionesFormRawValue['direccionOrigen']>;
  fechaOperacion: FormControl<AutorizacionesFormRawValue['fechaOperacion']>;
  ventanaTrabajo: FormControl<AutorizacionesFormRawValue['ventanaTrabajo']>;
  contactoTecnico: FormControl<AutorizacionesFormRawValue['contactoTecnico']>;
  tipoTrabajo: FormControl<AutorizacionesFormRawValue['tipoTrabajo']>;
  observaciones: FormControl<AutorizacionesFormRawValue['observaciones']>;
  createdAt: FormControl<AutorizacionesFormRawValue['createdAt']>;
  direccionDestino: FormControl<AutorizacionesFormRawValue['direccionDestino']>;
  razonSocial: FormControl<AutorizacionesFormRawValue['razonSocial']>;
  numeropozo: FormControl<AutorizacionesFormRawValue['numeropozo']>;
};

export type AutorizacionesFormGroup = FormGroup<AutorizacionesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AutorizacionesFormService {
  createAutorizacionesFormGroup(autorizaciones: AutorizacionesFormGroupInput = { id: null }): AutorizacionesFormGroup {
    const autorizacionesRawValue = this.convertAutorizacionesToAutorizacionesRawValue({
      ...this.getFormDefaults(),
      ...autorizaciones,
    });
    return new FormGroup<AutorizacionesFormGroupContent>({
      id: new FormControl(
        { value: autorizacionesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cliente: new FormControl(autorizacionesRawValue.cliente, {
        validators: [Validators.required],
      }),
      direccionOrigen: new FormControl(autorizacionesRawValue.direccionOrigen, {
        validators: [Validators.required],
      }),
      fechaOperacion: new FormControl(autorizacionesRawValue.fechaOperacion, {
        validators: [Validators.required],
      }),
      ventanaTrabajo: new FormControl(autorizacionesRawValue.ventanaTrabajo, {
        validators: [Validators.required],
      }),
      contactoTecnico: new FormControl(autorizacionesRawValue.contactoTecnico),
      tipoTrabajo: new FormControl(autorizacionesRawValue.tipoTrabajo, {
        validators: [Validators.required],
      }),
      observaciones: new FormControl(autorizacionesRawValue.observaciones, {
        validators: [Validators.required],
      }),
      createdAt: new FormControl(autorizacionesRawValue.createdAt, {
        validators: [Validators.required],
      }),
      direccionDestino: new FormControl(autorizacionesRawValue.direccionDestino, {
        validators: [Validators.required],
      }),
      razonSocial: new FormControl(autorizacionesRawValue.razonSocial, {
        validators: [Validators.required],
      }),
      numeropozo: new FormControl(autorizacionesRawValue.numeropozo, {
        validators: [Validators.required],
      }),
    });
  }

  getAutorizaciones(form: AutorizacionesFormGroup): IAutorizaciones | NewAutorizaciones {
    return this.convertAutorizacionesRawValueToAutorizaciones(
      form.getRawValue() as AutorizacionesFormRawValue | NewAutorizacionesFormRawValue
    );
  }

  resetForm(form: AutorizacionesFormGroup, autorizaciones: AutorizacionesFormGroupInput): void {
    const autorizacionesRawValue = this.convertAutorizacionesToAutorizacionesRawValue({ ...this.getFormDefaults(), ...autorizaciones });
    form.reset(
      {
        ...autorizacionesRawValue,
        id: { value: autorizacionesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AutorizacionesFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
    };
  }

  private convertAutorizacionesRawValueToAutorizaciones(
    rawAutorizaciones: AutorizacionesFormRawValue | NewAutorizacionesFormRawValue
  ): IAutorizaciones | NewAutorizaciones {
    return {
      ...rawAutorizaciones,
      createdAt: dayjs(rawAutorizaciones.createdAt, DATE_TIME_FORMAT),
    };
  }

  private convertAutorizacionesToAutorizacionesRawValue(
    autorizaciones: IAutorizaciones | (Partial<NewAutorizaciones> & AutorizacionesFormDefaults)
  ): AutorizacionesFormRawValue | PartialWithRequiredKeyOf<NewAutorizacionesFormRawValue> {
    return {
      ...autorizaciones,
      createdAt: autorizaciones.createdAt ? autorizaciones.createdAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
