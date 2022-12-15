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
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';

import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'jhi-despliegue-infraestructura-troncal-distribucion-update',
  templateUrl: './despliegue-infraestructura-troncal-distribucion-update.component.html',
})
export class DespliegueInfraestructuraTroncalDistribucionUpdateComponent implements OnInit {
  isSaving = false;
  despliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion | null = null;

  pozosSharedCollection: IPozo[] = [];
  proveedorsSharedCollection: IProveedor[] = [];

  editForm: DespliegueInfraestructuraTroncalDistribucionFormGroup =
    this.despliegueInfraestructuraTroncalDistribucionFormService.createDespliegueInfraestructuraTroncalDistribucionFormGroup();
  form_val: any;
  formBuilder: any;

  constructor(
    protected despliegueInfraestructuraTroncalDistribucionService: DespliegueInfraestructuraTroncalDistribucionService,
    protected despliegueInfraestructuraTroncalDistribucionFormService: DespliegueInfraestructuraTroncalDistribucionFormService,
    protected pozoService: PozoService,
    protected proveedorService: ProveedorService,
    protected activatedRoute: ActivatedRoute
  ) {}

  comparePozo = (o1: IPozo | null, o2: IPozo | null): boolean => this.pozoService.comparePozo(o1, o2);

  compareProveedor = (o1: IProveedor | null, o2: IProveedor | null): boolean => this.proveedorService.compareProveedor(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ despliegueInfraestructuraTroncalDistribucion }) => {
      this.despliegueInfraestructuraTroncalDistribucion = despliegueInfraestructuraTroncalDistribucion;
      if (despliegueInfraestructuraTroncalDistribucion) {
        this.updateForm(despliegueInfraestructuraTroncalDistribucion);
      }

      this.loadRelationshipsOptions();
    });

    /* de aquí */
    this.form_val = this.formBuilder.group({
      metrajeInicial: new FormControl(0),
      metrajeFinal: new FormControl(0),
      valorMetro: new FormControl(0),
      calculoValorPago: new FormControl(0),
    });
    /* hasta acá */
  }
  /* de aquí */
  calcvalor(event: any) {
    console.log(event.target.value);
    let metrajei = this.editForm.controls['metrajeInicial'].value || 0;
    let metrajef = this.editForm.controls['metrajeFinal'].value || 0;
    let valormet = this.editForm.controls['valorMetro'].value || 0;

    const result = (metrajef - metrajei) * valormet;

    if (result < 0) {
      const result2 = result * -1;
      this.editForm.controls['calculoValorPago'].setValue(parseFloat(result2.toFixed(2)));
    } else {
      this.editForm.controls['calculoValorPago'].setValue(parseFloat(result.toFixed(2)));
    }
  }
  /* hasta acá */

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
    this.proveedorsSharedCollection = this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(
      this.proveedorsSharedCollection,
      despliegueInfraestructuraTroncalDistribucion.razonSocial
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

    this.proveedorService
      .query()
      .pipe(map((res: HttpResponse<IProveedor[]>) => res.body ?? []))
      .pipe(
        map((proveedors: IProveedor[]) =>
          this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(
            proveedors,
            this.despliegueInfraestructuraTroncalDistribucion?.razonSocial
          )
        )
      )
      .subscribe((proveedors: IProveedor[]) => (this.proveedorsSharedCollection = proveedors));
  }
}
