import dayjs from 'dayjs/esm';
import { IDespliegueInfraestructuraTroncalDistribucion } from 'app/entities/despliegue-infraestructura-troncal-distribucion/despliegue-infraestructura-troncal-distribucion.model';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { IPozo } from 'app/entities/pozo/pozo.model';
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
  createdAt?: dayjs.Dayjs | null;
  valorMetro?: number | null;
  calculoValorPagoD?: number | null;
  nombreRuta?: Pick<IDespliegueInfraestructuraTroncalDistribucion, 'id' | 'nombreRuta'> | null;
  razonSocial?: Pick<IProveedor, 'id' | 'razonSocial'> | null;
  numeropozos?: Pick<IPozo, 'id' | 'numeropozo'>[] | null;
}

export type NewDespliegueinfraestructuradispersion = Omit<IDespliegueinfraestructuradispersion, 'id'> & { id: null };
