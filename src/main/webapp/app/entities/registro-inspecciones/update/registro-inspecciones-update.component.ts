import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { RegistroInspeccionesFormService, RegistroInspeccionesFormGroup } from './registro-inspecciones-form.service';
import { IRegistroInspecciones } from '../registro-inspecciones.model';
import { RegistroInspeccionesService } from '../service/registro-inspecciones.service';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { PozoService } from 'app/entities/pozo/service/pozo.service';

@Component({
  selector: 'jhi-registro-inspecciones-update',
  templateUrl: './registro-inspecciones-update.component.html',
})
export class RegistroInspeccionesUpdateComponent implements OnInit {
  isSaving = false;
  registroInspecciones: IRegistroInspecciones | null = null;

  proveedorsSharedCollection: IProveedor[] = [];
  pozosSharedCollection: IPozo[] = [];

  editForm: RegistroInspeccionesFormGroup = this.registroInspeccionesFormService.createRegistroInspeccionesFormGroup();

  constructor(
    protected registroInspeccionesService: RegistroInspeccionesService,
    protected registroInspeccionesFormService: RegistroInspeccionesFormService,
    protected proveedorService: ProveedorService,
    protected pozoService: PozoService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProveedor = (o1: IProveedor | null, o2: IProveedor | null): boolean => this.proveedorService.compareProveedor(o1, o2);

  comparePozo = (o1: IPozo | null, o2: IPozo | null): boolean => this.pozoService.comparePozo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ registroInspecciones }) => {
      this.registroInspecciones = registroInspecciones;
      if (registroInspecciones) {
        this.updateForm(registroInspecciones);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const registroInspecciones = this.registroInspeccionesFormService.getRegistroInspecciones(this.editForm);
    if (registroInspecciones.id !== null) {
      this.subscribeToSaveResponse(this.registroInspeccionesService.update(registroInspecciones));
    } else {
      this.subscribeToSaveResponse(this.registroInspeccionesService.create(registroInspecciones));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegistroInspecciones>>): void {
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

  protected updateForm(registroInspecciones: IRegistroInspecciones): void {
    this.registroInspecciones = registroInspecciones;
    this.registroInspeccionesFormService.resetForm(this.editForm, registroInspecciones);

    this.proveedorsSharedCollection = this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(
      this.proveedorsSharedCollection,
      registroInspecciones.razonSocial
    );
    this.pozosSharedCollection = this.pozoService.addPozoToCollectionIfMissing<IPozo>(
      this.pozosSharedCollection,
      registroInspecciones.numeropozo
    );
  }

  protected loadRelationshipsOptions(): void {
    this.proveedorService
      .query()
      .pipe(map((res: HttpResponse<IProveedor[]>) => res.body ?? []))
      .pipe(
        map((proveedors: IProveedor[]) =>
          this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(proveedors, this.registroInspecciones?.razonSocial)
        )
      )
      .subscribe((proveedors: IProveedor[]) => (this.proveedorsSharedCollection = proveedors));

    this.pozoService
      .query()
      .pipe(map((res: HttpResponse<IPozo[]>) => res.body ?? []))
      .pipe(map((pozos: IPozo[]) => this.pozoService.addPozoToCollectionIfMissing<IPozo>(pozos, this.registroInspecciones?.numeropozo)))
      .subscribe((pozos: IPozo[]) => (this.pozosSharedCollection = pozos));
  }
}
