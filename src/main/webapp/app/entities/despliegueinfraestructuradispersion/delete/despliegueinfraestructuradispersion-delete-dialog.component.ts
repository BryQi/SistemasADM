import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDespliegueinfraestructuradispersion } from '../despliegueinfraestructuradispersion.model';
import { DespliegueinfraestructuradispersionService } from '../service/despliegueinfraestructuradispersion.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './despliegueinfraestructuradispersion-delete-dialog.component.html',
})
export class DespliegueinfraestructuradispersionDeleteDialogComponent {
  despliegueinfraestructuradispersion?: IDespliegueinfraestructuradispersion;

  constructor(
    protected despliegueinfraestructuradispersionService: DespliegueinfraestructuradispersionService,
    protected activeModal: NgbActiveModal
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.despliegueinfraestructuradispersionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
