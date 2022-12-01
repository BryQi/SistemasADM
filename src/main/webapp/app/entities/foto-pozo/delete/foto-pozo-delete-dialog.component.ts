import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFotoPozo } from '../foto-pozo.model';
import { FotoPozoService } from '../service/foto-pozo.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './foto-pozo-delete-dialog.component.html',
})
export class FotoPozoDeleteDialogComponent {
  fotoPozo?: IFotoPozo;

  constructor(protected fotoPozoService: FotoPozoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fotoPozoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
