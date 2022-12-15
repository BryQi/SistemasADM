import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AutorizacionesFormService, AutorizacionesFormGroup } from './autorizaciones-form.service';
import { IAutorizaciones } from '../autorizaciones.model';
import { AutorizacionesService } from '../service/autorizaciones.service';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { PozoService } from 'app/entities/pozo/service/pozo.service';
import { ContactoTecnico } from 'app/entities/enumerations/contacto-tecnico.model';

@Component({
  selector: 'jhi-autorizaciones-update',
  templateUrl: './autorizaciones-update.component.html',
})
export class AutorizacionesUpdateComponent implements OnInit {
  isSaving = false;
  autorizaciones: IAutorizaciones | null = null;
  contactoTecnicoValues = Object.keys(ContactoTecnico);

  proveedorsSharedCollection: IProveedor[] = [];
  pozosSharedCollection: IPozo[] = [];

  editForm: AutorizacionesFormGroup = this.autorizacionesFormService.createAutorizacionesFormGroup();

  constructor(
    protected autorizacionesService: AutorizacionesService,
    protected autorizacionesFormService: AutorizacionesFormService,
    protected proveedorService: ProveedorService,
    protected pozoService: PozoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProveedor = (o1: IProveedor | null, o2: IProveedor | null): boolean => this.proveedorService.compareProveedor(o1, o2);

  comparePozo = (o1: IPozo | null, o2: IPozo | null): boolean => this.pozoService.comparePozo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autorizaciones }) => {
      this.autorizaciones = autorizaciones;
      if (autorizaciones) {
        this.updateForm(autorizaciones);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const autorizaciones = this.autorizacionesFormService.getAutorizaciones(this.editForm);
    if (autorizaciones.id !== null) {
      this.subscribeToSaveResponse(this.autorizacionesService.update(autorizaciones));
    } else {
      this.subscribeToSaveResponse(this.autorizacionesService.create(autorizaciones));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutorizaciones>>): void {
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

  protected updateForm(autorizaciones: IAutorizaciones): void {
    this.autorizaciones = autorizaciones;
    this.autorizacionesFormService.resetForm(this.editForm, autorizaciones);

    this.proveedorsSharedCollection = this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(
      this.proveedorsSharedCollection,
      autorizaciones.razonSocial
    );
    this.pozosSharedCollection = this.pozoService.addPozoToCollectionIfMissing<IPozo>(
      this.pozosSharedCollection,
      autorizaciones.numeropozo
    );
  }

  protected loadRelationshipsOptions(): void {
    this.proveedorService
      .query()
      .pipe(map((res: HttpResponse<IProveedor[]>) => res.body ?? []))
      .pipe(
        map((proveedors: IProveedor[]) =>
          this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(proveedors, this.autorizaciones?.razonSocial)
        )
      )
      .subscribe((proveedors: IProveedor[]) => (this.proveedorsSharedCollection = proveedors));

    this.pozoService
      .query()
      .pipe(map((res: HttpResponse<IPozo[]>) => res.body ?? []))
      .pipe(map((pozos: IPozo[]) => this.pozoService.addPozoToCollectionIfMissing<IPozo>(pozos, this.autorizaciones?.numeropozo)))
      .subscribe((pozos: IPozo[]) => (this.pozosSharedCollection = pozos));
  }
}
