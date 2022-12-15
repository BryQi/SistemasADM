import dayjs from 'dayjs/esm';
import { TipoPozo } from 'app/entities/enumerations/tipo-pozo.model';

export interface IPozo {
  id: number;
  numeropozo?: string | null;
  direccion?: string | null;
  tipopozo?: TipoPozo | null;
  createdAt?: dayjs.Dayjs | null;
  longitud?: string | null;
  latitud?: string | null;
}

export type NewPozo = Omit<IPozo, 'id'> & { id: null };
