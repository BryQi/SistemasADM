import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDespliegueinfraestructuradispersion } from '../despliegueinfraestructuradispersion.model';
import { DespliegueinfraestructuradispersionService } from '../service/despliegueinfraestructuradispersion.service';

@Injectable({ providedIn: 'root' })
export class DespliegueinfraestructuradispersionRoutingResolveService implements Resolve<IDespliegueinfraestructuradispersion | null> {
  constructor(protected service: DespliegueinfraestructuradispersionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDespliegueinfraestructuradispersion | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((despliegueinfraestructuradispersion: HttpResponse<IDespliegueinfraestructuradispersion>) => {
          if (despliegueinfraestructuradispersion.body) {
            return of(despliegueinfraestructuradispersion.body);
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
