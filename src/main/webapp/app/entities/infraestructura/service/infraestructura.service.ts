import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInfraestructura, NewInfraestructura } from '../infraestructura.model';

export type PartialUpdateInfraestructura = Partial<IInfraestructura> & Pick<IInfraestructura, 'id'>;

type RestOf<T extends IInfraestructura | NewInfraestructura> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

export type RestInfraestructura = RestOf<IInfraestructura>;

export type NewRestInfraestructura = RestOf<NewInfraestructura>;

export type PartialUpdateRestInfraestructura = RestOf<PartialUpdateInfraestructura>;

export type EntityResponseType = HttpResponse<IInfraestructura>;
export type EntityArrayResponseType = HttpResponse<IInfraestructura[]>;

@Injectable({ providedIn: 'root' })
export class InfraestructuraService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/infraestructuras');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(infraestructura: NewInfraestructura): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(infraestructura);
    return this.http
      .post<RestInfraestructura>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(infraestructura: IInfraestructura): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(infraestructura);
    return this.http
      .put<RestInfraestructura>(`${this.resourceUrl}/${this.getInfraestructuraIdentifier(infraestructura)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(infraestructura: PartialUpdateInfraestructura): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(infraestructura);
    return this.http
      .patch<RestInfraestructura>(`${this.resourceUrl}/${this.getInfraestructuraIdentifier(infraestructura)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestInfraestructura>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestInfraestructura[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInfraestructuraIdentifier(infraestructura: Pick<IInfraestructura, 'id'>): number {
    return infraestructura.id;
  }

  compareInfraestructura(o1: Pick<IInfraestructura, 'id'> | null, o2: Pick<IInfraestructura, 'id'> | null): boolean {
    return o1 && o2 ? this.getInfraestructuraIdentifier(o1) === this.getInfraestructuraIdentifier(o2) : o1 === o2;
  }

  addInfraestructuraToCollectionIfMissing<Type extends Pick<IInfraestructura, 'id'>>(
    infraestructuraCollection: Type[],
    ...infraestructurasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const infraestructuras: Type[] = infraestructurasToCheck.filter(isPresent);
    if (infraestructuras.length > 0) {
      const infraestructuraCollectionIdentifiers = infraestructuraCollection.map(
        infraestructuraItem => this.getInfraestructuraIdentifier(infraestructuraItem)!
      );
      const infraestructurasToAdd = infraestructuras.filter(infraestructuraItem => {
        const infraestructuraIdentifier = this.getInfraestructuraIdentifier(infraestructuraItem);
        if (infraestructuraCollectionIdentifiers.includes(infraestructuraIdentifier)) {
          return false;
        }
        infraestructuraCollectionIdentifiers.push(infraestructuraIdentifier);
        return true;
      });
      return [...infraestructurasToAdd, ...infraestructuraCollection];
    }
    return infraestructuraCollection;
  }

  protected convertDateFromClient<T extends IInfraestructura | NewInfraestructura | PartialUpdateInfraestructura>(
    infraestructura: T
  ): RestOf<T> {
    return {
      ...infraestructura,
      createdAt: infraestructura.createdAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restInfraestructura: RestInfraestructura): IInfraestructura {
    return {
      ...restInfraestructura,
      createdAt: restInfraestructura.createdAt ? dayjs(restInfraestructura.createdAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestInfraestructura>): HttpResponse<IInfraestructura> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestInfraestructura[]>): HttpResponse<IInfraestructura[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
