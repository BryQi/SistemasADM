import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import {
  DespliegueinfraestructuradispersionFormService,
  DespliegueinfraestructuradispersionFormGroup,
} from './despliegueinfraestructuradispersion-form.service';
import { IDespliegueinfraestructuradispersion } from '../despliegueinfraestructuradispersion.model';
import { DespliegueinfraestructuradispersionService } from '../service/despliegueinfraestructuradispersion.service';
import { IPozo } from 'app/entities/pozo/pozo.model';
import { PozoService } from 'app/entities/pozo/service/pozo.service';
import { IDespliegueInfraestructuraTroncalDistribucion } from 'app/entities/despliegue-infraestructura-troncal-distribucion/despliegue-infraestructura-troncal-distribucion.model';
import { DespliegueInfraestructuraTroncalDistribucionService } from 'app/entities/despliegue-infraestructura-troncal-distribucion/service/despliegue-infraestructura-troncal-distribucion.service';
import { IProveedor } from 'app/entities/proveedor/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor/service/proveedor.service';
import { Origen } from 'app/entities/enumerations/origen.model';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

/* de aquí */

/* hasta acá */

@Component({
  selector: 'jhi-despliegueinfraestructuradispersion-update',
  templateUrl: './despliegueinfraestructuradispersion-update.component.html',
})
export class DespliegueinfraestructuradispersionUpdateComponent implements OnInit {
  isSaving = false;
  form_val!: FormGroup;

  despliegueinfraestructuradispersion: IDespliegueinfraestructuradispersion | null = null;
  origenValues = Object.keys(Origen);

  pozosSharedCollection: IPozo[] = [];
  despliegueInfraestructuraTroncalDistribucionsSharedCollection: IDespliegueInfraestructuraTroncalDistribucion[] = [];
  proveedorsSharedCollection: IProveedor[] = [];

  editForm: DespliegueinfraestructuradispersionFormGroup =
    this.despliegueinfraestructuradispersionFormService.createDespliegueinfraestructuradispersionFormGroup();

  constructor(
    protected despliegueinfraestructuradispersionService: DespliegueinfraestructuradispersionService,
    protected despliegueinfraestructuradispersionFormService: DespliegueinfraestructuradispersionFormService,
    protected pozoService: PozoService,
    protected despliegueInfraestructuraTroncalDistribucionService: DespliegueInfraestructuraTroncalDistribucionService,
    protected proveedorService: ProveedorService,
    protected activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {}

  comparePozo = (o1: IPozo | null, o2: IPozo | null): boolean => this.pozoService.comparePozo(o1, o2);

  compareDespliegueInfraestructuraTroncalDistribucion = (
    o1: IDespliegueInfraestructuraTroncalDistribucion | null,
    o2: IDespliegueInfraestructuraTroncalDistribucion | null
  ): boolean => this.despliegueInfraestructuraTroncalDistribucionService.compareDespliegueInfraestructuraTroncalDistribucion(o1, o2);

  compareProveedor = (o1: IProveedor | null, o2: IProveedor | null): boolean => this.proveedorService.compareProveedor(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ despliegueinfraestructuradispersion }) => {
      this.despliegueinfraestructuradispersion = despliegueinfraestructuradispersion;
      if (despliegueinfraestructuradispersion) {
        this.updateForm(despliegueinfraestructuradispersion);
      }

      this.loadRelationshipsOptions();
    });
    this.form_val = this.formBuilder.group({
      metrajeInicial: new FormControl(0),
      metrajeFinal: new FormControl(0),
      valorMetro: new FormControl(0),
      calculoValorPago: new FormControl(0),
    });
  }

  calcvalor(event: any) {
    console.log(event.target.value);
    let metrajei = this.editForm.controls['metrajeInicial'].value || 0;
    let metrajef = this.editForm.controls['metrajeFinal'].value || 0;
    let valormet = this.editForm.controls['valorMetro'].value || 0;

    let result = (metrajef - metrajei) * valormet;

    this.editForm.controls['calculoValorPago'].setValue(parseFloat(result.toFixed(2)));
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const despliegueinfraestructuradispersion = this.despliegueinfraestructuradispersionFormService.getDespliegueinfraestructuradispersion(
      this.editForm
    );
    if (despliegueinfraestructuradispersion.id !== null) {
      this.subscribeToSaveResponse(this.despliegueinfraestructuradispersionService.update(despliegueinfraestructuradispersion));
    } else {
      this.subscribeToSaveResponse(this.despliegueinfraestructuradispersionService.create(despliegueinfraestructuradispersion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDespliegueinfraestructuradispersion>>): void {
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

  protected updateForm(despliegueinfraestructuradispersion: IDespliegueinfraestructuradispersion): void {
    this.despliegueinfraestructuradispersion = despliegueinfraestructuradispersion;
    this.despliegueinfraestructuradispersionFormService.resetForm(this.editForm, despliegueinfraestructuradispersion);

    this.pozosSharedCollection = this.pozoService.addPozoToCollectionIfMissing<IPozo>(
      this.pozosSharedCollection,
      ...(despliegueinfraestructuradispersion.pozos ?? [])
    );
    this.despliegueInfraestructuraTroncalDistribucionsSharedCollection =
      this.despliegueInfraestructuraTroncalDistribucionService.addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing<IDespliegueInfraestructuraTroncalDistribucion>(
        this.despliegueInfraestructuraTroncalDistribucionsSharedCollection,
        despliegueinfraestructuradispersion.idDespliegueInfraestructuraTroncalDistribucion
      );
    this.proveedorsSharedCollection = this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(
      this.proveedorsSharedCollection,
      despliegueinfraestructuradispersion.idProveedor
    );
  }

  protected loadRelationshipsOptions(): void {
    this.pozoService
      .query()
      .pipe(map((res: HttpResponse<IPozo[]>) => res.body ?? []))
      .pipe(
        map((pozos: IPozo[]) =>
          this.pozoService.addPozoToCollectionIfMissing<IPozo>(pozos, ...(this.despliegueinfraestructuradispersion?.pozos ?? []))
        )
      )
      .subscribe((pozos: IPozo[]) => (this.pozosSharedCollection = pozos));

    this.despliegueInfraestructuraTroncalDistribucionService
      .query()
      .pipe(map((res: HttpResponse<IDespliegueInfraestructuraTroncalDistribucion[]>) => res.body ?? []))
      .pipe(
        map((despliegueInfraestructuraTroncalDistribucions: IDespliegueInfraestructuraTroncalDistribucion[]) =>
          this.despliegueInfraestructuraTroncalDistribucionService.addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing<IDespliegueInfraestructuraTroncalDistribucion>(
            despliegueInfraestructuraTroncalDistribucions,
            this.despliegueinfraestructuradispersion?.idDespliegueInfraestructuraTroncalDistribucion
          )
        )
      )
      .subscribe(
        (despliegueInfraestructuraTroncalDistribucions: IDespliegueInfraestructuraTroncalDistribucion[]) =>
          (this.despliegueInfraestructuraTroncalDistribucionsSharedCollection = despliegueInfraestructuraTroncalDistribucions)
      );

    this.proveedorService
      .query()
      .pipe(map((res: HttpResponse<IProveedor[]>) => res.body ?? []))
      .pipe(
        map((proveedors: IProveedor[]) =>
          this.proveedorService.addProveedorToCollectionIfMissing<IProveedor>(
            proveedors,
            this.despliegueinfraestructuradispersion?.idProveedor
          )
        )
      )
      .subscribe((proveedors: IProveedor[]) => (this.proveedorsSharedCollection = proveedors));
  }
}
