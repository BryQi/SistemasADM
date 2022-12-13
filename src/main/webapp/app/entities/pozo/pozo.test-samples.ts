import dayjs from 'dayjs/esm';

import { TipoPozo } from 'app/entities/enumerations/tipo-pozo.model';

import { IPozo, NewPozo } from './pozo.model';

export const sampleWithRequiredData: IPozo = {
  id: 20960,
  numeropozo: 'methodologies Madera',
  direccion: 'SDD strategic',
  createdAt: dayjs('2022-12-01T08:52'),
  longitud: 'Peso',
  latitud: 'Aragón de Raton',
};

export const sampleWithPartialData: IPozo = {
  id: 24073,
  numeropozo: 'USB Aragón Inversor',
  direccion: 'proyección Ghana',
  createdAt: dayjs('2022-12-01T18:19'),
  longitud: 'Savings Blanco copying',
  latitud: 'Loan compressing',
};

export const sampleWithFullData: IPozo = {
  id: 46576,
  numeropozo: 'SMS',
  direccion: 'Grecia Granito',
  tipopozo: TipoPozo['Revision'],
  createdAt: dayjs('2022-12-01T14:42'),
  longitud: 'bandwidth Guantes Granito',
  latitud: 'array',
};

export const sampleWithNewData: NewPozo = {
  numeropozo: 'Interfaz Fantástico',
  direccion: 'Marruecos Blanco',
  createdAt: dayjs('2022-12-01T01:41'),
  longitud: 'withdrawal compress Ferrocarril',
  latitud: 'circuit India',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
