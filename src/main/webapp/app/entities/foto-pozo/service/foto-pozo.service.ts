import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFotoPozo, NewFotoPozo } from '../foto-pozo.model';

export type PartialUpdateFotoPozo = Partial<IFotoPozo> & Pick<IFotoPozo, 'id'>;

type RestOf<T extends IFotoPozo | NewFotoPozo> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

export type RestFotoPozo = RestOf<IFotoPozo>;

export type NewRestFotoPozo = RestOf<NewFotoPozo>;

export type PartialUpdateRestFotoPozo = RestOf<PartialUpdateFotoPozo>;

export type EntityResponseType = HttpResponse<IFotoPozo>;
export type EntityArrayResponseType = HttpResponse<IFotoPozo[]>;

@Injectable({ providedIn: 'root' })
export class FotoPozoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/foto-pozos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fotoPozo: NewFotoPozo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fotoPozo);
    return this.http
      .post<RestFotoPozo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(fotoPozo: IFotoPozo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fotoPozo);
    return this.http
      .put<RestFotoPozo>(`${this.resourceUrl}/${this.getFotoPozoIdentifier(fotoPozo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(fotoPozo: PartialUpdateFotoPozo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fotoPozo);
    return this.http
      .patch<RestFotoPozo>(`${this.resourceUrl}/${this.getFotoPozoIdentifier(fotoPozo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFotoPozo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFotoPozo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFotoPozoIdentifier(fotoPozo: Pick<IFotoPozo, 'id'>): number {
    return fotoPozo.id;
  }

  compareFotoPozo(o1: Pick<IFotoPozo, 'id'> | null, o2: Pick<IFotoPozo, 'id'> | null): boolean {
    return o1 && o2 ? this.getFotoPozoIdentifier(o1) === this.getFotoPozoIdentifier(o2) : o1 === o2;
  }

  addFotoPozoToCollectionIfMissing<Type extends Pick<IFotoPozo, 'id'>>(
    fotoPozoCollection: Type[],
    ...fotoPozosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fotoPozos: Type[] = fotoPozosToCheck.filter(isPresent);
    if (fotoPozos.length > 0) {
      const fotoPozoCollectionIdentifiers = fotoPozoCollection.map(fotoPozoItem => this.getFotoPozoIdentifier(fotoPozoItem)!);
      const fotoPozosToAdd = fotoPozos.filter(fotoPozoItem => {
        const fotoPozoIdentifier = this.getFotoPozoIdentifier(fotoPozoItem);
        if (fotoPozoCollectionIdentifiers.includes(fotoPozoIdentifier)) {
          return false;
        }
        fotoPozoCollectionIdentifiers.push(fotoPozoIdentifier);
        return true;
      });
      return [...fotoPozosToAdd, ...fotoPozoCollection];
    }
    return fotoPozoCollection;
  }

  protected convertDateFromClient<T extends IFotoPozo | NewFotoPozo | PartialUpdateFotoPozo>(fotoPozo: T): RestOf<T> {
    return {
      ...fotoPozo,
      createdAt: fotoPozo.createdAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restFotoPozo: RestFotoPozo): IFotoPozo {
    return {
      ...restFotoPozo,
      createdAt: restFotoPozo.createdAt ? dayjs(restFotoPozo.createdAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFotoPozo>): HttpResponse<IFotoPozo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFotoPozo[]>): HttpResponse<IFotoPozo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
