import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRegistroInspecciones } from '../registro-inspecciones.model';
import { RegistroInspeccionesService } from '../service/registro-inspecciones.service';

@Injectable({ providedIn: 'root' })
export class RegistroInspeccionesRoutingResolveService implements Resolve<IRegistroInspecciones | null> {
  constructor(protected service: RegistroInspeccionesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRegistroInspecciones | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((registroInspecciones: HttpResponse<IRegistroInspecciones>) => {
          if (registroInspecciones.body) {
            return of(registroInspecciones.body);
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
