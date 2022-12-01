import dayjs from 'dayjs/esm';
import { IPozo } from 'app/entities/pozo/pozo.model';

export interface IRegistroInspecciones {
  id: number;
  cumpleAutorizacion?: boolean | null;
  numeroAutorizacion?: number | null;
  cumpleSenaletica?: boolean | null;
  cumpleConosSeguridad?: boolean | null;
  cumpleEtiquetado?: boolean | null;
  cumpleArregloCables?: boolean | null;
  cumplelimpiezaOrdenPozo?: boolean | null;
  createdAt?: dayjs.Dayjs | null;
  idPozo?: Pick<IPozo, 'id'> | null;
}

export type NewRegistroInspecciones = Omit<IRegistroInspecciones, 'id'> & { id: null };
