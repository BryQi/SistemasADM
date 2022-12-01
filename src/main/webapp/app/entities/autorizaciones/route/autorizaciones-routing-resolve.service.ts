import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAutorizaciones } from '../autorizaciones.model';
import { AutorizacionesService } from '../service/autorizaciones.service';

@Injectable({ providedIn: 'root' })
export class AutorizacionesRoutingResolveService implements Resolve<IAutorizaciones | null> {
  constructor(protected service: AutorizacionesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAutorizaciones | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((autorizaciones: HttpResponse<IAutorizaciones>) => {
          if (autorizaciones.body) {
            return of(autorizaciones.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
