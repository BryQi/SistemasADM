import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInfraestructura } from '../infraestructura.model';

@Component({
  selector: 'jhi-infraestructura-detail',
  templateUrl: './infraestructura-detail.component.html',
})
export class InfraestructuraDetailComponent implements OnInit {
  infraestructura: IInfraestructura | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ infraestructura }) => {
      this.infraestructura = infraestructura;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
