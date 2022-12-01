import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DespliegueinfraestructuradispersionService } from '../service/despliegueinfraestructuradispersion.service';

import { DespliegueinfraestructuradispersionComponent } from './despliegueinfraestructuradispersion.component';
import SpyInstance = jest.SpyInstance;

describe('Despliegueinfraestructuradispersion Management Component', () => {
  let comp: DespliegueinfraestructuradispersionComponent;
  let fixture: ComponentFixture<DespliegueinfraestructuradispersionComponent>;
  let service: DespliegueinfraestructuradispersionService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([
          { path: 'despliegueinfraestructuradispersion', component: DespliegueinfraestructuradispersionComponent },
        ]),
        HttpClientTestingModule,
      ],
      declarations: [DespliegueinfraestructuradispersionComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(DespliegueinfraestructuradispersionComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DespliegueinfraestructuradispersionComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DespliegueinfraestructuradispersionService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.despliegueinfraestructuradispersions?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to despliegueinfraestructuradispersionService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getDespliegueinfraestructuradispersionIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getDespliegueinfraestructuradispersionIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });

  it('should load a page', () => {
    // WHEN
    comp.navigateToPage(1);

    // THEN
    expect(routerNavigateSpy).toHaveBeenCalled();
  });

  it('should calculate the sort attribute for an id', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['id,desc'] }));
  });

  it('should calculate the sort attribute for a non-id attribute', () => {
    // GIVEN
    comp.predicate = 'name';

    // WHEN
    comp.navigateToWithComponentValues();

    // THEN
    expect(routerNavigateSpy).toHaveBeenLastCalledWith(
      expect.anything(),
      expect.objectContaining({
        queryParams: expect.objectContaining({
          sort: ['name,asc'],
        }),
      })
    );
  });
});
