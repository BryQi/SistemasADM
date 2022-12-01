import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDespliegueinfraestructuradispersion } from '../despliegueinfraestructuradispersion.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../despliegueinfraestructuradispersion.test-samples';

import {
  DespliegueinfraestructuradispersionService,
  RestDespliegueinfraestructuradispersion,
} from './despliegueinfraestructuradispersion.service';

const requireRestSample: RestDespliegueinfraestructuradispersion = {
  ...sampleWithRequiredData,
  fechaInstalacion: sampleWithRequiredData.fechaInstalacion?.format(DATE_FORMAT),
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
};

describe('Despliegueinfraestructuradispersion Service', () => {
  let service: DespliegueinfraestructuradispersionService;
  let httpMock: HttpTestingController;
  let expectedResult: IDespliegueinfraestructuradispersion | IDespliegueinfraestructuradispersion[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DespliegueinfraestructuradispersionService);
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

    it('should create a Despliegueinfraestructuradispersion', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const despliegueinfraestructuradispersion = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(despliegueinfraestructuradispersion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Despliegueinfraestructuradispersion', () => {
      const despliegueinfraestructuradispersion = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(despliegueinfraestructuradispersion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Despliegueinfraestructuradispersion', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Despliegueinfraestructuradispersion', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Despliegueinfraestructuradispersion', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDespliegueinfraestructuradispersionToCollectionIfMissing', () => {
      it('should add a Despliegueinfraestructuradispersion to an empty array', () => {
        const despliegueinfraestructuradispersion: IDespliegueinfraestructuradispersion = sampleWithRequiredData;
        expectedResult = service.addDespliegueinfraestructuradispersionToCollectionIfMissing([], despliegueinfraestructuradispersion);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(despliegueinfraestructuradispersion);
      });

      it('should not add a Despliegueinfraestructuradispersion to an array that contains it', () => {
        const despliegueinfraestructuradispersion: IDespliegueinfraestructuradispersion = sampleWithRequiredData;
        const despliegueinfraestructuradispersionCollection: IDespliegueinfraestructuradispersion[] = [
          {
            ...despliegueinfraestructuradispersion,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDespliegueinfraestructuradispersionToCollectionIfMissing(
          despliegueinfraestructuradispersionCollection,
          despliegueinfraestructuradispersion
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Despliegueinfraestructuradispersion to an array that doesn't contain it", () => {
        const despliegueinfraestructuradispersion: IDespliegueinfraestructuradispersion = sampleWithRequiredData;
        const despliegueinfraestructuradispersionCollection: IDespliegueinfraestructuradispersion[] = [sampleWithPartialData];
        expectedResult = service.addDespliegueinfraestructuradispersionToCollectionIfMissing(
          despliegueinfraestructuradispersionCollection,
          despliegueinfraestructuradispersion
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(despliegueinfraestructuradispersion);
      });

      it('should add only unique Despliegueinfraestructuradispersion to an array', () => {
        const despliegueinfraestructuradispersionArray: IDespliegueinfraestructuradispersion[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const despliegueinfraestructuradispersionCollection: IDespliegueinfraestructuradispersion[] = [sampleWithRequiredData];
        expectedResult = service.addDespliegueinfraestructuradispersionToCollectionIfMissing(
          despliegueinfraestructuradispersionCollection,
          ...despliegueinfraestructuradispersionArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const despliegueinfraestructuradispersion: IDespliegueinfraestructuradispersion = sampleWithRequiredData;
        const despliegueinfraestructuradispersion2: IDespliegueinfraestructuradispersion = sampleWithPartialData;
        expectedResult = service.addDespliegueinfraestructuradispersionToCollectionIfMissing(
          [],
          despliegueinfraestructuradispersion,
          despliegueinfraestructuradispersion2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(despliegueinfraestructuradispersion);
        expect(expectedResult).toContain(despliegueinfraestructuradispersion2);
      });

      it('should accept null and undefined values', () => {
        const despliegueinfraestructuradispersion: IDespliegueinfraestructuradispersion = sampleWithRequiredData;
        expectedResult = service.addDespliegueinfraestructuradispersionToCollectionIfMissing(
          [],
          null,
          despliegueinfraestructuradispersion,
          undefined
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(despliegueinfraestructuradispersion);
      });

      it('should return initial array if no Despliegueinfraestructuradispersion is added', () => {
        const despliegueinfraestructuradispersionCollection: IDespliegueinfraestructuradispersion[] = [sampleWithRequiredData];
        expectedResult = service.addDespliegueinfraestructuradispersionToCollectionIfMissing(
          despliegueinfraestructuradispersionCollection,
          undefined,
          null
        );
        expect(expectedResult).toEqual(despliegueinfraestructuradispersionCollection);
      });
    });

    describe('compareDespliegueinfraestructuradispersion', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDespliegueinfraestructuradispersion(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDespliegueinfraestructuradispersion(entity1, entity2);
        const compareResult2 = service.compareDespliegueinfraestructuradispersion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDespliegueinfraestructuradispersion(entity1, entity2);
        const compareResult2 = service.compareDespliegueinfraestructuradispersion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDespliegueinfraestructuradispersion(entity1, entity2);
        const compareResult2 = service.compareDespliegueinfraestructuradispersion(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
