import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DespliegueInfraestructuraTroncalDistribucionComponent } from '../list/despliegue-infraestructura-troncal-distribucion.component';
import { DespliegueInfraestructuraTroncalDistribucionDetailComponent } from '../detail/despliegue-infraestructura-troncal-distribucion-detail.component';
import { DespliegueInfraestructuraTroncalDistribucionUpdateComponent } from '../update/despliegue-infraestructura-troncal-distribucion-update.component';
import { DespliegueInfraestructuraTroncalDistribucionRoutingResolveService } from './despliegue-infraestructura-troncal-distribucion-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const despliegueInfraestructuraTroncalDistribucionRoute: Routes = [
  {
    path: '',
    component: DespliegueInfraestructuraTroncalDistribucionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DespliegueInfraestructuraTroncalDistribucionDetailComponent,
    resolve: {
      despliegueInfraestructuraTroncalDistribucion: DespliegueInfraestructuraTroncalDistribucionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DespliegueInfraestructuraTroncalDistribucionUpdateComponent,
    resolve: {
      despliegueInfraestructuraTroncalDistribucion: DespliegueInfraestructuraTroncalDistribucionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DespliegueInfraestructuraTroncalDistribucionUpdateComponent,
    resolve: {
      despliegueInfraestructuraTroncalDistribucion: DespliegueInfraestructuraTroncalDistribucionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(despliegueInfraestructuraTroncalDistribucionRoute)],
  exports: [RouterModule],
})
export class DespliegueInfraestructuraTroncalDistribucionRoutingModule {}
