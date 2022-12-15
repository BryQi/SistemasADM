import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FotoPozoFormService, FotoPozoFormGroup } from './foto-pozo-form.service';
import { IFotoPozo } from '../foto-pozo.model';
import { FotoPozoService } from '../service/foto-pozo.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { PozoService } from 'app/entities/pozo/service/pozo.service';

@Component({
  selector: 'jhi-foto-pozo-update',
  templateUrl: './foto-pozo-update.component.html',
})
export class FotoPozoUpdateComponent implements OnInit {
  isSaving = false;
  fotoPozo: IFotoPozo | null = null;

  pozosSharedCollection: IPozo[] = [];

  editForm: FotoPozoFormGroup = this.fotoPozoFormService.createFotoPozoFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected fotoPozoService: FotoPozoService,
    protected fotoPozoFormService: FotoPozoFormService,
    protected pozoService: PozoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePozo = (o1: IPozo | null, o2: IPozo | null): boolean => this.pozoService.comparePozo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fotoPozo }) => {
      this.fotoPozo = fotoPozo;
      if (fotoPozo) {
        this.updateForm(fotoPozo);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('sistemasAdmApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fotoPozo = this.fotoPozoFormService.getFotoPozo(this.editForm);
    if (fotoPozo.id !== null) {
      this.subscribeToSaveResponse(this.fotoPozoService.update(fotoPozo));
    } else {
      this.subscribeToSaveResponse(this.fotoPozoService.create(fotoPozo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFotoPozo>>): void {
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

  protected updateForm(fotoPozo: IFotoPozo): void {
    this.fotoPozo = fotoPozo;
    this.fotoPozoFormService.resetForm(this.editForm, fotoPozo);

    this.pozosSharedCollection = this.pozoService.addPozoToCollectionIfMissing<IPozo>(this.pozosSharedCollection, fotoPozo.numeropozo);
  }

  protected loadRelationshipsOptions(): void {
    this.pozoService
      .query()
      .pipe(map((res: HttpResponse<IPozo[]>) => res.body ?? []))
      .pipe(map((pozos: IPozo[]) => this.pozoService.addPozoToCollectionIfMissing<IPozo>(pozos, this.fotoPozo?.numeropozo)))
      .subscribe((pozos: IPozo[]) => (this.pozosSharedCollection = pozos));
  }
}
