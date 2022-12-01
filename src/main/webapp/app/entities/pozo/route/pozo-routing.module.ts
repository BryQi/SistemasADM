import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PozoComponent } from '../list/pozo.component';
import { PozoDetailComponent } from '../detail/pozo-detail.component';
import { PozoUpdateComponent } from '../update/pozo-update.component';
import { PozoRoutingResolveService } from './pozo-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const pozoRoute: Routes = [
  {
    path: '',
    component: PozoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PozoDetailComponent,
    resolve: {
      pozo: PozoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PozoUpdateComponent,
    resolve: {
      pozo: PozoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PozoUpdateComponent,
    resolve: {
      pozo: PozoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pozoRoute)],
  exports: [RouterModule],
})
export class PozoRoutingModule {}
