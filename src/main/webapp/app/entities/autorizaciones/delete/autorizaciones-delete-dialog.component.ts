import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAutorizaciones } from '../autorizaciones.model';
import { AutorizacionesService } from '../service/autorizaciones.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './autorizaciones-delete-dialog.component.html',
})
export class AutorizacionesDeleteDialogComponent {
  autorizaciones?: IAutorizaciones;

  constructor(protected autorizacionesService: AutorizacionesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.autorizacionesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
