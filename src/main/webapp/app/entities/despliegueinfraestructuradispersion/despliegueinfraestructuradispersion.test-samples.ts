import dayjs from 'dayjs/esm';

import { Origen } from 'app/entities/enumerations/origen.model';

import { IDespliegueinfraestructuradispersion, NewDespliegueinfraestructuradispersion } from './despliegueinfraestructuradispersion.model';

export const sampleWithRequiredData: IDespliegueinfraestructuradispersion = {
  id: 94346,
  nombreCliente: 'funcionalidad Consultor Castilla-La',
  direccion: 'Silver',
  fechaInstalacion: dayjs('2022-12-01'),
  destino: 'deposit Increible interface',
  descripcionDePozosUsadosRuta: 'Pollo Burundi',
  metrajeInicial: 52059,
  metrajeFinal: 4172,
  calculoValorPago: 13612,
  createdAt: dayjs('2022-12-01T07:58'),
};

export const sampleWithPartialData: IDespliegueinfraestructuradispersion = {
  id: 52935,
  nombreCliente: 'Andalucía Teclado',
  direccion: 'Canarias',
  fechaInstalacion: dayjs('2022-11-30'),
  origen: Origen['NAP'],
  destino: 'Aruba override Global',
  descripcionDePozosUsadosRuta: 'en Directo',
  metrajeInicial: 3994,
  metrajeFinal: 32071,
  calculoValorPago: 73676,
  createdAt: dayjs('2022-12-01T13:12'),
};

export const sampleWithFullData: IDespliegueinfraestructuradispersion = {
  id: 1299,
  nombreCliente: 'Blanco el',
  direccion: 'Creativo',
  fechaInstalacion: dayjs('2022-12-01'),
  origen: Origen['NAP'],
  destino: 'Director Optimizado',
  descripcionDePozosUsadosRuta: 'Gerente',
  metrajeInicial: 70126,
  metrajeFinal: 93125,
  calculoValorPago: 91567,
  createdAt: dayjs('2022-12-01T09:52'),
};

export const sampleWithNewData: NewDespliegueinfraestructuradispersion = {
  nombreCliente: 'Money',
  direccion: 'expedite',
  fechaInstalacion: dayjs('2022-12-01'),
  destino: 'up',
  descripcionDePozosUsadosRuta: 'Plástico Interno',
  metrajeInicial: 13244,
  metrajeFinal: 67642,
  calculoValorPago: 86780,
  createdAt: dayjs('2022-12-01T00:42'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
