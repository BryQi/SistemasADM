import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AutorizacionesFormService, AutorizacionesFormGroup } from './autorizaciones-form.service';
import { IAutorizaciones } from '../autorizaciones.model';
import { AutorizacionesService } from '../service/autorizaciones.service';
import { IRegistroInspecciones } from 'app/entities/registro-inspecciones/registro-inspecciones.model';
import { RegistroInspeccionesService } from 'app/entities/registro-inspecciones/service/registro-inspecciones.service';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';
import { ContactoTecnico } from 'app/entities/enumerations/contacto-tecnico.model';

@Component({
  selector: 'jhi-autorizaciones-update',
  templateUrl: './autorizaciones-update.component.html',
})
export class AutorizacionesUpdateComponent implements OnInit {
  isSaving = false;
  autorizaciones: IAutorizaciones | null = null;
  contactoTecnicoValues = Object.keys(ContactoTecnico);

  registroInspeccionesSharedCollection: IRegistroInspecciones[] = [];
  proveedorsSharedCollection: IProveedor[] = [];

  editForm: AutorizacionesFormGroup = this.autorizacionesFormService.createAutorizacionesFormGroup();

  constructor(
    protected autorizacionesService: AutorizacionesService,
    protected autorizacionesFormService: AutorizacionesFormService,
    protected registroInspeccionesService: RegistroInspeccionesService,
    protected proveedorService: ProveedorService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareRegistroInspecciones = (o1: IRegistroInspecciones | null, o2: IRegistroInspecciones | null): boolean =>
    this.registroInspeccionesService.compareRegistroInspecciones(o1, o2);

  compareProveedor = (o1: IProveedor | null, o2: IProveedor | null): boolean => this.proveedorService.compareProveedor(o1, o2);

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

    this.registroInspeccionesSharedCollection =
      this.registroInspeccionesService.addRegistroInspeccionesToCollectionIfMissing<IRegistroInspecciones>(
        this.registroInspeccionesSharedCollection,
        autorizaciones.registroInspecciones
      );
    this.proveedorsSharedCollection = this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(
      this.proveedorsSharedCollection,
      autorizaciones.idProveedor
    );
  }

  protected loadRelationshipsOptions(): void {
    this.registroInspeccionesService
      .query()
      .pipe(map((res: HttpResponse<IRegistroInspecciones[]>) => res.body ?? []))
      .pipe(
        map((registroInspecciones: IRegistroInspecciones[]) =>
          this.registroInspeccionesService.addRegistroInspeccionesToCollectionIfMissing<IRegistroInspecciones>(
            registroInspecciones,
            this.autorizaciones?.registroInspecciones
          )
        )
      )
      .subscribe((registroInspecciones: IRegistroInspecciones[]) => (this.registroInspeccionesSharedCollection = registroInspecciones));

    this.proveedorService
      .query()
      .pipe(map((res: HttpResponse<IProveedor[]>) => res.body ?? []))
      .pipe(
        map((proveedors: IProveedor[]) =>
          this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(proveedors, this.autorizaciones?.idProveedor)
        )
      )
      .subscribe((proveedors: IProveedor[]) => (this.proveedorsSharedCollection = proveedors));
  }
}
