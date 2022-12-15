import dayjs from 'dayjs/esm';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { Tipo } from 'app/entities/enumerations/tipo.model';

export interface IInfraestructura {
  id: number;
  tipo?: Tipo | null;
  createdAt?: dayjs.Dayjs | null;
  razonSocial?: Pick<IProveedor, 'id' | 'razonSocial'> | null;
  numeropozos?: Pick<IPozo, 'id' | 'numeropozo'>[] | null;
}

export type NewInfraestructura = Omit<IInfraestructura, 'id'> & { id: null };
