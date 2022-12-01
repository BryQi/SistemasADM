import dayjs from 'dayjs/esm';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { IDespliegueInfraestructuraTroncalDistribucion } from 'app/entities/despliegue-infraestructura-troncal-distribucion/despliegue-infraestructura-troncal-distribucion.model';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { Origen } from 'app/entities/enumerations/origen.model';

export interface IDespliegueinfraestructuradispersion {
  id: number;
  nombreCliente?: string | null;
  direccion?: string | null;
  fechaInstalacion?: dayjs.Dayjs | null;
  origen?: Origen | null;
  destino?: string | null;
  descripcionDePozosUsadosRuta?: string | null;
  metrajeInicial?: number | null;
  metrajeFinal?: number | null;
  calculoValorPago?: number | null;
  createdAt?: dayjs.Dayjs | null;
  pozos?: Pick<IPozo, 'id'>[] | null;
  idDespliegueInfraestructuraTroncalDistribucion?: Pick<IDespliegueInfraestructuraTroncalDistribucion, 'id'> | null;
  idProveedor?: Pick<IProveedor, 'id'> | null;
}

export type NewDespliegueinfraestructuradispersion = Omit<IDespliegueinfraestructuradispersion, 'id'> & { id: null };
