import dayjs from 'dayjs/esm';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';

export interface IDespliegueInfraestructuraTroncalDistribucion {
  id: number;
  nombreRuta?: string | null;
  descripcionRuta?: string | null;
  metrajeInicial?: number | null;
  metrajeFinal?: number | null;
  calculoValorPago?: number | null;
  createdAt?: dayjs.Dayjs | null;
  valorMetro?: number | null;
  pozos?: Pick<IPozo, 'id' | 'numeropozo'>[] | null;
  razonSocial?: Pick<IProveedor, 'id' | 'razonSocial'> | null;
}

export type NewDespliegueInfraestructuraTroncalDistribucion = Omit<IDespliegueInfraestructuraTroncalDistribucion, 'id'> & { id: null };
