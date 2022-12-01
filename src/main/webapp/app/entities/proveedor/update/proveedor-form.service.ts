import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProveedor, NewProveedor } from '../proveedor.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProveedor for edit and NewProveedorFormGroupInput for create.
 */
type ProveedorFormGroupInput = IProveedor | PartialWithRequiredKeyOf<NewProveedor>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProveedor | NewProveedor> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

type ProveedorFormRawValue = FormValueOf<IProveedor>;

type NewProveedorFormRawValue = FormValueOf<NewProveedor>;

type ProveedorFormDefaults = Pick<NewProveedor, 'id' | 'createdAt'>;

type ProveedorFormGroupContent = {
  id: FormControl<ProveedorFormRawValue['id'] | NewProveedor['id']>;
  razonSocial: FormControl<ProveedorFormRawValue['razonSocial']>;
  contactoTecnico: FormControl<ProveedorFormRawValue['contactoTecnico']>;
  correoEmpresa: FormControl<ProveedorFormRawValue['correoEmpresa']>;
  direccion: FormControl<ProveedorFormRawValue['direccion']>;
  celular: FormControl<ProveedorFormRawValue['celular']>;
  createdAt: FormControl<ProveedorFormRawValue['createdAt']>;
  user: FormControl<ProveedorFormRawValue['user']>;
};

export type ProveedorFormGroup = FormGroup<ProveedorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProveedorFormService {
  createProveedorFormGroup(proveedor: ProveedorFormGroupInput = { id: null }): ProveedorFormGroup {
    const proveedorRawValue = this.convertProveedorToProveedorRawValue({
      ...this.getFormDefaults(),
      ...proveedor,
    });
    return new FormGroup<ProveedorFormGroupContent>({
      id: new FormControl(
        { value: proveedorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      razonSocial: new FormControl(proveedorRawValue.razonSocial, {
        validators: [Validators.required],
      }),
      contactoTecnico: new FormControl(proveedorRawValue.contactoTecnico, {
        validators: [Validators.required],
      }),
      correoEmpresa: new FormControl(proveedorRawValue.correoEmpresa, {
        validators: [Validators.required],
      }),
      direccion: new FormControl(proveedorRawValue.direccion, {
        validators: [Validators.required],
      }),
      celular: new FormControl(proveedorRawValue.celular, {
        validators: [Validators.required],
      }),
      createdAt: new FormControl(proveedorRawValue.createdAt, {
        validators: [Validators.required],
      }),
      user: new FormControl(proveedorRawValue.user, {
        validators: [Validators.required],
      }),
    });
  }

  getProveedor(form: ProveedorFormGroup): IProveedor | NewProveedor {
    return this.convertProveedorRawValueToProveedor(form.getRawValue() as ProveedorFormRawValue | NewProveedorFormRawValue);
  }

  resetForm(form: ProveedorFormGroup, proveedor: ProveedorFormGroupInput): void {
    const proveedorRawValue = this.convertProveedorToProveedorRawValue({ ...this.getFormDefaults(), ...proveedor });
    form.reset(
      {
        ...proveedorRawValue,
        id: { value: proveedorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProveedorFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
    };
  }

  private convertProveedorRawValueToProveedor(rawProveedor: ProveedorFormRawValue | NewProveedorFormRawValue): IProveedor | NewProveedor {
    return {
      ...rawProveedor,
      createdAt: dayjs(rawProveedor.createdAt, DATE_TIME_FORMAT),
    };
  }

  private convertProveedorToProveedorRawValue(
    proveedor: IProveedor | (Partial<NewProveedor> & ProveedorFormDefaults)
  ): ProveedorFormRawValue | PartialWithRequiredKeyOf<NewProveedorFormRawValue> {
    return {
      ...proveedor,
      createdAt: proveedor.createdAt ? proveedor.createdAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
