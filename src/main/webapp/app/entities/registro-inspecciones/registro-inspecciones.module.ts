import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RegistroInspeccionesComponent } from './list/registro-inspecciones.component';
import { RegistroInspeccionesDetailComponent } from './detail/registro-inspecciones-detail.component';
import { RegistroInspeccionesUpdateComponent } from './update/registro-inspecciones-update.component';
import { RegistroInspeccionesDeleteDialogComponent } from './delete/registro-inspecciones-delete-dialog.component';
import { RegistroInspeccionesRoutingModule } from './route/registro-inspecciones-routing.module';

@NgModule({
  imports: [SharedModule, RegistroInspeccionesRoutingModule],
  declarations: [
    RegistroInspeccionesComponent,
    RegistroInspeccionesDetailComponent,
    RegistroInspeccionesUpdateComponent,
    RegistroInspeccionesDeleteDialogComponent,
  ],
})
export class RegistroInspeccionesModule {}
