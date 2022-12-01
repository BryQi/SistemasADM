import dayjs from 'dayjs/esm';

import { IProveedor, NewProveedor } from './proveedor.model';

export const sampleWithRequiredData: IProveedor = {
  id: 27795,
  razonSocial: 'deposit AGP Intranet',
  contactoTecnico: 'dot-com',
  correoEmpresa: 'Programa Credit',
  direccion: 'bus transicional',
  celular: 80550,
  createdAt: dayjs('2022-12-01T16:08'),
};

export const sampleWithPartialData: IProveedor = {
  id: 12870,
  razonSocial: 'Silla program',
  contactoTecnico: 'Cine',
  correoEmpresa: 'Hungria Cambridgeshire override',
  direccion: 'Chalet Intuitivo Violeta',
  celular: 84888,
  createdAt: dayjs('2022-12-01T18:50'),
};

export const sampleWithFullData: IProveedor = {
  id: 80860,
  razonSocial: 'Card',
  contactoTecnico: 'Aragón International',
  correoEmpresa: 'Principado',
  direccion: 'e-commerce Pantalones',
  celular: 17220,
  createdAt: dayjs('2022-12-01T00:53'),
};

export const sampleWithNewData: NewProveedor = {
  razonSocial: 'Desarrollador Blanco Andalucía',
  contactoTecnico: 'Burundi',
  correoEmpresa: 'repurpose',
  direccion: 'firmware orchestrate Rwanda',
  celular: 21681,
  createdAt: dayjs('2022-12-01T18:58'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
