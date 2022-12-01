import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PozoFormService, PozoFormGroup } from './pozo-form.service';
import { IPozo } from '../pozo.model';
import { PozoService } from '../service/pozo.service';
import { Ubicacion } from 'app/entities/enumerations/ubicacion.model';
import { TipoPozo } from 'app/entities/enumerations/tipo-pozo.model';

@Component({
  selector: 'jhi-pozo-update',
  templateUrl: './pozo-update.component.html',
})
export class PozoUpdateComponent implements OnInit {
  isSaving = false;
  pozo: IPozo | null = null;
  ubicacionValues = Object.keys(Ubicacion);
  tipoPozoValues = Object.keys(TipoPozo);

  editForm: PozoFormGroup = this.pozoFormService.createPozoFormGroup();

  constructor(protected pozoService: PozoService, protected pozoFormService: PozoFormService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pozo }) => {
      this.pozo = pozo;
      if (pozo) {
        this.updateForm(pozo);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pozo = this.pozoFormService.getPozo(this.editForm);
    if (pozo.id !== null) {
      this.subscribeToSaveResponse(this.pozoService.update(pozo));
    } else {
      this.subscribeToSaveResponse(this.pozoService.create(pozo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPozo>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(pozo: IPozo): void {
    this.pozo = pozo;
    this.pozoFormService.resetForm(this.editForm, pozo);
  }
}
