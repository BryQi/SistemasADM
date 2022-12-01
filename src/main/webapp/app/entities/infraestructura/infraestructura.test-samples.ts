import dayjs from 'dayjs/esm';

import { Tipo } from 'app/entities/enumerations/tipo.model';

import { IInfraestructura, NewInfraestructura } from './infraestructura.model';

export const sampleWithRequiredData: IInfraestructura = {
  id: 20126,
  createdAt: dayjs('2022-12-01T09:48'),
};

export const sampleWithPartialData: IInfraestructura = {
  id: 49718,
  tipo: Tipo['Nodo'],
  createdAt: dayjs('2022-12-01T02:16'),
};

export const sampleWithFullData: IInfraestructura = {
  id: 36578,
  tipo: Tipo['Pedestal'],
  createdAt: dayjs('2022-12-01T17:36'),
};

export const sampleWithNewData: NewInfraestructura = {
  createdAt: dayjs('2022-11-30T21:07'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
