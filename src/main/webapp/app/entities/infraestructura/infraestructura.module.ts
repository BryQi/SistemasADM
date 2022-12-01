import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InfraestructuraComponent } from './list/infraestructura.component';
import { InfraestructuraDetailComponent } from './detail/infraestructura-detail.component';
import { InfraestructuraUpdateComponent } from './update/infraestructura-update.component';
import { InfraestructuraDeleteDialogComponent } from './delete/infraestructura-delete-dialog.component';
import { InfraestructuraRoutingModule } from './route/infraestructura-routing.module';

@NgModule({
  imports: [SharedModule, InfraestructuraRoutingModule],
  declarations: [
    InfraestructuraComponent,
    InfraestructuraDetailComponent,
    InfraestructuraUpdateComponent,
    InfraestructuraDeleteDialogComponent,
  ],
})
export class InfraestructuraModule {}
