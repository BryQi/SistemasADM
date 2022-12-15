import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PagoFormService, PagoFormGroup } from './pago-form.service';
import { IPago } from '../pago.model';
import { PagoService } from '../service/pago.service';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';
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

  proveedorsSharedCollection: IProveedor[] = [];
  despliegueInfraestructuraTroncalDistribucionsSharedCollection: IDespliegueInfraestructuraTroncalDistribucion[] = [];
  despliegueinfraestructuradispersionsSharedCollection: IDespliegueinfraestructuradispersion[] = [];

  editForm: PagoFormGroup = this.pagoFormService.createPagoFormGroup();

  constructor(
    protected pagoService: PagoService,
    protected pagoFormService: PagoFormService,
    protected proveedorService: ProveedorService,
    protected despliegueInfraestructuraTroncalDistribucionService: DespliegueInfraestructuraTroncalDistribucionService,
    protected despliegueinfraestructuradispersionService: DespliegueinfraestructuradispersionService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProveedor = (o1: IProveedor | null, o2: IProveedor | null): boolean => this.proveedorService.compareProveedor(o1, o2);

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

    this.proveedorsSharedCollection = this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(
      this.proveedorsSharedCollection,
      pago.razonSocial
    );
    this.despliegueInfraestructuraTroncalDistribucionsSharedCollection =
      this.despliegueInfraestructuraTroncalDistribucionService.addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing<IDespliegueInfraestructuraTroncalDistribucion>(
        this.despliegueInfraestructuraTroncalDistribucionsSharedCollection,
        pago.calculoValorPago
      );
    this.despliegueinfraestructuradispersionsSharedCollection =
      this.despliegueinfraestructuradispersionService.addDespliegueinfraestructuradispersionToCollectionIfMissing<IDespliegueinfraestructuradispersion>(
        this.despliegueinfraestructuradispersionsSharedCollection,
        pago.calculoValorPagoD
      );
  }

  protected loadRelationshipsOptions(): void {
    this.proveedorService
      .query()
      .pipe(map((res: HttpResponse<IProveedor[]>) => res.body ?? []))
      .pipe(
        map((proveedors: IProveedor[]) =>
          this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(proveedors, this.pago?.razonSocial)
        )
      )
      .subscribe((proveedors: IProveedor[]) => (this.proveedorsSharedCollection = proveedors));

    this.despliegueInfraestructuraTroncalDistribucionService
      .query()
      .pipe(map((res: HttpResponse<IDespliegueInfraestructuraTroncalDistribucion[]>) => res.body ?? []))
      .pipe(
        map((despliegueInfraestructuraTroncalDistribucions: IDespliegueInfraestructuraTroncalDistribucion[]) =>
          this.despliegueInfraestructuraTroncalDistribucionService.addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing<IDespliegueInfraestructuraTroncalDistribucion>(
            despliegueInfraestructuraTroncalDistribucions,
            this.pago?.calculoValorPago
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
            this.pago?.calculoValorPagoD
          )
        )
      )
      .subscribe(
        (despliegueinfraestructuradispersions: IDespliegueinfraestructuradispersion[]) =>
          (this.despliegueinfraestructuradispersionsSharedCollection = despliegueinfraestructuradispersions)
      );
  }
}
