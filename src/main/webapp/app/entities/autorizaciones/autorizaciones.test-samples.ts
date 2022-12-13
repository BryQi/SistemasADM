import dayjs from 'dayjs/esm';

import { ContactoTecnico } from 'app/entities/enumerations/contacto-tecnico.model';

import { IAutorizaciones, NewAutorizaciones } from './autorizaciones.model';

export const sampleWithRequiredData: IAutorizaciones = {
  id: 34343,
  cliente: 'deposit',
  direccionOrigen: 'Marroquinería',
  fechaOperacion: dayjs('2022-12-01'),
  ventanaTrabajo: 'firewall',
  tipoTrabajo: 'Bedfordshire Comunicaciones',
  observaciones: 'Director Funcionario',
  createdAt: dayjs('2022-12-01T09:23'),
  direccionDestino: 'Distribuido applications bypassing',
};

export const sampleWithPartialData: IAutorizaciones = {
  id: 74970,
  cliente: 'groupware política 17(E.U.A.-17)',
  direccionOrigen: 'cultivate SSL Comunidad',
  fechaOperacion: dayjs('2022-12-01'),
  ventanaTrabajo: 'matrix Senda',
  contactoTecnico: ContactoTecnico['InSitu'],
  tipoTrabajo: 'Buckinghamshire Loan Marroquinería',
  observaciones: 'RSS Amarillo Coche',
  createdAt: dayjs('2022-11-30T21:00'),
  direccionDestino: 'Decoración Atún',
};

export const sampleWithFullData: IAutorizaciones = {
  id: 44855,
  cliente: 'en',
  direccionOrigen: 'Dinamarca Silla',
  fechaOperacion: dayjs('2022-12-01'),
  ventanaTrabajo: 'ejecutiva Atún moderador',
  contactoTecnico: ContactoTecnico['InSitu'],
  tipoTrabajo: 'parsing',
  observaciones: 'Adelante objetos',
  createdAt: dayjs('2022-12-01T00:55'),
  direccionDestino: 'Acero Planificador',
};

export const sampleWithNewData: NewAutorizaciones = {
  cliente: 'Pelota Papelería',
  direccionOrigen: 'productize Pescado',
  fechaOperacion: dayjs('2022-12-01'),
  ventanaTrabajo: 'copy Enfocado Aplicaciones',
  tipoTrabajo: 'Progresivo',
  observaciones: 'Toallas deposit',
  createdAt: dayjs('2022-12-01T01:07'),
  direccionDestino: 'de auxiliary',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
