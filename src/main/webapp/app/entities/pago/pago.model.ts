import dayjs from 'dayjs/esm';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { IDespliegueInfraestructuraTroncalDistribucion } from 'app/entities/despliegue-infraestructura-troncal-distribucion/despliegue-infraestructura-troncal-distribucion.model';
import { IDespliegueinfraestructuradispersion } from 'app/entities/despliegueinfraestructuradispersion/despliegueinfraestructuradispersion.model';

export interface IPago {
  id: number;
  fechaPago?: dayjs.Dayjs | null;
  pago?: number | null;
  createdAt?: dayjs.Dayjs | null;
  razonSocial?: Pick<IProveedor, 'id' | 'razonSocial'> | null;
  calculoValorPago?: Pick<IDespliegueInfraestructuraTroncalDistribucion, 'id' | 'calculoValorPago'> | null;
  calculoValorPagoD?: Pick<IDespliegueinfraestructuradispersion, 'id' | 'calculoValorPagoD'> | null;
}

export type NewPago = Omit<IPago, 'id'> & { id: null };
