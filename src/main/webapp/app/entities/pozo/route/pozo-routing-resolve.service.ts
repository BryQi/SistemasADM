import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPozo } from '../pozo.model';
import { PozoService } from '../service/pozo.service';

@Injectable({ providedIn: 'root' })
export class PozoRoutingResolveService implements Resolve<IPozo | null> {
  constructor(protected service: PozoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPozo | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pozo: HttpResponse<IPozo>) => {
          if (pozo.body) {
            return of(pozo.body);
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
