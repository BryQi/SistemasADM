import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FotoPozoComponent } from './list/foto-pozo.component';
import { FotoPozoDetailComponent } from './detail/foto-pozo-detail.component';
import { FotoPozoUpdateComponent } from './update/foto-pozo-update.component';
import { FotoPozoDeleteDialogComponent } from './delete/foto-pozo-delete-dialog.component';
import { FotoPozoRoutingModule } from './route/foto-pozo-routing.module';

@NgModule({
  imports: [SharedModule, FotoPozoRoutingModule],
  declarations: [FotoPozoComponent, FotoPozoDetailComponent, FotoPozoUpdateComponent, FotoPozoDeleteDialogComponent],
})
export class FotoPozoModule {}
