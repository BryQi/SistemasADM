import dayjs from 'dayjs/esm';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { Tipo } from 'app/entities/enumerations/tipo.model';

export interface IInfraestructura {
  id: number;
  tipo?: Tipo | null;
  createdAt?: dayjs.Dayjs | null;
  pozos?: Pick<IPozo, 'id'>[] | null;
  idProveedor?: Pick<IProveedor, 'id'> | null;
}

export type NewInfraestructura = Omit<IInfraestructura, 'id'> & { id: null };
