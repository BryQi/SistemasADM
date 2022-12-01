import dayjs from 'dayjs/esm';
import { IDespliegueInfraestructuraTroncalDistribucion } from 'app/entities/despliegue-infraestructura-troncal-distribucion/despliegue-infraestructura-troncal-distribucion.model';
import { IDespliegueinfraestructuradispersion } from 'app/entities/despliegueinfraestructuradispersion/despliegueinfraestructuradispersion.model';
import { IInfraestructura } from 'app/entities/infraestructura/infraestructura.model';
import { Ubicacion } from 'app/entities/enumerations/ubicacion.model';
import { TipoPozo } from 'app/entities/enumerations/tipo-pozo.model';

export interface IPozo {
  id: number;
  numeropozo?: string | null;
  direccion?: string | null;
  ubicacion?: Ubicacion | null;
  tipopozo?: TipoPozo | null;
  createdAt?: dayjs.Dayjs | null;
  idDespliegueInfraestructuraTroncalDistribucions?: Pick<IDespliegueInfraestructuraTroncalDistribucion, 'id'>[] | null;
  idDespliegueinfraestructuradispersions?: Pick<IDespliegueinfraestructuradispersion, 'id'>[] | null;
  idInfraestructuras?: Pick<IInfraestructura, 'id'>[] | null;
}

export type NewPozo = Omit<IPozo, 'id'> & { id: null };
