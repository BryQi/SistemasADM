import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDespliegueInfraestructuraTroncalDistribucion } from '../despliegue-infraestructura-troncal-distribucion.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../despliegue-infraestructura-troncal-distribucion.test-samples';

import {
  DespliegueInfraestructuraTroncalDistribucionService,
  RestDespliegueInfraestructuraTroncalDistribucion,
} from './despliegue-infraestructura-troncal-distribucion.service';

const requireRestSample: RestDespliegueInfraestructuraTroncalDistribucion = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
};

describe('DespliegueInfraestructuraTroncalDistribucion Service', () => {
  let service: DespliegueInfraestructuraTroncalDistribucionService;
  let httpMock: HttpTestingController;
  let expectedResult: IDespliegueInfraestructuraTroncalDistribucion | IDespliegueInfraestructuraTroncalDistribucion[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DespliegueInfraestructuraTroncalDistribucionService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a DespliegueInfraestructuraTroncalDistribucion', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const despliegueInfraestructuraTroncalDistribucion = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(despliegueInfraestructuraTroncalDistribucion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DespliegueInfraestructuraTroncalDistribucion', () => {
      const despliegueInfraestructuraTroncalDistribucion = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(despliegueInfraestructuraTroncalDistribucion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DespliegueInfraestructuraTroncalDistribucion', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DespliegueInfraestructuraTroncalDistribucion', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DespliegueInfraestructuraTroncalDistribucion', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing', () => {
      it('should add a DespliegueInfraestructuraTroncalDistribucion to an empty array', () => {
        const despliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion = sampleWithRequiredData;
        expectedResult = service.addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing(
          [],
          despliegueInfraestructuraTroncalDistribucion
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(despliegueInfraestructuraTroncalDistribucion);
      });

      it('should not add a DespliegueInfraestructuraTroncalDistribucion to an array that contains it', () => {
        const despliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion = sampleWithRequiredData;
        const despliegueInfraestructuraTroncalDistribucionCollection: IDespliegueInfraestructuraTroncalDistribucion[] = [
          {
            ...despliegueInfraestructuraTroncalDistribucion,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing(
          despliegueInfraestructuraTroncalDistribucionCollection,
          despliegueInfraestructuraTroncalDistribucion
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DespliegueInfraestructuraTroncalDistribucion to an array that doesn't contain it", () => {
        const despliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion = sampleWithRequiredData;
        const despliegueInfraestructuraTroncalDistribucionCollection: IDespliegueInfraestructuraTroncalDistribucion[] = [
          sampleWithPartialData,
        ];
        expectedResult = service.addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing(
          despliegueInfraestructuraTroncalDistribucionCollection,
          despliegueInfraestructuraTroncalDistribucion
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(despliegueInfraestructuraTroncalDistribucion);
      });

      it('should add only unique DespliegueInfraestructuraTroncalDistribucion to an array', () => {
        const despliegueInfraestructuraTroncalDistribucionArray: IDespliegueInfraestructuraTroncalDistribucion[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const despliegueInfraestructuraTroncalDistribucionCollection: IDespliegueInfraestructuraTroncalDistribucion[] = [
          sampleWithRequiredData,
        ];
        expectedResult = service.addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing(
          despliegueInfraestructuraTroncalDistribucionCollection,
          ...despliegueInfraestructuraTroncalDistribucionArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const despliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion = sampleWithRequiredData;
        const despliegueInfraestructuraTroncalDistribucion2: IDespliegueInfraestructuraTroncalDistribucion = sampleWithPartialData;
        expectedResult = service.addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing(
          [],
          despliegueInfraestructuraTroncalDistribucion,
          despliegueInfraestructuraTroncalDistribucion2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(despliegueInfraestructuraTroncalDistribucion);
        expect(expectedResult).toContain(despliegueInfraestructuraTroncalDistribucion2);
      });

      it('should accept null and undefined values', () => {
        const despliegueInfraestructuraTroncalDistribucion: IDespliegueInfraestructuraTroncalDistribucion = sampleWithRequiredData;
        expectedResult = service.addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing(
          [],
          null,
          despliegueInfraestructuraTroncalDistribucion,
          undefined
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(despliegueInfraestructuraTroncalDistribucion);
      });

      it('should return initial array if no DespliegueInfraestructuraTroncalDistribucion is added', () => {
        const despliegueInfraestructuraTroncalDistribucionCollection: IDespliegueInfraestructuraTroncalDistribucion[] = [
          sampleWithRequiredData,
        ];
        expectedResult = service.addDespliegueInfraestructuraTroncalDistribucionToCollectionIfMissing(
          despliegueInfraestructuraTroncalDistribucionCollection,
          undefined,
          null
        );
        expect(expectedResult).toEqual(despliegueInfraestructuraTroncalDistribucionCollection);
      });
    });

    describe('compareDespliegueInfraestructuraTroncalDistribucion', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDespliegueInfraestructuraTroncalDistribucion(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDespliegueInfraestructuraTroncalDistribucion(entity1, entity2);
        const compareResult2 = service.compareDespliegueInfraestructuraTroncalDistribucion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDespliegueInfraestructuraTroncalDistribucion(entity1, entity2);
        const compareResult2 = service.compareDespliegueInfraestructuraTroncalDistribucion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDespliegueInfraestructuraTroncalDistribucion(entity1, entity2);
        const compareResult2 = service.compareDespliegueInfraestructuraTroncalDistribucion(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
