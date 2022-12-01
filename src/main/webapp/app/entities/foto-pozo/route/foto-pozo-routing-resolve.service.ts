import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFotoPozo } from '../foto-pozo.model';
import { FotoPozoService } from '../service/foto-pozo.service';

@Injectable({ providedIn: 'root' })
export class FotoPozoRoutingResolveService implements Resolve<IFotoPozo | null> {
  constructor(protected service: FotoPozoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFotoPozo | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fotoPozo: HttpResponse<IFotoPozo>) => {
          if (fotoPozo.body) {
            return of(fotoPozo.body);
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
