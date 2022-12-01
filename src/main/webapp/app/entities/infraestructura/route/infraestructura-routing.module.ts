import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InfraestructuraComponent } from '../list/infraestructura.component';
import { InfraestructuraDetailComponent } from '../detail/infraestructura-detail.component';
import { InfraestructuraUpdateComponent } from '../update/infraestructura-update.component';
import { InfraestructuraRoutingResolveService } from './infraestructura-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const infraestructuraRoute: Routes = [
  {
    path: '',
    component: InfraestructuraComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InfraestructuraDetailComponent,
    resolve: {
      infraestructura: InfraestructuraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InfraestructuraUpdateComponent,
    resolve: {
      infraestructura: InfraestructuraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InfraestructuraUpdateComponent,
    resolve: {
      infraestructura: InfraestructuraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(infraestructuraRoute)],
  exports: [RouterModule],
})
export class InfraestructuraRoutingModule {}
