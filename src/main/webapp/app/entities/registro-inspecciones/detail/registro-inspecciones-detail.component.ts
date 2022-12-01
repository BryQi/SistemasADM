import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegistroInspecciones } from '../registro-inspecciones.model';

@Component({
  selector: 'jhi-registro-inspecciones-detail',
  templateUrl: './registro-inspecciones-detail.component.html',
})
export class RegistroInspeccionesDetailComponent implements OnInit {
  registroInspecciones: IRegistroInspecciones | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ registroInspecciones }) => {
      this.registroInspecciones = registroInspecciones;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
