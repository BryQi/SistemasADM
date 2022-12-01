import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DespliegueinfraestructuradispersionComponent } from '../list/despliegueinfraestructuradispersion.component';
import { DespliegueinfraestructuradispersionDetailComponent } from '../detail/despliegueinfraestructuradispersion-detail.component';
import { DespliegueinfraestructuradispersionUpdateComponent } from '../update/despliegueinfraestructuradispersion-update.component';
import { DespliegueinfraestructuradispersionRoutingResolveService } from './despliegueinfraestructuradispersion-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const despliegueinfraestructuradispersionRoute: Routes = [
  {
    path: '',
    component: DespliegueinfraestructuradispersionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DespliegueinfraestructuradispersionDetailComponent,
    resolve: {
      despliegueinfraestructuradispersion: DespliegueinfraestructuradispersionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DespliegueinfraestructuradispersionUpdateComponent,
    resolve: {
      despliegueinfraestructuradispersion: DespliegueinfraestructuradispersionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DespliegueinfraestructuradispersionUpdateComponent,
    resolve: {
      despliegueinfraestructuradispersion: DespliegueinfraestructuradispersionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(despliegueinfraestructuradispersionRoute)],
  exports: [RouterModule],
})
export class DespliegueinfraestructuradispersionRoutingModule {}
