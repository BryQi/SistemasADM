import dayjs from 'dayjs/esm';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { IInfraestructura } from 'app/entities/infraestructura/infraestructura.model';

export interface IDespliegueInfraestructuraTroncalDistribucion {
  id: number;
  nombreRuta?: string | null;
  descripcionRuta?: string | null;
  metrajeInicial?: number | null;
  metrajeFinal?: number | null;
  calculoValorPago?: number | null;
  createdAt?: dayjs.Dayjs | null;
  valorMetro?: number | null;
  pozos?: Pick<IPozo, 'id'>[] | null;
  infraestructura?: Pick<IInfraestructura, 'id'> | null;
}

export type NewDespliegueInfraestructuraTroncalDistribucion = Omit<IDespliegueInfraestructuraTroncalDistribucion, 'id'> & { id: null };
