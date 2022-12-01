import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAutorizaciones, NewAutorizaciones } from '../autorizaciones.model';

export type PartialUpdateAutorizaciones = Partial<IAutorizaciones> & Pick<IAutorizaciones, 'id'>;

type RestOf<T extends IAutorizaciones | NewAutorizaciones> = Omit<T, 'fechaOperacion' | 'createdAt'> & {
  fechaOperacion?: string | null;
  createdAt?: string | null;
};

export type RestAutorizaciones = RestOf<IAutorizaciones>;

export type NewRestAutorizaciones = RestOf<NewAutorizaciones>;

export type PartialUpdateRestAutorizaciones = RestOf<PartialUpdateAutorizaciones>;

export type EntityResponseType = HttpResponse<IAutorizaciones>;
export type EntityArrayResponseType = HttpResponse<IAutorizaciones[]>;

@Injectable({ providedIn: 'root' })
export class AutorizacionesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/autorizaciones');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(autorizaciones: NewAutorizaciones): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(autorizaciones);
    return this.http
      .post<RestAutorizaciones>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(autorizaciones: IAutorizaciones): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(autorizaciones);
    return this.http
      .put<RestAutorizaciones>(`${this.resourceUrl}/${this.getAutorizacionesIdentifier(autorizaciones)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(autorizaciones: PartialUpdateAutorizaciones): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(autorizaciones);
    return this.http
      .patch<RestAutorizaciones>(`${this.resourceUrl}/${this.getAutorizacionesIdentifier(autorizaciones)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAutorizaciones>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAutorizaciones[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAutorizacionesIdentifier(autorizaciones: Pick<IAutorizaciones, 'id'>): number {
    return autorizaciones.id;
  }

  compareAutorizaciones(o1: Pick<IAutorizaciones, 'id'> | null, o2: Pick<IAutorizaciones, 'id'> | null): boolean {
    return o1 && o2 ? this.getAutorizacionesIdentifier(o1) === this.getAutorizacionesIdentifier(o2) : o1 === o2;
  }

  addAutorizacionesToCollectionIfMissing<Type extends Pick<IAutorizaciones, 'id'>>(
    autorizacionesCollection: Type[],
    ...autorizacionesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const autorizaciones: Type[] = autorizacionesToCheck.filter(isPresent);
    if (autorizaciones.length > 0) {
      const autorizacionesCollectionIdentifiers = autorizacionesCollection.map(
        autorizacionesItem => this.getAutorizacionesIdentifier(autorizacionesItem)!
      );
      const autorizacionesToAdd = autorizaciones.filter(autorizacionesItem => {
        const autorizacionesIdentifier = this.getAutorizacionesIdentifier(autorizacionesItem);
        if (autorizacionesCollectionIdentifiers.includes(autorizacionesIdentifier)) {
          return false;
        }
        autorizacionesCollectionIdentifiers.push(autorizacionesIdentifier);
        return true;
      });
      return [...autorizacionesToAdd, ...autorizacionesCollection];
    }
    return autorizacionesCollection;
  }

  protected convertDateFromClient<T extends IAutorizaciones | NewAutorizaciones | PartialUpdateAutorizaciones>(
    autorizaciones: T
  ): RestOf<T> {
    return {
      ...autorizaciones,
      fechaOperacion: autorizaciones.fechaOperacion?.format(DATE_FORMAT) ?? null,
      createdAt: autorizaciones.createdAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAutorizaciones: RestAutorizaciones): IAutorizaciones {
    return {
      ...restAutorizaciones,
      fechaOperacion: restAutorizaciones.fechaOperacion ? dayjs(restAutorizaciones.fechaOperacion) : undefined,
      createdAt: restAutorizaciones.createdAt ? dayjs(restAutorizaciones.createdAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAutorizaciones>): HttpResponse<IAutorizaciones> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAutorizaciones[]>): HttpResponse<IAutorizaciones[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
