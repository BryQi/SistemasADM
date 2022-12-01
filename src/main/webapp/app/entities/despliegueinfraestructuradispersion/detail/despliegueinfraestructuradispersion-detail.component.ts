import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDespliegueinfraestructuradispersion } from '../despliegueinfraestructuradispersion.model';

@Component({
  selector: 'jhi-despliegueinfraestructuradispersion-detail',
  templateUrl: './despliegueinfraestructuradispersion-detail.component.html',
})
export class DespliegueinfraestructuradispersionDetailComponent implements OnInit {
  despliegueinfraestructuradispersion: IDespliegueinfraestructuradispersion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ despliegueinfraestructuradispersion }) => {
      this.despliegueinfraestructuradispersion = despliegueinfraestructuradispersion;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
