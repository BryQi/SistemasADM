import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DespliegueinfraestructuradispersionComponent } from './list/despliegueinfraestructuradispersion.component';
import { DespliegueinfraestructuradispersionDetailComponent } from './detail/despliegueinfraestructuradispersion-detail.component';
import { DespliegueinfraestructuradispersionUpdateComponent } from './update/despliegueinfraestructuradispersion-update.component';
import { DespliegueinfraestructuradispersionDeleteDialogComponent } from './delete/despliegueinfraestructuradispersion-delete-dialog.component';
import { DespliegueinfraestructuradispersionRoutingModule } from './route/despliegueinfraestructuradispersion-routing.module';

@NgModule({
  imports: [SharedModule, DespliegueinfraestructuradispersionRoutingModule],
  declarations: [
    DespliegueinfraestructuradispersionComponent,
    DespliegueinfraestructuradispersionDetailComponent,
    DespliegueinfraestructuradispersionUpdateComponent,
    DespliegueinfraestructuradispersionDeleteDialogComponent,
  ],
})
export class DespliegueinfraestructuradispersionModule {}
