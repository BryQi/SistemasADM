import dayjs from 'dayjs/esm';

import { IRegistroInspecciones, NewRegistroInspecciones } from './registro-inspecciones.model';

export const sampleWithRequiredData: IRegistroInspecciones = {
  id: 46631,
  numeroAutorizacion: 21551,
  createdAt: dayjs('2022-12-01T08:15'),
};

export const sampleWithPartialData: IRegistroInspecciones = {
  id: 98874,
  numeroAutorizacion: 67557,
  cumpleEtiquetado: false,
  cumpleArregloCables: false,
  cumplelimpiezaOrdenPozo: true,
  createdAt: dayjs('2022-12-01T05:32'),
};

export const sampleWithFullData: IRegistroInspecciones = {
  id: 12803,
  cumpleAutorizacion: true,
  numeroAutorizacion: 14460,
  cumpleSenaletica: true,
  cumpleConosSeguridad: true,
  cumpleEtiquetado: true,
  cumpleArregloCables: false,
  cumplelimpiezaOrdenPozo: true,
  createdAt: dayjs('2022-12-01T03:19'),
};

export const sampleWithNewData: NewRegistroInspecciones = {
  numeroAutorizacion: 11785,
  createdAt: dayjs('2022-12-01T05:01'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
