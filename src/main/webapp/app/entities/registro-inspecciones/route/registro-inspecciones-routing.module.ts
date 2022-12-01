import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RegistroInspeccionesComponent } from '../list/registro-inspecciones.component';
import { RegistroInspeccionesDetailComponent } from '../detail/registro-inspecciones-detail.component';
import { RegistroInspeccionesUpdateComponent } from '../update/registro-inspecciones-update.component';
import { RegistroInspeccionesRoutingResolveService } from './registro-inspecciones-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const registroInspeccionesRoute: Routes = [
  {
    path: '',
    component: RegistroInspeccionesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RegistroInspeccionesDetailComponent,
    resolve: {
      registroInspecciones: RegistroInspeccionesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RegistroInspeccionesUpdateComponent,
    resolve: {
      registroInspecciones: RegistroInspeccionesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RegistroInspeccionesUpdateComponent,
    resolve: {
      registroInspecciones: RegistroInspeccionesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(registroInspeccionesRoute)],
  exports: [RouterModule],
})
export class RegistroInspeccionesRoutingModule {}
