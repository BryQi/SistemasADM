import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FotoPozoComponent } from '../list/foto-pozo.component';
import { FotoPozoDetailComponent } from '../detail/foto-pozo-detail.component';
import { FotoPozoUpdateComponent } from '../update/foto-pozo-update.component';
import { FotoPozoRoutingResolveService } from './foto-pozo-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fotoPozoRoute: Routes = [
  {
    path: '',
    component: FotoPozoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FotoPozoDetailComponent,
    resolve: {
      fotoPozo: FotoPozoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FotoPozoUpdateComponent,
    resolve: {
      fotoPozo: FotoPozoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FotoPozoUpdateComponent,
    resolve: {
      fotoPozo: FotoPozoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fotoPozoRoute)],
  exports: [RouterModule],
})
export class FotoPozoRoutingModule {}
