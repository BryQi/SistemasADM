import dayjs from 'dayjs/esm';

export interface IProveedor {
  id: number;
  razonSocial?: string | null;
  contactoTecnico?: string | null;
  correoEmpresa?: string | null;
  direccion?: string | null;
  celular?: number | null;
  createdAt?: dayjs.Dayjs | null;
}

export type NewProveedor = Omit<IProveedor, 'id'> & { id: null };
