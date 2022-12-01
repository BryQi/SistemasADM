import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AutorizacionesDetailComponent } from './autorizaciones-detail.component';

describe('Autorizaciones Management Detail Component', () => {
  let comp: AutorizacionesDetailComponent;
  let fixture: ComponentFixture<AutorizacionesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AutorizacionesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ autorizaciones: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AutorizacionesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AutorizacionesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load autorizaciones on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.autorizaciones).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
