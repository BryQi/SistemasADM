import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRegistroInspecciones } from '../registro-inspecciones.model';
import { RegistroInspeccionesService } from '../service/registro-inspecciones.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './registro-inspecciones-delete-dialog.component.html',
})
export class RegistroInspeccionesDeleteDialogComponent {
  registroInspecciones?: IRegistroInspecciones;

  constructor(protected registroInspeccionesService: RegistroInspeccionesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.registroInspeccionesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
