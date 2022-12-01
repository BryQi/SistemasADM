import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IInfraestructura, NewInfraestructura } from '../infraestructura.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInfraestructura for edit and NewInfraestructuraFormGroupInput for create.
 */
type InfraestructuraFormGroupInput = IInfraestructura | PartialWithRequiredKeyOf<NewInfraestructura>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IInfraestructura | NewInfraestructura> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

type InfraestructuraFormRawValue = FormValueOf<IInfraestructura>;

type NewInfraestructuraFormRawValue = FormValueOf<NewInfraestructura>;

type InfraestructuraFormDefaults = Pick<NewInfraestructura, 'id' | 'createdAt' | 'pozos'>;

type InfraestructuraFormGroupContent = {
  id: FormControl<InfraestructuraFormRawValue['id'] | NewInfraestructura['id']>;
  tipo: FormControl<InfraestructuraFormRawValue['tipo']>;
  createdAt: FormControl<InfraestructuraFormRawValue['createdAt']>;
  pozos: FormControl<InfraestructuraFormRawValue['pozos']>;
  idProveedor: FormControl<InfraestructuraFormRawValue['idProveedor']>;
};

export type InfraestructuraFormGroup = FormGroup<InfraestructuraFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InfraestructuraFormService {
  createInfraestructuraFormGroup(infraestructura: InfraestructuraFormGroupInput = { id: null }): InfraestructuraFormGroup {
    const infraestructuraRawValue = this.convertInfraestructuraToInfraestructuraRawValue({
      ...this.getFormDefaults(),
      ...infraestructura,
    });
    return new FormGroup<InfraestructuraFormGroupContent>({
      id: new FormControl(
        { value: infraestructuraRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      tipo: new FormControl(infraestructuraRawValue.tipo),
      createdAt: new FormControl(infraestructuraRawValue.createdAt, {
        validators: [Validators.required],
      }),
      pozos: new FormControl(infraestructuraRawValue.pozos ?? []),
      idProveedor: new FormControl(infraestructuraRawValue.idProveedor),
    });
  }

  getInfraestructura(form: InfraestructuraFormGroup): IInfraestructura | NewInfraestructura {
    return this.convertInfraestructuraRawValueToInfraestructura(
      form.getRawValue() as InfraestructuraFormRawValue | NewInfraestructuraFormRawValue
    );
  }

  resetForm(form: InfraestructuraFormGroup, infraestructura: InfraestructuraFormGroupInput): void {
    const infraestructuraRawValue = this.convertInfraestructuraToInfraestructuraRawValue({ ...this.getFormDefaults(), ...infraestructura });
    form.reset(
      {
        ...infraestructuraRawValue,
        id: { value: infraestructuraRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): InfraestructuraFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      pozos: [],
    };
  }

  private convertInfraestructuraRawValueToInfraestructura(
    rawInfraestructura: InfraestructuraFormRawValue | NewInfraestructuraFormRawValue
  ): IInfraestructura | NewInfraestructura {
    return {
      ...rawInfraestructura,
      createdAt: dayjs(rawInfraestructura.createdAt, DATE_TIME_FORMAT),
    };
  }

  private convertInfraestructuraToInfraestructuraRawValue(
    infraestructura: IInfraestructura | (Partial<NewInfraestructura> & InfraestructuraFormDefaults)
  ): InfraestructuraFormRawValue | PartialWithRequiredKeyOf<NewInfraestructuraFormRawValue> {
    return {
      ...infraestructura,
      createdAt: infraestructura.createdAt ? infraestructura.createdAt.format(DATE_TIME_FORMAT) : undefined,
      pozos: infraestructura.pozos ?? [],
    };
  }
}
