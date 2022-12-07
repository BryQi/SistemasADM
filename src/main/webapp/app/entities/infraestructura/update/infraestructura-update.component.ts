import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { InfraestructuraFormService, InfraestructuraFormGroup } from './infraestructura-form.service';
import { IInfraestructura } from '../infraestructura.model';
import { InfraestructuraService } from '../service/infraestructura.service';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { PozoService } from 'app/entities/pozo/service/pozo.service';
import { Tipo } from 'app/entities/enumerations/tipo.model';

@Component({
  selector: 'jhi-infraestructura-update',
  templateUrl: './infraestructura-update.component.html',
})
export class InfraestructuraUpdateComponent implements OnInit {
  isSaving = false;
  infraestructura: IInfraestructura | null = null;
  tipoValues = Object.keys(Tipo);

  proveedorsSharedCollection: IProveedor[] = [];
  pozosSharedCollection: IPozo[] = [];

  editForm: InfraestructuraFormGroup = this.infraestructuraFormService.createInfraestructuraFormGroup();

  constructor(
    protected infraestructuraService: InfraestructuraService,
    protected infraestructuraFormService: InfraestructuraFormService,
    protected proveedorService: ProveedorService,
    protected pozoService: PozoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProveedor = (o1: IProveedor | null, o2: IProveedor | null): boolean => this.proveedorService.compareProveedor(o1, o2);

  comparePozo = (o1: IPozo | null, o2: IPozo | null): boolean => this.pozoService.comparePozo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ infraestructura }) => {
      this.infraestructura = infraestructura;
      if (infraestructura) {
        this.updateForm(infraestructura);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const infraestructura = this.infraestructuraFormService.getInfraestructura(this.editForm);
    if (infraestructura.id !== null) {
      this.subscribeToSaveResponse(this.infraestructuraService.update(infraestructura));
    } else {
      this.subscribeToSaveResponse(this.infraestructuraService.create(infraestructura));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInfraestructura>>): void {
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

  protected updateForm(infraestructura: IInfraestructura): void {
    this.infraestructura = infraestructura;
    this.infraestructuraFormService.resetForm(this.editForm, infraestructura);

    this.proveedorsSharedCollection = this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(
      this.proveedorsSharedCollection,
      infraestructura.idProveedor
    );
    this.pozosSharedCollection = this.pozoService.addPozoToCollectionIfMissing<IPozo>(
      this.pozosSharedCollection,
      ...(infraestructura.pozos ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.proveedorService
      .query()
      .pipe(map((res: HttpResponse<IProveedor[]>) => res.body ?? []))
      .pipe(
        map((proveedors: IProveedor[]) =>
          this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(proveedors, this.infraestructura?.idProveedor)
        )
      )
      .subscribe((proveedors: IProveedor[]) => (this.proveedorsSharedCollection = proveedors));

    this.pozoService
      .query()
      .pipe(map((res: HttpResponse<IPozo[]>) => res.body ?? []))
      .pipe(map((pozos: IPozo[]) => this.pozoService.addPozoToCollectionIfMissing<IPozo>(pozos, ...(this.infraestructura?.pozos ?? []))))
      .subscribe((pozos: IPozo[]) => (this.pozosSharedCollection = pozos));
  }
}
