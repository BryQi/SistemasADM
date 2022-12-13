import dayjs from 'dayjs/esm';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { ContactoTecnico } from 'app/entities/enumerations/contacto-tecnico.model';

export interface IAutorizaciones {
  id: number;
  cliente?: string | null;
  direccionOrigen?: string | null;
  fechaOperacion?: dayjs.Dayjs | null;
  ventanaTrabajo?: string | null;
  contactoTecnico?: ContactoTecnico | null;
  tipoTrabajo?: string | null;
  observaciones?: string | null;
  createdAt?: dayjs.Dayjs | null;
  direccionDestino?: string | null;
  idProveedor?: Pick<IProveedor, 'id'> | null;
  pozo?: Pick<IPozo, 'id'> | null;
}

export type NewAutorizaciones = Omit<IAutorizaciones, 'id'> & { id: null };
