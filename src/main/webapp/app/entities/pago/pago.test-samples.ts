import dayjs from 'dayjs/esm';

import { IPago, NewPago } from './pago.model';

export const sampleWithRequiredData: IPago = {
  id: 23778,
  fechaPago: dayjs('2022-12-01'),
  pago: 70862,
  createdAt: dayjs('2022-12-01T13:21'),
};

export const sampleWithPartialData: IPago = {
  id: 21240,
  fechaPago: dayjs('2022-12-01'),
  pago: 58737,
  createdAt: dayjs('2022-11-30T23:17'),
};

export const sampleWithFullData: IPago = {
  id: 89108,
  fechaPago: dayjs('2022-12-01'),
  pago: 64525,
  createdAt: dayjs('2022-12-01T07:41'),
};

export const sampleWithNewData: NewPago = {
  fechaPago: dayjs('2022-12-01'),
  pago: 24485,
  createdAt: dayjs('2022-12-01T01:56'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
