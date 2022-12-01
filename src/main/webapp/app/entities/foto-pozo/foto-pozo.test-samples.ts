import dayjs from 'dayjs/esm';

import { IFotoPozo, NewFotoPozo } from './foto-pozo.model';

export const sampleWithRequiredData: IFotoPozo = {
  id: 67782,
  foto: '../fake-data/blob/hipster.png',
  fotoContentType: 'unknown',
  descripcion: 'Salud',
  createdAt: dayjs('2022-12-01T14:14'),
};

export const sampleWithPartialData: IFotoPozo = {
  id: 78782,
  foto: '../fake-data/blob/hipster.png',
  fotoContentType: 'unknown',
  descripcion: 'firewall',
  createdAt: dayjs('2022-12-01T15:15'),
};

export const sampleWithFullData: IFotoPozo = {
  id: 6452,
  foto: '../fake-data/blob/hipster.png',
  fotoContentType: 'unknown',
  descripcion: 'Canarias',
  createdAt: dayjs('2022-12-01T20:04'),
};

export const sampleWithNewData: NewFotoPozo = {
  foto: '../fake-data/blob/hipster.png',
  fotoContentType: 'unknown',
  descripcion: 'Guapa',
  createdAt: dayjs('2022-12-01T14:28'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
