import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDespliegueInfraestructuraTroncalDistribucion } from '../despliegue-infraestructura-troncal-distribucion.model';
import { DespliegueInfraestructuraTroncalDistribucionService } from '../service/despliegue-infraestructura-troncal-distribucion.service';

@Injectable({ providedIn: 'root' })
export class DespliegueInfraestructuraTroncalDistribucionRoutingResolveService
  implements Resolve<IDespliegueInfraestructuraTroncalDistribucion | null>
{
  constructor(protected service: DespliegueInfraestructuraTroncalDistribucionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDespliegueInfraestructuraTroncalDistribucion | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((despliegueInfraestructuraTroncalDistribucion: HttpResponse<IDespliegueInfraestructuraTroncalDistribucion>) => {
          if (despliegueInfraestructuraTroncalDistribucion.body) {
            return of(despliegueInfraestructuraTroncalDistribucion.body);
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
