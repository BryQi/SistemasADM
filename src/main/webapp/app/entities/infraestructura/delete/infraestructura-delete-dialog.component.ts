import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInfraestructura } from '../infraestructura.model';
import { InfraestructuraService } from '../service/infraestructura.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './infraestructura-delete-dialog.component.html',
})
export class InfraestructuraDeleteDialogComponent {
  infraestructura?: IInfraestructura;

  constructor(protected infraestructuraService: InfraestructuraService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.infraestructuraService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
