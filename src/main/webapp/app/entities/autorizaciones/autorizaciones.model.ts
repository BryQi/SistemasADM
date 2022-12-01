import dayjs from 'dayjs/esm';
import { IRegistroInspecciones } from 'app/entities/registro-inspecciones/registro-inspecciones.model';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ContactoTecnico } from 'app/entities/enumerations/contacto-tecnico.model';

export interface IAutorizaciones {
  id: number;
  cliente?: string | null;
  direccionOrigen?: string | null;
  direccionDestino?: number | null;
  fechaOperacion?: dayjs.Dayjs | null;
  ventanaTrabajo?: string | null;
  contactoTecnico?: ContactoTecnico | null;
  tipoTrabajo?: string | null;
  observaciones?: string | null;
  createdAt?: dayjs.Dayjs | null;
  registroInspecciones?: Pick<IRegistroInspecciones, 'id'> | null;
  idProveedor?: Pick<IProveedor, 'id'> | null;
}

export type NewAutorizaciones = Omit<IAutorizaciones, 'id'> & { id: null };
