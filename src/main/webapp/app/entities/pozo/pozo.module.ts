import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PozoComponent } from './list/pozo.component';
import { PozoDetailComponent } from './detail/pozo-detail.component';
import { PozoUpdateComponent } from './update/pozo-update.component';
import { PozoDeleteDialogComponent } from './delete/pozo-delete-dialog.component';
import { PozoRoutingModule } from './route/pozo-routing.module';

@NgModule({
  imports: [SharedModule, PozoRoutingModule],
  declarations: [PozoComponent, PozoDetailComponent, PozoUpdateComponent, PozoDeleteDialogComponent],
})
export class PozoModule {}
