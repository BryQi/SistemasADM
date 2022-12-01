import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPozo, NewPozo } from '../pozo.model';

export type PartialUpdatePozo = Partial<IPozo> & Pick<IPozo, 'id'>;

type RestOf<T extends IPozo | NewPozo> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

export type RestPozo = RestOf<IPozo>;

export type NewRestPozo = RestOf<NewPozo>;

export type PartialUpdateRestPozo = RestOf<PartialUpdatePozo>;

export type EntityResponseType = HttpResponse<IPozo>;
export type EntityArrayResponseType = HttpResponse<IPozo[]>;

@Injectable({ providedIn: 'root' })
export class PozoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pozos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pozo: NewPozo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pozo);
    return this.http.post<RestPozo>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(pozo: IPozo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pozo);
    return this.http
      .put<RestPozo>(`${this.resourceUrl}/${this.getPozoIdentifier(pozo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(pozo: PartialUpdatePozo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pozo);
    return this.http
      .patch<RestPozo>(`${this.resourceUrl}/${this.getPozoIdentifier(pozo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPozo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPozo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPozoIdentifier(pozo: Pick<IPozo, 'id'>): number {
    return pozo.id;
  }

  comparePozo(o1: Pick<IPozo, 'id'> | null, o2: Pick<IPozo, 'id'> | null): boolean {
    return o1 && o2 ? this.getPozoIdentifier(o1) === this.getPozoIdentifier(o2) : o1 === o2;
  }

  addPozoToCollectionIfMissing<Type extends Pick<IPozo, 'id'>>(
    pozoCollection: Type[],
    ...pozosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pozos: Type[] = pozosToCheck.filter(isPresent);
    if (pozos.length > 0) {
      const pozoCollectionIdentifiers = pozoCollection.map(pozoItem => this.getPozoIdentifier(pozoItem)!);
      const pozosToAdd = pozos.filter(pozoItem => {
        const pozoIdentifier = this.getPozoIdentifier(pozoItem);
        if (pozoCollectionIdentifiers.includes(pozoIdentifier)) {
          return false;
        }
        pozoCollectionIdentifiers.push(pozoIdentifier);
        return true;
      });
      return [...pozosToAdd, ...pozoCollection];
    }
    return pozoCollection;
  }

  protected convertDateFromClient<T extends IPozo | NewPozo | PartialUpdatePozo>(pozo: T): RestOf<T> {
    return {
      ...pozo,
      createdAt: pozo.createdAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPozo: RestPozo): IPozo {
    return {
      ...restPozo,
      createdAt: restPozo.createdAt ? dayjs(restPozo.createdAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPozo>): HttpResponse<IPozo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPozo[]>): HttpResponse<IPozo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
