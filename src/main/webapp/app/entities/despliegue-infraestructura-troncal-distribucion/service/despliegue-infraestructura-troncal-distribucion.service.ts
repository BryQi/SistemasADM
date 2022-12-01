import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import {
  IDespliegueInfraestructuraTroncalDistribucion,
  NewDespliegueInfraestructuraTroncalDistribucion,
} from '../despliegue-infraestructura-troncal-distribucion.model';

export type PartialUpdateDespliegueInfraestructuraTroncalDistribucion = Partial<IDespliegueInfraestructuraTroncalDistribucion> &
  Pick<IDespliegueInfraestructuraTroncalDistribucion, 'id'>;

type RestOf<T extends IDespliegueInfraestructuraTroncalDistribucion | NewDespliegueInfraestructuraTroncalDistribucion> = Omit<
  T,
  'createdAt'
> & {
  createdAt?: string | null;
};

export type RestDespliegueInfraestructuraTroncalDistribucion = RestOf<IDespliegueInfraestructuraTroncalDistribucion>;

export type NewRestDespliegueInfraestructuraTroncalDistribucion = RestOf<NewDespliegueInfraestructuraTroncalDistribucion>;

export type PartialUpdateRestDespliegueInfraestructuraTroncalDistribucion =
  RestOf<PartialUpdateDespliegueInfraestructuraTroncalDistribucion>;

export type EntityResponseType = HttpResponse<IDespliegueInfraestructuraTroncalDistribucion>;
export type EntityArrayResponseType = HttpResponse<IDespliegueInfraestructuraTroncalDistribucion[]>;

@Injectable({ providedIn: 'root' })
export class DespliegueInfraestructuraTroncalDistribucionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/despliegue-infraestructura-troncal-distribucions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(despliegueInfraestructuraTroncalDistribucion: NewDespliegueInfraestructuraTroncalDistribucion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(despliegueInfraestructuraTroncalDistribucion);
    return this.http
      .post<RestDespliegueInfraestructuraTroncalDistribucion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(despliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(despliegueInfraestructuraTroncalDistribucion);
    return this.http
      .put<RestDespliegueInfraestructuraTroncalDistribucion>(
        `${this.resourceUrl}/${this.getDespliegueInfraestructuraTroncalDistribucionIdentifier(
          despliegueInfraestructuraTroncalDistribucion
        )}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(
    despliegueInfraestructuraTroncalDistribucion: PartialUpdateDespliegueInfraestructuraTroncalDistribucion
  ): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(despliegueInfraestructuraTroncalDistribucion);
    return this.http
      .patch<RestDespliegueInfraestructuraTroncalDistribucion>(
        `${this.resourceUrl}/${this.getDespliegueInfraestructuraTroncalDistribucionIdentifier(
          despliegueInfraestructuraTroncalDistribucion
        )}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDespliegueInfraestructuraTroncalDistribucion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDespliegueInfraestructuraTroncalDistribucion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDespliegueInfraestructuraTroncalDistribucionIdentifier(
    despliegueInfraestructuraTroncalDistribucion: Pick<IDespliegueInfraestructuraTroncalDistribucion, 'id'>
  ): number {
    return despliegueInfraestructuraTroncalDistribucion.id;
  }

  compareDespliegueInfraestructuraTroncalDistribucion(
    o1: Pick<IDespliegueInfraestructuraTroncalDistribucion, 'id'> | null,
    o2: Pick<IDespliegueInfraestructuraTroncalDistribucion, 'id'> | null
  ): boolean {
    return o1 && o2
      ? this.getDespliegueInfraestructuraTroncalDistribucionIdentifier(o1) ===
          this.getDespliegueInfraestructuraTroncalDistribucionIdentifier(o2)
      : o1 === o2;
  }

  addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing<
    Type extends Pick<IDespliegueInfraestructuraTroncalDistribucion, 'id'>
  >(
    despliegueInfraestructuraTroncalDistribucionCollection: Type[],
    ...despliegueInfraestructuraTroncalDistribucionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const despliegueInfraestructuraTroncalDistribucions: Type[] = despliegueInfraestructuraTroncalDistribucionsToCheck.filter(isPresent);
    if (despliegueInfraestructuraTroncalDistribucions.length > 0) {
      const despliegueInfraestructuraTroncalDistribucionCollectionIdentifiers = despliegueInfraestructuraTroncalDistribucionCollection.map(
        despliegueInfraestructuraTroncalDistribucionItem =>
          this.getDespliegueInfraestructuraTroncalDistribucionIdentifier(despliegueInfraestructuraTroncalDistribucionItem)!
      );
      const despliegueInfraestructuraTroncalDistribucionsToAdd = despliegueInfraestructuraTroncalDistribucions.filter(
        despliegueInfraestructuraTroncalDistribucionItem => {
          const despliegueInfraestructuraTroncalDistribucionIdentifier = this.getDespliegueInfraestructuraTroncalDistribucionIdentifier(
            despliegueInfraestructuraTroncalDistribucionItem
          );
          if (
            despliegueInfraestructuraTroncalDistribucionCollectionIdentifiers.includes(
              despliegueInfraestructuraTroncalDistribucionIdentifier
            )
          ) {
            return false;
          }
          despliegueInfraestructuraTroncalDistribucionCollectionIdentifiers.push(despliegueInfraestructuraTroncalDistribucionIdentifier);
          return true;
        }
      );
      return [...despliegueInfraestructuraTroncalDistribucionsToAdd, ...despliegueInfraestructuraTroncalDistribucionCollection];
    }
    return despliegueInfraestructuraTroncalDistribucionCollection;
  }

  protected convertDateFromClient<
    T extends
      | IDespliegueInfraestructuraTroncalDistribucion
      | NewDespliegueInfraestructuraTroncalDistribucion
      | PartialUpdateDespliegueInfraestructuraTroncalDistribucion
  >(despliegueInfraestructuraTroncalDistribucion: T): RestOf<T> {
    return {
      ...despliegueInfraestructuraTroncalDistribucion,
      createdAt: despliegueInfraestructuraTroncalDistribucion.createdAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(
    restDespliegueInfraestructuraTroncalDistribucion: RestDespliegueInfraestructuraTroncalDistribucion
  ): IDespliegueInfraestructuraTroncalDistribucion {
    return {
      ...restDespliegueInfraestructuraTroncalDistribucion,
      createdAt: restDespliegueInfraestructuraTroncalDistribucion.createdAt
        ? dayjs(restDespliegueInfraestructuraTroncalDistribucion.createdAt)
        : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestDespliegueInfraestructuraTroncalDistribucion>
  ): HttpResponse<IDespliegueInfraestructuraTroncalDistribucion> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestDespliegueInfraestructuraTroncalDistribucion[]>
  ): HttpResponse<IDespliegueInfraestructuraTroncalDistribucion[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
