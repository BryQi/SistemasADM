import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import {
  DespliegueInfraestructuraTroncalDistribucionFormService,
  DespliegueInfraestructuraTroncalDistribucionFormGroup,
} from './despliegue-infraestructura-troncal-distribucion-form.service';
import { IDespliegueInfraestructuraTroncalDistribucion } from '../despliegue-infraestructura-troncal-distribucion.model';
import { DespliegueInfraestructuraTroncalDistribucionService } from '../service/despliegue-infraestructura-troncal-distribucion.service';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { PozoService } from 'app/entities/pozo/service/pozo.service';

@Component({
  selector: 'jhi-despliegue-infraestructura-troncal-distribucion-update',
  templateUrl: './despliegue-infraestructura-troncal-distribucion-update.component.html',
})
export class DespliegueInfraestructuraTroncalDistribucionUpdateComponent implements OnInit {
  isSaving = false;
  despliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion | null = null;

  pozosSharedCollection: IPozo[] = [];

  editForm: DespliegueInfraestructuraTroncalDistribucionFormGroup =
    this.despliegueInfraestructuraTroncalDistribucionFormService.createDespliegueInfraestructuraTroncalDistribucionFormGroup();

  constructor(
    protected despliegueInfraestructuraTroncalDistribucionService: DespliegueInfraestructuraTroncalDistribucionService,
    protected despliegueInfraestructuraTroncalDistribucionFormService: DespliegueInfraestructuraTroncalDistribucionFormService,
    protected pozoService: PozoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePozo = (o1: IPozo | null, o2: IPozo | null): boolean => this.pozoService.comparePozo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ despliegueInfraestructuraTroncalDistribucion }) => {
      this.despliegueInfraestructuraTroncalDistribucion = despliegueInfraestructuraTroncalDistribucion;
      if (despliegueInfraestructuraTroncalDistribucion) {
        this.updateForm(despliegueInfraestructuraTroncalDistribucion);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const despliegueInfraestructuraTroncalDistribucion =
      this.despliegueInfraestructuraTroncalDistribucionFormService.getDespliegueInfraestructuraTroncalDistribucion(this.editForm);
    if (despliegueInfraestructuraTroncalDistribucion.id !== null) {
      this.subscribeToSaveResponse(
        this.despliegueInfraestructuraTroncalDistribucionService.update(despliegueInfraestructuraTroncalDistribucion)
      );
    } else {
      this.subscribeToSaveResponse(
        this.despliegueInfraestructuraTroncalDistribucionService.create(despliegueInfraestructuraTroncalDistribucion)
      );
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDespliegueInfraestructuraTroncalDistribucion>>): void {
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

  protected updateForm(despliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion): void {
    this.despliegueInfraestructuraTroncalDistribucion = despliegueInfraestructuraTroncalDistribucion;
    this.despliegueInfraestructuraTroncalDistribucionFormService.resetForm(this.editForm, despliegueInfraestructuraTroncalDistribucion);

    this.pozosSharedCollection = this.pozoService.addPozoToCollectionIfMissing<IPozo>(
      this.pozosSharedCollection,
      ...(despliegueInfraestructuraTroncalDistribucion.pozos ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pozoService
      .query()
      .pipe(map((res: HttpResponse<IPozo[]>) => res.body ?? []))
      .pipe(
        map((pozos: IPozo[]) =>
          this.pozoService.addPozoToCollectionIfMissing<IPozo>(pozos, ...(this.despliegueInfraestructuraTroncalDistribucion?.pozos ?? []))
        )
      )
      .subscribe((pozos: IPozo[]) => (this.pozosSharedCollection = pozos));
  }
}