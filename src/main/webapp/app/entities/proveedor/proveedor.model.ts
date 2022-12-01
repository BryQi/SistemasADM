import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';

export interface IProveedor {
  id: number;
  razonSocial?: string | null;
  contactoTecnico?: string | null;
  correoEmpresa?: string | null;
  direccion?: string | null;
  celular?: number | null;
  createdAt?: dayjs.Dayjs | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewProveedor = Omit<IProveedor, 'id'> & { id: null };
