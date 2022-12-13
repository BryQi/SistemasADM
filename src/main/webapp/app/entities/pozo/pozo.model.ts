import dayjs from 'dayjs/esm';
import { IDespliegueInfraestructuraTroncalDistribucion } from 'app/entities/despliegue-infraestructura-troncal-distribucion/despliegue-infraestructura-troncal-distribucion.model';
import { IDespliegueinfraestructuradispersion } from 'app/entities/despliegueinfraestructuradispersion/despliegueinfraestructuradispersion.model';
import { TipoPozo } from 'app/entities/enumerations/tipo-pozo.model';

export interface IPozo {
  id: number;
  numeropozo?: string | null;
  direccion?: string | null;
  tipopozo?: TipoPozo | null;
  createdAt?: dayjs.Dayjs | null;
  longitud?: string | null;
  latitud?: string | null;
  idDespliegueInfraestructuraTroncalDistribucions?: Pick<IDespliegueInfraestructuraTroncalDistribucion, 'id'>[] | null;
  idDespliegueinfraestructuradispersions?: Pick<IDespliegueinfraestructuradispersion, 'id'>[] | null;
}

export type NewPozo = Omit<IPozo, 'id'> & { id: null };
