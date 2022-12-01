import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFotoPozo, NewFotoPozo } from '../foto-pozo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFotoPozo for edit and NewFotoPozoFormGroupInput for create.
 */
type FotoPozoFormGroupInput = IFotoPozo | PartialWithRequiredKeyOf<NewFotoPozo>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IFotoPozo | NewFotoPozo> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

type FotoPozoFormRawValue = FormValueOf<IFotoPozo>;

type NewFotoPozoFormRawValue = FormValueOf<NewFotoPozo>;

type FotoPozoFormDefaults = Pick<NewFotoPozo, 'id' | 'createdAt'>;

type FotoPozoFormGroupContent = {
  id: FormControl<FotoPozoFormRawValue['id'] | NewFotoPozo['id']>;
  foto: FormControl<FotoPozoFormRawValue['foto']>;
  fotoContentType: FormControl<FotoPozoFormRawValue['fotoContentType']>;
  descripcion: FormControl<FotoPozoFormRawValue['descripcion']>;
  createdAt: FormControl<FotoPozoFormRawValue['createdAt']>;
  idPozo: FormControl<FotoPozoFormRawValue['idPozo']>;
};

export type FotoPozoFormGroup = FormGroup<FotoPozoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FotoPozoFormService {
  createFotoPozoFormGroup(fotoPozo: FotoPozoFormGroupInput = { id: null }): FotoPozoFormGroup {
    const fotoPozoRawValue = this.convertFotoPozoToFotoPozoRawValue({
      ...this.getFormDefaults(),
      ...fotoPozo,
    });
    return new FormGroup<FotoPozoFormGroupContent>({
      id: new FormControl(
        { value: fotoPozoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      foto: new FormControl(fotoPozoRawValue.foto, {
        validators: [Validators.required],
      }),
      fotoContentType: new FormControl(fotoPozoRawValue.fotoContentType),
      descripcion: new FormControl(fotoPozoRawValue.descripcion, {
        validators: [Validators.required],
      }),
      createdAt: new FormControl(fotoPozoRawValue.createdAt, {
        validators: [Validators.required],
      }),
      idPozo: new FormControl(fotoPozoRawValue.idPozo),
    });
  }

  getFotoPozo(form: FotoPozoFormGroup): IFotoPozo | NewFotoPozo {
    return this.convertFotoPozoRawValueToFotoPozo(form.getRawValue() as FotoPozoFormRawValue | NewFotoPozoFormRawValue);
  }

  resetForm(form: FotoPozoFormGroup, fotoPozo: FotoPozoFormGroupInput): void {
    const fotoPozoRawValue = this.convertFotoPozoToFotoPozoRawValue({ ...this.getFormDefaults(), ...fotoPozo });
    form.reset(
      {
        ...fotoPozoRawValue,
        id: { value: fotoPozoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FotoPozoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
    };
  }

  private convertFotoPozoRawValueToFotoPozo(rawFotoPozo: FotoPozoFormRawValue | NewFotoPozoFormRawValue): IFotoPozo | NewFotoPozo {
    return {
      ...rawFotoPozo,
      createdAt: dayjs(rawFotoPozo.createdAt, DATE_TIME_FORMAT),
    };
  }

  private convertFotoPozoToFotoPozoRawValue(
    fotoPozo: IFotoPozo | (Partial<NewFotoPozo> & FotoPozoFormDefaults)
  ): FotoPozoFormRawValue | PartialWithRequiredKeyOf<NewFotoPozoFormRawValue> {
    return {
      ...fotoPozo,
      createdAt: fotoPozo.createdAt ? fotoPozo.createdAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
