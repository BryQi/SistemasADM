import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DespliegueInfraestructuraTroncalDistribucionComponent } from './list/despliegue-infraestructura-troncal-distribucion.component';
import { DespliegueInfraestructuraTroncalDistribucionDetailComponent } from './detail/despliegue-infraestructura-troncal-distribucion-detail.component';
import { DespliegueInfraestructuraTroncalDistribucionUpdateComponent } from './update/despliegue-infraestructura-troncal-distribucion-update.component';
import { DespliegueInfraestructuraTroncalDistribucionDeleteDialogComponent } from './delete/despliegue-infraestructura-troncal-distribucion-delete-dialog.component';
import { DespliegueInfraestructuraTroncalDistribucionRoutingModule } from './route/despliegue-infraestructura-troncal-distribucion-routing.module';

@NgModule({
  imports: [SharedModule, DespliegueInfraestructuraTroncalDistribucionRoutingModule],
  declarations: [
    DespliegueInfraestructuraTroncalDistribucionComponent,
    DespliegueInfraestructuraTroncalDistribucionDetailComponent,
    DespliegueInfraestructuraTroncalDistribucionUpdateComponent,
    DespliegueInfraestructuraTroncalDistribucionDeleteDialogComponent,
  ],
})
export class DespliegueInfraestructuraTroncalDistribucionModule {}
