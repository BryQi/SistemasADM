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
  valorMetro: 66251,
};

export const sampleWithPartialData: IDespliegueinfraestructuradispersion = {
  id: 38710,
  nombreCliente: 'Papelería Madera',
  direccion: 'contenido Seguro',
  fechaInstalacion: dayjs('2022-12-01'),
  origen: Origen['NAP'],
  destino: 'Pantalones Liberia en',
  descripcionDePozosUsadosRuta: 'Artesanal política Salud',
  metrajeInicial: 45513,
  metrajeFinal: 68428,
  calculoValorPago: 26823,
  createdAt: dayjs('2022-12-01T16:34'),
  valorMetro: 74384,
};

export const sampleWithFullData: IDespliegueinfraestructuradispersion = {
  id: 24004,
  nombreCliente: 'haptic Opcional protocolo',
  direccion: 'Avon Aplicaciones',
  fechaInstalacion: dayjs('2022-11-30'),
  origen: Origen['Nodo'],
  destino: 'en arquitectura',
  descripcionDePozosUsadosRuta: 'Infraestructura up',
  metrajeInicial: 48630,
  metrajeFinal: 13629,
  calculoValorPago: 32006,
  createdAt: dayjs('2022-12-01T03:04'),
  valorMetro: 91525,
};

export const sampleWithNewData: NewDespliegueinfraestructuradispersion = {
  nombreCliente: 'Videojuegos SAS Sorprendente',
  direccion: 'de',
  fechaInstalacion: dayjs('2022-12-01'),
  destino: 'soporte',
  descripcionDePozosUsadosRuta: 'Loan',
  metrajeInicial: 1830,
  metrajeFinal: 30630,
  calculoValorPago: 71400,
  createdAt: dayjs('2022-12-01T08:51'),
  valorMetro: 92368,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
