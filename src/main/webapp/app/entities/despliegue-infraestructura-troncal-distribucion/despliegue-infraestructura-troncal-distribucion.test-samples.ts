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
  valorMetro: 90580,
};

export const sampleWithPartialData: IDespliegueInfraestructuraTroncalDistribucion = {
  id: 47205,
  nombreRuta: 'Funcionario plug-and-play deposit',
  descripcionRuta: 'copying Saudi matrix',
  metrajeInicial: 69645,
  metrajeFinal: 18668,
  calculoValorPago: 4507,
  createdAt: dayjs('2022-12-01T05:01'),
  valorMetro: 43294,
};

export const sampleWithFullData: IDespliegueInfraestructuraTroncalDistribucion = {
  id: 25227,
  nombreRuta: 'flexibilidad Sorprendente Azerbayán',
  descripcionRuta: 'contenido Negro',
  metrajeInicial: 802,
  metrajeFinal: 3865,
  calculoValorPago: 16237,
  createdAt: dayjs('2022-12-01T10:45'),
  valorMetro: 13039,
};

export const sampleWithNewData: NewDespliegueInfraestructuraTroncalDistribucion = {
  nombreRuta: 'Guapa haptic',
  descripcionRuta: 'port Interno',
  metrajeInicial: 47018,
  metrajeFinal: 19092,
  calculoValorPago: 15095,
  createdAt: dayjs('2022-12-01T06:18'),
  valorMetro: 74256,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
