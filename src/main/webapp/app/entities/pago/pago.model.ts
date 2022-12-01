import dayjs from 'dayjs/esm';
import { IDespliegueInfraestructuraTroncalDistribucion } from 'app/entities/despliegue-infraestructura-troncal-distribucion/despliegue-infraestructura-troncal-distribucion.model';
import { IDespliegueinfraestructuradispersion } from 'app/entities/despliegueinfraestructuradispersion/despliegueinfraestructuradispersion.model';

export interface IPago {
  id: number;
  fechaPago?: dayjs.Dayjs | null;
  pago?: number | null;
  createdAt?: dayjs.Dayjs | null;
  idDespliegueInfraestructuraTroncalDistribucion?: Pick<IDespliegueInfraestructuraTroncalDistribucion, 'id'> | null;
  idDespliegueinfraestructuradispersion?: Pick<IDespliegueinfraestructuradispersion, 'id'> | null;
}

export type NewPago = Omit<IPago, 'id'> & { id: null };
