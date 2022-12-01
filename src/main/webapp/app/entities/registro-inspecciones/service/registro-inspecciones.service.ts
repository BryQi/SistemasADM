import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRegistroInspecciones, NewRegistroInspecciones } from '../registro-inspecciones.model';

export type PartialUpdateRegistroInspecciones = Partial<IRegistroInspecciones> & Pick<IRegistroInspecciones, 'id'>;

type RestOf<T extends IRegistroInspecciones | NewRegistroInspecciones> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

export type RestRegistroInspecciones = RestOf<IRegistroInspecciones>;

export type NewRestRegistroInspecciones = RestOf<NewRegistroInspecciones>;

export type PartialUpdateRestRegistroInspecciones = RestOf<PartialUpdateRegistroInspecciones>;

export type EntityResponseType = HttpResponse<IRegistroInspecciones>;
export type EntityArrayResponseType = HttpResponse<IRegistroInspecciones[]>;

@Injectable({ providedIn: 'root' })
export class RegistroInspeccionesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/registro-inspecciones');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(registroInspecciones: NewRegistroInspecciones): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(registroInspecciones);
    return this.http
      .post<RestRegistroInspecciones>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(registroInspecciones: IRegistroInspecciones): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(registroInspecciones);
    return this.http
      .put<RestRegistroInspecciones>(`${this.resourceUrl}/${this.getRegistroInspeccionesIdentifier(registroInspecciones)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(registroInspecciones: PartialUpdateRegistroInspecciones): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(registroInspecciones);
    return this.http
      .patch<RestRegistroInspecciones>(`${this.resourceUrl}/${this.getRegistroInspeccionesIdentifier(registroInspecciones)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRegistroInspecciones>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRegistroInspecciones[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRegistroInspeccionesIdentifier(registroInspecciones: Pick<IRegistroInspecciones, 'id'>): number {
    return registroInspecciones.id;
  }

  compareRegistroInspecciones(o1: Pick<IRegistroInspecciones, 'id'> | null, o2: Pick<IRegistroInspecciones, 'id'> | null): boolean {
    return o1 && o2 ? this.getRegistroInspeccionesIdentifier(o1) === this.getRegistroInspeccionesIdentifier(o2) : o1 === o2;
  }

  addRegistroInspeccionesToCollectionIfMissing<Type extends Pick<IRegistroInspecciones, 'id'>>(
    registroInspeccionesCollection: Type[],
    ...registroInspeccionesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const registroInspecciones: Type[] = registroInspeccionesToCheck.filter(isPresent);
    if (registroInspecciones.length > 0) {
      const registroInspeccionesCollectionIdentifiers = registroInspeccionesCollection.map(
        registroInspeccionesItem => this.getRegistroInspeccionesIdentifier(registroInspeccionesItem)!
      );
      const registroInspeccionesToAdd = registroInspecciones.filter(registroInspeccionesItem => {
        const registroInspeccionesIdentifier = this.getRegistroInspeccionesIdentifier(registroInspeccionesItem);
        if (registroInspeccionesCollectionIdentifiers.includes(registroInspeccionesIdentifier)) {
          return false;
        }
        registroInspeccionesCollectionIdentifiers.push(registroInspeccionesIdentifier);
        return true;
      });
      return [...registroInspeccionesToAdd, ...registroInspeccionesCollection];
    }
    return registroInspeccionesCollection;
  }

  protected convertDateFromClient<T extends IRegistroInspecciones | NewRegistroInspecciones | PartialUpdateRegistroInspecciones>(
    registroInspecciones: T
  ): RestOf<T> {
    return {
      ...registroInspecciones,
      createdAt: registroInspecciones.createdAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restRegistroInspecciones: RestRegistroInspecciones): IRegistroInspecciones {
    return {
      ...restRegistroInspecciones,
      createdAt: restRegistroInspecciones.createdAt ? dayjs(restRegistroInspecciones.createdAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRegistroInspecciones>): HttpResponse<IRegistroInspecciones> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRegistroInspecciones[]>): HttpResponse<IRegistroInspecciones[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
