import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAutorizaciones } from '../autorizaciones.model';

@Component({
  selector: 'jhi-autorizaciones-detail',
  templateUrl: './autorizaciones-detail.component.html',
})
export class AutorizacionesDetailComponent implements OnInit {
  autorizaciones: IAutorizaciones | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autorizaciones }) => {
      this.autorizaciones = autorizaciones;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
