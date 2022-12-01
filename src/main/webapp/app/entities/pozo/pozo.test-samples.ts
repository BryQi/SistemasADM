import dayjs from 'dayjs/esm';

import { Ubicacion } from 'app/entities/enumerations/ubicacion.model';
import { TipoPozo } from 'app/entities/enumerations/tipo-pozo.model';

import { IPozo, NewPozo } from './pozo.model';

export const sampleWithRequiredData: IPozo = {
  id: 20960,
  numeropozo: 'methodologies Madera',
  direccion: 'SDD strategic',
  createdAt: dayjs('2022-12-01T08:52'),
};

export const sampleWithPartialData: IPozo = {
  id: 18772,
  numeropozo: 'quantify copying',
  direccion: 'Raton 24/7',
  tipopozo: TipoPozo['Revision'],
  createdAt: dayjs('2022-12-01T02:10'),
};

export const sampleWithFullData: IPozo = {
  id: 19719,
  numeropozo: 'Práctico withdrawal',
  direccion: '1080p',
  ubicacion: Ubicacion['Este'],
  tipopozo: TipoPozo['PozodePaso'],
  createdAt: dayjs('2022-12-01T18:19'),
};

export const sampleWithNewData: NewPozo = {
  numeropozo: 'Savings Blanco copying',
  direccion: 'Loan compressing',
  createdAt: dayjs('2022-12-01T09:10'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
