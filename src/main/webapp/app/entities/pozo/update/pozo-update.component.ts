import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PozoFormService, PozoFormGroup } from './pozo-form.service';
import { IPozo } from '../pozo.model';
import { PozoService } from '../service/pozo.service';
import { TipoPozo } from 'app/entities/enumerations/tipo-pozo.model';

//import exp from 'constants';
import { Map, marker, popup, tileLayer } from 'leaflet';

@Component({
  selector: 'jhi-pozo-update',
  templateUrl: './pozo-update.component.html',
})
export class PozoUpdateComponent implements OnInit {
  zoom = 13;

  field_longitud = ' ';
  field_latitud = ' ';

  isSaving = false;
  pozo: IPozo | null = null;
  tipoPozoValues = Object.keys(TipoPozo);

  editForm: PozoFormGroup = this.pozoFormService.createPozoFormGroup();

  constructor(protected pozoService: PozoService, protected pozoFormService: PozoFormService, protected activatedRoute: ActivatedRoute) {}

  //Inicio Agregar el mapa e iniciar el mapa
  ngOnInit(): void {
    const map = new Map('map').setView([-4.0041, -79.1983], 13);

    tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
    }).addTo(map);

    const myMarker = marker([-4.0041, -79.1983], { title: 'Mi Ubicación', alt: 'The Big I', draggable: true })
      .addTo(map)
      .on('moveend', event => {
        event.target;
        //const coord = String(myMarker.getLatLng()).split(',');
        const coord = myMarker.getLatLng();

        const longitud = `${String(coord.lng)}`; // lng también es una propiedad de latlng
        const latitud = `${String(coord.lat)}`; // lat  es una propiedad de latlng

        //alert("Acabas de hacer clic en: \n latitud: " + latitud + "\n longitud: " + longitud);

        this.field_longitud = longitud;
        this.field_latitud = latitud;

        popup({
          offset: [0, -30],
        })
          .setLatLng([coord.lat, coord.lng])
          .setContent(`Ubicación Geográfica del Pozo \n  ${String(coord.lng)} \n  ${String(coord.lat)}`)
          .openOn(map);

        //myMarker.bindPopup("Moved to: " + lat[1] + ", " + lng[0] + ".");
      });

    //Fin Agregar el mapa e iniciar el mapa
    this.activatedRoute.data.subscribe(({ pozo }) => {
      this.pozo = pozo;
      if (pozo) {
        this.updateForm(pozo);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pozo = this.pozoFormService.getPozo(this.editForm);
    if (pozo.id !== null) {
      this.subscribeToSaveResponse(this.pozoService.update(pozo));
    } else {
      this.subscribeToSaveResponse(this.pozoService.create(pozo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPozo>>): void {
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

  protected updateForm(pozo: IPozo): void {
    this.pozo = pozo;
    this.pozoFormService.resetForm(this.editForm, pozo);
  }
}
