import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IInfraestructura } from '../infraestructura.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../infraestructura.test-samples';

import { InfraestructuraService, RestInfraestructura } from './infraestructura.service';

const requireRestSample: RestInfraestructura = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
};

describe('Infraestructura Service', () => {
  let service: InfraestructuraService;
  let httpMock: HttpTestingController;
  let expectedResult: IInfraestructura | IInfraestructura[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InfraestructuraService);
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

    it('should create a Infraestructura', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const infraestructura = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(infraestructura).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Infraestructura', () => {
      const infraestructura = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(infraestructura).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Infraestructura', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Infraestructura', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Infraestructura', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addInfraestructuraToCollectionIfMissing', () => {
      it('should add a Infraestructura to an empty array', () => {
        const infraestructura: IInfraestructura = sampleWithRequiredData;
        expectedResult = service.addInfraestructuraToCollectionIfMissing([], infraestructura);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(infraestructura);
      });

      it('should not add a Infraestructura to an array that contains it', () => {
        const infraestructura: IInfraestructura = sampleWithRequiredData;
        const infraestructuraCollection: IInfraestructura[] = [
          {
            ...infraestructura,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInfraestructuraToCollectionIfMissing(infraestructuraCollection, infraestructura);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Infraestructura to an array that doesn't contain it", () => {
        const infraestructura: IInfraestructura = sampleWithRequiredData;
        const infraestructuraCollection: IInfraestructura[] = [sampleWithPartialData];
        expectedResult = service.addInfraestructuraToCollectionIfMissing(infraestructuraCollection, infraestructura);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(infraestructura);
      });

      it('should add only unique Infraestructura to an array', () => {
        const infraestructuraArray: IInfraestructura[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const infraestructuraCollection: IInfraestructura[] = [sampleWithRequiredData];
        expectedResult = service.addInfraestructuraToCollectionIfMissing(infraestructuraCollection, ...infraestructuraArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const infraestructura: IInfraestructura = sampleWithRequiredData;
        const infraestructura2: IInfraestructura = sampleWithPartialData;
        expectedResult = service.addInfraestructuraToCollectionIfMissing([], infraestructura, infraestructura2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(infraestructura);
        expect(expectedResult).toContain(infraestructura2);
      });

      it('should accept null and undefined values', () => {
        const infraestructura: IInfraestructura = sampleWithRequiredData;
        expectedResult = service.addInfraestructuraToCollectionIfMissing([], null, infraestructura, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(infraestructura);
      });

      it('should return initial array if no Infraestructura is added', () => {
        const infraestructuraCollection: IInfraestructura[] = [sampleWithRequiredData];
        expectedResult = service.addInfraestructuraToCollectionIfMissing(infraestructuraCollection, undefined, null);
        expect(expectedResult).toEqual(infraestructuraCollection);
      });
    });

    describe('compareInfraestructura', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInfraestructura(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareInfraestructura(entity1, entity2);
        const compareResult2 = service.compareInfraestructura(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareInfraestructura(entity1, entity2);
        const compareResult2 = service.compareInfraestructura(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareInfraestructura(entity1, entity2);
        const compareResult2 = service.compareInfraestructura(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
