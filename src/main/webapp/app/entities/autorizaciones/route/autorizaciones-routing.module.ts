import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AutorizacionesComponent } from '../list/autorizaciones.component';
import { AutorizacionesDetailComponent } from '../detail/autorizaciones-detail.component';
import { AutorizacionesUpdateComponent } from '../update/autorizaciones-update.component';
import { AutorizacionesRoutingResolveService } from './autorizaciones-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const autorizacionesRoute: Routes = [
  {
    path: '',
    component: AutorizacionesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AutorizacionesDetailComponent,
    resolve: {
      autorizaciones: AutorizacionesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AutorizacionesUpdateComponent,
    resolve: {
      autorizaciones: AutorizacionesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AutorizacionesUpdateComponent,
    resolve: {
      autorizaciones: AutorizacionesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(autorizacionesRoute)],
  exports: [RouterModule],
})
export class AutorizacionesRoutingModule {}
