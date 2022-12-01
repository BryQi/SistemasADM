import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInfraestructura } from '../infraestructura.model';
import { InfraestructuraService } from '../service/infraestructura.service';

@Injectable({ providedIn: 'root' })
export class InfraestructuraRoutingResolveService implements Resolve<IInfraestructura | null> {
  constructor(protected service: InfraestructuraService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInfraestructura | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((infraestructura: HttpResponse<IInfraestructura>) => {
          if (infraestructura.body) {
            return of(infraestructura.body);
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
