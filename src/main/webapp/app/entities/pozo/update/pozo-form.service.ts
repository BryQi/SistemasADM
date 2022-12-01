import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPozo, NewPozo } from '../pozo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPozo for edit and NewPozoFormGroupInput for create.
 */
type PozoFormGroupInput = IPozo | PartialWithRequiredKeyOf<NewPozo>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPozo | NewPozo> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

type PozoFormRawValue = FormValueOf<IPozo>;

type NewPozoFormRawValue = FormValueOf<NewPozo>;

type PozoFormDefaults = Pick<
  NewPozo,
  'id' | 'createdAt' | 'idDespliegueInfraestructuraTroncalDistribucions' | 'idDespliegueinfraestructuradispersions' | 'idInfraestructuras'
>;

type PozoFormGroupContent = {
  id: FormControl<PozoFormRawValue['id'] | NewPozo['id']>;
  numeropozo: FormControl<PozoFormRawValue['numeropozo']>;
  direccion: FormControl<PozoFormRawValue['direccion']>;
  ubicacion: FormControl<PozoFormRawValue['ubicacion']>;
  tipopozo: FormControl<PozoFormRawValue['tipopozo']>;
  createdAt: FormControl<PozoFormRawValue['createdAt']>;
  idDespliegueInfraestructuraTroncalDistribucions: FormControl<PozoFormRawValue['idDespliegueInfraestructuraTroncalDistribucions']>;
  idDespliegueinfraestructuradispersions: FormControl<PozoFormRawValue['idDespliegueinfraestructuradispersions']>;
  idInfraestructuras: FormControl<PozoFormRawValue['idInfraestructuras']>;
};

export type PozoFormGroup = FormGroup<PozoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PozoFormService {
  createPozoFormGroup(pozo: PozoFormGroupInput = { id: null }): PozoFormGroup {
    const pozoRawValue = this.convertPozoToPozoRawValue({
      ...this.getFormDefaults(),
      ...pozo,
    });
    return new FormGroup<PozoFormGroupContent>({
      id: new FormControl(
        { value: pozoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      numeropozo: new FormControl(pozoRawValue.numeropozo, {
        validators: [Validators.required],
      }),
      direccion: new FormControl(pozoRawValue.direccion, {
        validators: [Validators.required],
      }),
      ubicacion: new FormControl(pozoRawValue.ubicacion),
      tipopozo: new FormControl(pozoRawValue.tipopozo),
      createdAt: new FormControl(pozoRawValue.createdAt, {
        validators: [Validators.required],
      }),
      idDespliegueInfraestructuraTroncalDistribucions: new FormControl(pozoRawValue.idDespliegueInfraestructuraTroncalDistribucions ?? []),
      idDespliegueinfraestructuradispersions: new FormControl(pozoRawValue.idDespliegueinfraestructuradispersions ?? []),
      idInfraestructuras: new FormControl(pozoRawValue.idInfraestructuras ?? []),
    });
  }

  getPozo(form: PozoFormGroup): IPozo | NewPozo {
    return this.convertPozoRawValueToPozo(form.getRawValue() as PozoFormRawValue | NewPozoFormRawValue);
  }

  resetForm(form: PozoFormGroup, pozo: PozoFormGroupInput): void {
    const pozoRawValue = this.convertPozoToPozoRawValue({ ...this.getFormDefaults(), ...pozo });
    form.reset(
      {
        ...pozoRawValue,
        id: { value: pozoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PozoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      idDespliegueInfraestructuraTroncalDistribucions: [],
      idDespliegueinfraestructuradispersions: [],
      idInfraestructuras: [],
    };
  }

  private convertPozoRawValueToPozo(rawPozo: PozoFormRawValue | NewPozoFormRawValue): IPozo | NewPozo {
    return {
      ...rawPozo,
      createdAt: dayjs(rawPozo.createdAt, DATE_TIME_FORMAT),
    };
  }

  private convertPozoToPozoRawValue(
    pozo: IPozo | (Partial<NewPozo> & PozoFormDefaults)
  ): PozoFormRawValue | PartialWithRequiredKeyOf<NewPozoFormRawValue> {
    return {
      ...pozo,
      createdAt: pozo.createdAt ? pozo.createdAt.format(DATE_TIME_FORMAT) : undefined,
      idDespliegueInfraestructuraTroncalDistribucions: pozo.idDespliegueInfraestructuraTroncalDistribucions ?? [],
      idDespliegueinfraestructuradispersions: pozo.idDespliegueinfraestructuradispersions ?? [],
      idInfraestructuras: pozo.idInfraestructuras ?? [],
    };
  }
}