import dayjs from 'dayjs/esm';
import { IPozo } from 'app/entities/pozo/pozo.model';

export interface IFotoPozo {
  id: number;
  foto?: string | null;
  fotoContentType?: string | null;
  descripcion?: string | null;
  createdAt?: dayjs.Dayjs | null;
  idPozo?: Pick<IPozo, 'id'> | null;
}

export type NewFotoPozo = Omit<IFotoPozo, 'id'> & { id: null };
