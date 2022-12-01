import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDespliegueInfraestructuraTroncalDistribucion } from '../despliegue-infraestructura-troncal-distribucion.model';

@Component({
  selector: 'jhi-despliegue-infraestructura-troncal-distribucion-detail',
  templateUrl: './despliegue-infraestructura-troncal-distribucion-detail.component.html',
})
export class DespliegueInfraestructuraTroncalDistribucionDetailComponent implements OnInit {
  despliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ despliegueInfraestructuraTroncalDistribucion }) => {
      this.despliegueInfraestructuraTroncalDistribucion = despliegueInfraestructuraTroncalDistribucion;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
