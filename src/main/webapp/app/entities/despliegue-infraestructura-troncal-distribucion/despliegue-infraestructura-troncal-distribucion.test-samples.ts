import dayjs from 'dayjs/esm';

import {
  IDespliegueInfraestructuraTroncalDistribucion,
  NewDespliegueInfraestructuraTroncalDistribucion,
} from './despliegue-infraestructura-troncal-distribucion.model';

export const sampleWithRequiredData: IDespliegueInfraestructuraTroncalDistribucion = {
  id: 90735,
  nombreRuta: 'tiempo sistémica',
  descripcionRuta: 'streamline transmitter',
  metrajeInicial: 40000,
  metrajeFinal: 43492,
  calculoValorPago: 96510,
  createdAt: dayjs('2022-12-01T01:06'),
};

export const sampleWithPartialData: IDespliegueInfraestructuraTroncalDistribucion = {
  id: 90580,
  nombreRuta: 'JBOD firmware',
  descripcionRuta: 'deposit Asistente Buckinghamshire',
  metrajeInicial: 37340,
  metrajeFinal: 71889,
  calculoValorPago: 99975,
  createdAt: dayjs('2022-11-30T23:21'),
};

export const sampleWithFullData: IDespliegueInfraestructuraTroncalDistribucion = {
  id: 69645,
  nombreRuta: 'Pelota',
  descripcionRuta: 'real-time',
  metrajeInicial: 48594,
  metrajeFinal: 1799,
  calculoValorPago: 55399,
  createdAt: dayjs('2022-12-01T18:59'),
};

export const sampleWithNewData: NewDespliegueInfraestructuraTroncalDistribucion = {
  nombreRuta: 'Bedfordshire Oficial implement',
  descripcionRuta: 'embrace',
  metrajeInicial: 9776,
  metrajeFinal: 81731,
  calculoValorPago: 88673,
  createdAt: dayjs('2022-12-01T01:20'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
