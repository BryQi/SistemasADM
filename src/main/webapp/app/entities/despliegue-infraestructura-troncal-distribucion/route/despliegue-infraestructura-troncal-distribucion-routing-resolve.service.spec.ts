import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IDespliegueInfraestructuraTroncalDistribucion } from '../despliegue-infraestructura-troncal-distribucion.model';
import { DespliegueInfraestructuraTroncalDistribucionService } from '../service/despliegue-infraestructura-troncal-distribucion.service';

import { DespliegueInfraestructuraTroncalDistribucionRoutingResolveService } from './despliegue-infraestructura-troncal-distribucion-routing-resolve.service';

describe('DespliegueInfraestructuraTroncalDistribucion routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: DespliegueInfraestructuraTroncalDistribucionRoutingResolveService;
  let service: DespliegueInfraestructuraTroncalDistribucionService;
  let resultDespliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(DespliegueInfraestructuraTroncalDistribucionRoutingResolveService);
    service = TestBed.inject(DespliegueInfraestructuraTroncalDistribucionService);
    resultDespliegueInfraestructuraTroncalDistribucion = undefined;
  });

  describe('resolve', () => {
    it('should return IDespliegueInfraestructuraTroncalDistribucion returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDespliegueInfraestructuraTroncalDistribucion = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDespliegueInfraestructuraTroncalDistribucion).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDespliegueInfraestructuraTroncalDistribucion = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDespliegueInfraestructuraTroncalDistribucion).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IDespliegueInfraestructuraTroncalDistribucion>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultDespliegueInfraestructuraTroncalDistribucion = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultDespliegueInfraestructuraTroncalDistribucion).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
