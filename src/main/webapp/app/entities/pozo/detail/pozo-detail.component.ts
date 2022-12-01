import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPozo } from '../pozo.model';

@Component({
  selector: 'jhi-pozo-detail',
  templateUrl: './pozo-detail.component.html',
})
export class PozoDetailComponent implements OnInit {
  pozo: IPozo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pozo }) => {
      this.pozo = pozo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
