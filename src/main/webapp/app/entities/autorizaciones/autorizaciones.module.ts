import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AutorizacionesComponent } from './list/autorizaciones.component';
import { AutorizacionesDetailComponent } from './detail/autorizaciones-detail.component';
import { AutorizacionesUpdateComponent } from './update/autorizaciones-update.component';
import { AutorizacionesDeleteDialogComponent } from './delete/autorizaciones-delete-dialog.component';
import { AutorizacionesRoutingModule } from './route/autorizaciones-routing.module';

@NgModule({
  imports: [SharedModule, AutorizacionesRoutingModule],
  declarations: [
    AutorizacionesComponent,
    AutorizacionesDetailComponent,
    AutorizacionesUpdateComponent,
    AutorizacionesDeleteDialogComponent,
  ],
})
export class AutorizacionesModule {}
