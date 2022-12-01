import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDespliegueinfraestructuradispersion, NewDespliegueinfraestructuradispersion } from '../despliegueinfraestructuradispersion.model';

export type PartialUpdateDespliegueinfraestructuradispersion = Partial<IDespliegueinfraestructuradispersion> &
  Pick<IDespliegueinfraestructuradispersion, 'id'>;

type RestOf<T extends IDespliegueinfraestructuradispersion | NewDespliegueinfraestructuradispersion> = Omit<
  T,
  'fechaInstalacion' | 'createdAt'
> & {
  fechaInstalacion?: string | null;
  createdAt?: string | null;
};

export type RestDespliegueinfraestructuradispersion = RestOf<IDespliegueinfraestructuradispersion>;

export type NewRestDespliegueinfraestructuradispersion = RestOf<NewDespliegueinfraestructuradispersion>;

export type PartialUpdateRestDespliegueinfraestructuradispersion = RestOf<PartialUpdateDespliegueinfraestructuradispersion>;

export type EntityResponseType = HttpResponse<IDespliegueinfraestructuradispersion>;
export type EntityArrayResponseType = HttpResponse<IDespliegueinfraestructuradispersion[]>;

@Injectable({ providedIn: 'root' })
export class DespliegueinfraestructuradispersionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/despliegueinfraestructuradispersions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(despliegueinfraestructuradispersion: NewDespliegueinfraestructuradispersion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(despliegueinfraestructuradispersion);
    return this.http
      .post<RestDespliegueinfraestructuradispersion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(despliegueinfraestructuradispersion: IDespliegueinfraestructuradispersion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(despliegueinfraestructuradispersion);
    return this.http
      .put<RestDespliegueinfraestructuradispersion>(
        `${this.resourceUrl}/${this.getDespliegueinfraestructuradispersionIdentifier(despliegueinfraestructuradispersion)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(despliegueinfraestructuradispersion: PartialUpdateDespliegueinfraestructuradispersion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(despliegueinfraestructuradispersion);
    return this.http
      .patch<RestDespliegueinfraestructuradispersion>(
        `${this.resourceUrl}/${this.getDespliegueinfraestructuradispersionIdentifier(despliegueinfraestructuradispersion)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDespliegueinfraestructuradispersion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDespliegueinfraestructuradispersion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDespliegueinfraestructuradispersionIdentifier(
    despliegueinfraestructuradispersion: Pick<IDespliegueinfraestructuradispersion, 'id'>
  ): number {
    return despliegueinfraestructuradispersion.id;
  }

  compareDespliegueinfraestructuradispersion(
    o1: Pick<IDespliegueinfraestructuradispersion, 'id'> | null,
    o2: Pick<IDespliegueinfraestructuradispersion, 'id'> | null
  ): boolean {
    return o1 && o2
      ? this.getDespliegueinfraestructuradispersionIdentifier(o1) === this.getDespliegueinfraestructuradispersionIdentifier(o2)
      : o1 === o2;
  }

  addDespliegueinfraestructuradispersionToCollectionIfMissing<Type extends Pick<IDespliegueinfraestructuradispersion, 'id'>>(
    despliegueinfraestructuradispersionCollection: Type[],
    ...despliegueinfraestructuradispersionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const despliegueinfraestructuradispersions: Type[] = despliegueinfraestructuradispersionsToCheck.filter(isPresent);
    if (despliegueinfraestructuradispersions.length > 0) {
      const despliegueinfraestructuradispersionCollectionIdentifiers = despliegueinfraestructuradispersionCollection.map(
        despliegueinfraestructuradispersionItem =>
          this.getDespliegueinfraestructuradispersionIdentifier(despliegueinfraestructuradispersionItem)!
      );
      const despliegueinfraestructuradispersionsToAdd = despliegueinfraestructuradispersions.filter(
        despliegueinfraestructuradispersionItem => {
          const despliegueinfraestructuradispersionIdentifier = this.getDespliegueinfraestructuradispersionIdentifier(
            despliegueinfraestructuradispersionItem
          );
          if (despliegueinfraestructuradispersionCollectionIdentifiers.includes(despliegueinfraestructuradispersionIdentifier)) {
            return false;
          }
          despliegueinfraestructuradispersionCollectionIdentifiers.push(despliegueinfraestructuradispersionIdentifier);
          return true;
        }
      );
      return [...despliegueinfraestructuradispersionsToAdd, ...despliegueinfraestructuradispersionCollection];
    }
    return despliegueinfraestructuradispersionCollection;
  }

  protected convertDateFromClient<
    T extends
      | IDespliegueinfraestructuradispersion
      | NewDespliegueinfraestructuradispersion
      | PartialUpdateDespliegueinfraestructuradispersion
  >(despliegueinfraestructuradispersion: T): RestOf<T> {
    return {
      ...despliegueinfraestructuradispersion,
      fechaInstalacion: despliegueinfraestructuradispersion.fechaInstalacion?.format(DATE_FORMAT) ?? null,
      createdAt: despliegueinfraestructuradispersion.createdAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(
    restDespliegueinfraestructuradispersion: RestDespliegueinfraestructuradispersion
  ): IDespliegueinfraestructuradispersion {
    return {
      ...restDespliegueinfraestructuradispersion,
      fechaInstalacion: restDespliegueinfraestructuradispersion.fechaInstalacion
        ? dayjs(restDespliegueinfraestructuradispersion.fechaInstalacion)
        : undefined,
      createdAt: restDespliegueinfraestructuradispersion.createdAt ? dayjs(restDespliegueinfraestructuradispersion.createdAt) : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestDespliegueinfraestructuradispersion>
  ): HttpResponse<IDespliegueinfraestructuradispersion> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestDespliegueinfraestructuradispersion[]>
  ): HttpResponse<IDespliegueinfraestructuradispersion[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
