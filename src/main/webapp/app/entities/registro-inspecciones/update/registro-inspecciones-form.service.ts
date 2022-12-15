import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRegistroInspecciones, NewRegistroInspecciones } from '../registro-inspecciones.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRegistroInspecciones for edit and NewRegistroInspeccionesFormGroupInput for create.
 */
type RegistroInspeccionesFormGroupInput = IRegistroInspecciones | PartialWithRequiredKeyOf<NewRegistroInspecciones>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IRegistroInspecciones | NewRegistroInspecciones> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

type RegistroInspeccionesFormRawValue = FormValueOf<IRegistroInspecciones>;

type NewRegistroInspeccionesFormRawValue = FormValueOf<NewRegistroInspecciones>;

type RegistroInspeccionesFormDefaults = Pick<
  NewRegistroInspecciones,
  | 'id'
  | 'cumpleAutorizacion'
  | 'cumpleSenaletica'
  | 'cumpleConosSeguridad'
  | 'cumpleEtiquetado'
  | 'cumpleArregloCables'
  | 'cumplelimpiezaOrdenPozo'
  | 'createdAt'
>;

type RegistroInspeccionesFormGroupContent = {
  id: FormControl<RegistroInspeccionesFormRawValue['id'] | NewRegistroInspecciones['id']>;
  cumpleAutorizacion: FormControl<RegistroInspeccionesFormRawValue['cumpleAutorizacion']>;
  numeroAutorizacion: FormControl<RegistroInspeccionesFormRawValue['numeroAutorizacion']>;
  cumpleSenaletica: FormControl<RegistroInspeccionesFormRawValue['cumpleSenaletica']>;
  cumpleConosSeguridad: FormControl<RegistroInspeccionesFormRawValue['cumpleConosSeguridad']>;
  cumpleEtiquetado: FormControl<RegistroInspeccionesFormRawValue['cumpleEtiquetado']>;
  cumpleArregloCables: FormControl<RegistroInspeccionesFormRawValue['cumpleArregloCables']>;
  cumplelimpiezaOrdenPozo: FormControl<RegistroInspeccionesFormRawValue['cumplelimpiezaOrdenPozo']>;
  createdAt: FormControl<RegistroInspeccionesFormRawValue['createdAt']>;
  razonSocial: FormControl<RegistroInspeccionesFormRawValue['razonSocial']>;
  numeropozo: FormControl<RegistroInspeccionesFormRawValue['numeropozo']>;
};

export type RegistroInspeccionesFormGroup = FormGroup<RegistroInspeccionesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RegistroInspeccionesFormService {
  createRegistroInspeccionesFormGroup(
    registroInspecciones: RegistroInspeccionesFormGroupInput = { id: null }
  ): RegistroInspeccionesFormGroup {
    const registroInspeccionesRawValue = this.convertRegistroInspeccionesToRegistroInspeccionesRawValue({
      ...this.getFormDefaults(),
      ...registroInspecciones,
    });
    return new FormGroup<RegistroInspeccionesFormGroupContent>({
      id: new FormControl(
        { value: registroInspeccionesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cumpleAutorizacion: new FormControl(registroInspeccionesRawValue.cumpleAutorizacion),
      numeroAutorizacion: new FormControl(registroInspeccionesRawValue.numeroAutorizacion, {
        validators: [Validators.required],
      }),
      cumpleSenaletica: new FormControl(registroInspeccionesRawValue.cumpleSenaletica),
      cumpleConosSeguridad: new FormControl(registroInspeccionesRawValue.cumpleConosSeguridad),
      cumpleEtiquetado: new FormControl(registroInspeccionesRawValue.cumpleEtiquetado),
      cumpleArregloCables: new FormControl(registroInspeccionesRawValue.cumpleArregloCables),
      cumplelimpiezaOrdenPozo: new FormControl(registroInspeccionesRawValue.cumplelimpiezaOrdenPozo),
      createdAt: new FormControl(registroInspeccionesRawValue.createdAt, {
        validators: [Validators.required],
      }),
      razonSocial: new FormControl(registroInspeccionesRawValue.razonSocial, {
        validators: [Validators.required],
      }),
      numeropozo: new FormControl(registroInspeccionesRawValue.numeropozo, {
        validators: [Validators.required],
      }),
    });
  }

  getRegistroInspecciones(form: RegistroInspeccionesFormGroup): IRegistroInspecciones | NewRegistroInspecciones {
    return this.convertRegistroInspeccionesRawValueToRegistroInspecciones(
      form.getRawValue() as RegistroInspeccionesFormRawValue | NewRegistroInspeccionesFormRawValue
    );
  }

  resetForm(form: RegistroInspeccionesFormGroup, registroInspecciones: RegistroInspeccionesFormGroupInput): void {
    const registroInspeccionesRawValue = this.convertRegistroInspeccionesToRegistroInspeccionesRawValue({
      ...this.getFormDefaults(),
      ...registroInspecciones,
    });
    form.reset(
      {
        ...registroInspeccionesRawValue,
        id: { value: registroInspeccionesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RegistroInspeccionesFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      cumpleAutorizacion: false,
      cumpleSenaletica: false,
      cumpleConosSeguridad: false,
      cumpleEtiquetado: false,
      cumpleArregloCables: false,
      cumplelimpiezaOrdenPozo: false,
      createdAt: currentTime,
    };
  }

  private convertRegistroInspeccionesRawValueToRegistroInspecciones(
    rawRegistroInspecciones: RegistroInspeccionesFormRawValue | NewRegistroInspeccionesFormRawValue
  ): IRegistroInspecciones | NewRegistroInspecciones {
    return {
      ...rawRegistroInspecciones,
      createdAt: dayjs(rawRegistroInspecciones.createdAt, DATE_TIME_FORMAT),
    };
  }

  private convertRegistroInspeccionesToRegistroInspeccionesRawValue(
    registroInspecciones: IRegistroInspecciones | (Partial<NewRegistroInspecciones> & RegistroInspeccionesFormDefaults)
  ): RegistroInspeccionesFormRawValue | PartialWithRequiredKeyOf<NewRegistroInspeccionesFormRawValue> {
    return {
      ...registroInspecciones,
      createdAt: registroInspecciones.createdAt ? registroInspecciones.createdAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
