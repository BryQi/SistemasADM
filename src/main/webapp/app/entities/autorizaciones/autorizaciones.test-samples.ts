import dayjs from 'dayjs/esm';

import { ContactoTecnico } from 'app/entities/enumerations/contacto-tecnico.model';

import { IAutorizaciones, NewAutorizaciones } from './autorizaciones.model';

export const sampleWithRequiredData: IAutorizaciones = {
  id: 34343,
  cliente: 'deposit',
  direccionOrigen: 'Marroquinería',
  direccionDestino: 6800,
  fechaOperacion: dayjs('2022-12-01'),
  ventanaTrabajo: 'Cambridgeshire Bedfordshire Comunicaciones',
  tipoTrabajo: 'Director Funcionario',
  observaciones: 'Algerian funcionalidad',
  createdAt: dayjs('2022-12-01T12:40'),
};

export const sampleWithPartialData: IAutorizaciones = {
  id: 11835,
  cliente: '1080p groupware',
  direccionOrigen: 'firewall',
  direccionDestino: 91308,
  fechaOperacion: dayjs('2022-11-30'),
  ventanaTrabajo: 'cultivate SSL Comunidad',
  contactoTecnico: ContactoTecnico['InSitu'],
  tipoTrabajo: 'matrix Senda',
  observaciones: 'Analista',
  createdAt: dayjs('2022-11-30T22:06'),
};

export const sampleWithFullData: IAutorizaciones = {
  id: 60459,
  cliente: 'implement proyecto Caserio',
  direccionOrigen: 'primary bypass',
  direccionDestino: 97303,
  fechaOperacion: dayjs('2022-12-01'),
  ventanaTrabajo: 'Identidad',
  contactoTecnico: ContactoTecnico['InSitu'],
  tipoTrabajo: 'invoice Increible',
  observaciones: 'payment Chilean',
  createdAt: dayjs('2022-12-01T03:21'),
};

export const sampleWithNewData: NewAutorizaciones = {
  cliente: 'Gibraltar Digitalizado',
  direccionOrigen: 'moderador copy',
  direccionDestino: 95864,
  fechaOperacion: dayjs('2022-12-01'),
  ventanaTrabajo: 'Adelante objetos',
  tipoTrabajo: 'Calle Métricas Guatemala',
  observaciones: 'utilize',
  createdAt: dayjs('2022-11-30T22:55'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
