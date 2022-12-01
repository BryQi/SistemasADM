import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PagoFormService, PagoFormGroup } from './pago-form.service';
import { IPago } from '../pago.model';
import { PagoService } from '../service/pago.service';
import { IDespliegueInfraestructuraTroncalDistribucion } from 'app/entities/despliegue-infraestructura-troncal-distribucion/despliegue-infraestructura-troncal-distribucion.model';
import { DespliegueInfraestructuraTroncalDistribucionService } from 'app/entities/despliegue-infraestructura-troncal-distribucion/service/despliegue-infraestructura-troncal-distribucion.service';
import { IDespliegueinfraestructuradispersion } from 'app/entities/despliegueinfraestructuradispersion/despliegueinfraestructuradispersion.model';
import { DespliegueinfraestructuradispersionService } from 'app/entities/despliegueinfraestructuradispersion/service/despliegueinfraestructuradispersion.service';

@Component({
  selector: 'jhi-pago-update',
  templateUrl: './pago-update.component.html',
})
export class PagoUpdateComponent implements OnInit {
  isSaving = false;
  pago: IPago | null = null;

  despliegueInfraestructuraTroncalDistribucionsSharedCollection: IDespliegueInfraestructuraTroncalDistribucion[] = [];
  despliegueinfraestructuradispersionsSharedCollection: IDespliegueinfraestructuradispersion[] = [];

  editForm: PagoFormGroup = this.pagoFormService.createPagoFormGroup();

  constructor(
    protected pagoService: PagoService,
    protected pagoFormService: PagoFormService,
    protected despliegueInfraestructuraTroncalDistribucionService: DespliegueInfraestructuraTroncalDistribucionService,
    protected despliegueinfraestructuradispersionService: DespliegueinfraestructuradispersionService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDespliegueInfraestructuraTroncalDistribucion = (
    o1: IDespliegueInfraestructuraTroncalDistribucion | null,
    o2: IDespliegueInfraestructuraTroncalDistribucion | null
  ): boolean => this.despliegueInfraestructuraTroncalDistribucionService.compareDespliegueInfraestructuraTroncalDistribucion(o1, o2);

  compareDespliegueinfraestructuradispersion = (
    o1: IDespliegueinfraestructuradispersion | null,
    o2: IDespliegueinfraestructuradispersion | null
  ): boolean => this.despliegueinfraestructuradispersionService.compareDespliegueinfraestructuradispersion(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pago }) => {
      this.pago = pago;
      if (pago) {
        this.updateForm(pago);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pago = this.pagoFormService.getPago(this.editForm);
    if (pago.id !== null) {
      this.subscribeToSaveResponse(this.pagoService.update(pago));
    } else {
      this.subscribeToSaveResponse(this.pagoService.create(pago));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPago>>): void {
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

  protected updateForm(pago: IPago): void {
    this.pago = pago;
    this.pagoFormService.resetForm(this.editForm, pago);

    this.despliegueInfraestructuraTroncalDistribucionsSharedCollection =
      this.despliegueInfraestructuraTroncalDistribucionService.addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing<IDespliegueInfraestructuraTroncalDistribucion>(
        this.despliegueInfraestructuraTroncalDistribucionsSharedCollection,
        pago.idDespliegueInfraestructuraTroncalDistribucion
      );
    this.despliegueinfraestructuradispersionsSharedCollection =
      this.despliegueinfraestructuradispersionService.addDespliegueinfraestructuradispersionToCollectionIfMissing<IDespliegueinfraestructuradispersion>(
        this.despliegueinfraestructuradispersionsSharedCollection,
        pago.idDespliegueinfraestructuradispersion
      );
  }

  protected loadRelationshipsOptions(): void {
    this.despliegueInfraestructuraTroncalDistribucionService
      .query()
      .pipe(map((res: HttpResponse<IDespliegueInfraestructuraTroncalDistribucion[]>) => res.body ?? []))
      .pipe(
        map((despliegueInfraestructuraTroncalDistribucions: IDespliegueInfraestructuraTroncalDistribucion[]) =>
          this.despliegueInfraestructuraTroncalDistribucionService.addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing<IDespliegueInfraestructuraTroncalDistribucion>(
            despliegueInfraestructuraTroncalDistribucions,
            this.pago?.idDespliegueInfraestructuraTroncalDistribucion
          )
        )
      )
      .subscribe(
        (despliegueInfraestructuraTroncalDistribucions: IDespliegueInfraestructuraTroncalDistribucion[]) =>
          (this.despliegueInfraestructuraTroncalDistribucionsSharedCollection = despliegueInfraestructuraTroncalDistribucions)
      );

    this.despliegueinfraestructuradispersionService
      .query()
      .pipe(map((res: HttpResponse<IDespliegueinfraestructuradispersion[]>) => res.body ?? []))
      .pipe(
        map((despliegueinfraestructuradispersions: IDespliegueinfraestructuradispersion[]) =>
          this.despliegueinfraestructuradispersionService.addDespliegueinfraestructuradispersionToCollectionIfMissing<IDespliegueinfraestructuradispersion>(
            despliegueinfraestructuradispersions,
            this.pago?.idDespliegueinfraestructuradispersion
          )
        )
      )
      .subscribe(
        (despliegueinfraestructuradispersions: IDespliegueinfraestructuradispersion[]) =>
          (this.despliegueinfraestructuradispersionsSharedCollection = despliegueinfraestructuradispersions)
      );
  }
}
