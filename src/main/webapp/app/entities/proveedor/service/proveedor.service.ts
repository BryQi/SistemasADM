import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProveedor, NewProveedor } from '../proveedor.model';

export type PartialUpdateProveedor = Partial<IProveedor> & Pick<IProveedor, 'id'>;

type RestOf<T extends IProveedor | NewProveedor> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

export type RestProveedor = RestOf<IProveedor>;

export type NewRestProveedor = RestOf<NewProveedor>;

export type PartialUpdateRestProveedor = RestOf<PartialUpdateProveedor>;

export type EntityResponseType = HttpResponse<IProveedor>;
export type EntityArrayResponseType = HttpResponse<IProveedor[]>;

@Injectable({ providedIn: 'root' })
export class ProveedorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/proveedors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(proveedor: NewProveedor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proveedor);
    return this.http
      .post<RestProveedor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(proveedor: IProveedor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proveedor);
    return this.http
      .put<RestProveedor>(`${this.resourceUrl}/${this.getProveedorIdentifier(proveedor)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(proveedor: PartialUpdateProveedor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proveedor);
    return this.http
      .patch<RestProveedor>(`${this.resourceUrl}/${this.getProveedorIdentifier(proveedor)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestProveedor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProveedor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProveedorIdentifier(proveedor: Pick<IProveedor, 'id'>): number {
    return proveedor.id;
  }

  compareProveedor(o1: Pick<IProveedor, 'id'> | null, o2: Pick<IProveedor, 'id'> | null): boolean {
    return o1 && o2 ? this.getProveedorIdentifier(o1) === this.getProveedorIdentifier(o2) : o1 === o2;
  }

  addProveedorToCollectionIfMissing<Type extends Pick<IProveedor, 'id'>>(
    proveedorCollection: Type[],
    ...proveedorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const proveedors: Type[] = proveedorsToCheck.filter(isPresent);
    if (proveedors.length > 0) {
      const proveedorCollectionIdentifiers = proveedorCollection.map(proveedorItem => this.getProveedorIdentifier(proveedorItem)!);
      const proveedorsToAdd = proveedors.filter(proveedorItem => {
        const proveedorIdentifier = this.getProveedorIdentifier(proveedorItem);
        if (proveedorCollectionIdentifiers.includes(proveedorIdentifier)) {
          return false;
        }
        proveedorCollectionIdentifiers.push(proveedorIdentifier);
        return true;
      });
      return [...proveedorsToAdd, ...proveedorCollection];
    }
    return proveedorCollection;
  }

  protected convertDateFromClient<T extends IProveedor | NewProveedor | PartialUpdateProveedor>(proveedor: T): RestOf<T> {
    return {
      ...proveedor,
      createdAt: proveedor.createdAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restProveedor: RestProveedor): IProveedor {
    return {
      ...restProveedor,
      createdAt: restProveedor.createdAt ? dayjs(restProveedor.createdAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestProveedor>): HttpResponse<IProveedor> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestProveedor[]>): HttpResponse<IProveedor[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
