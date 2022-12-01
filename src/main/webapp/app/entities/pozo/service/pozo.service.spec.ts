import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPozo } from '../pozo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../pozo.test-samples';

import { PozoService, RestPozo } from './pozo.service';

const requireRestSample: RestPozo = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
};

describe('Pozo Service', () => {
  let service: PozoService;
  let httpMock: HttpTestingController;
  let expectedResult: IPozo | IPozo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PozoService);
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

    it('should create a Pozo', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const pozo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pozo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Pozo', () => {
      const pozo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pozo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Pozo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Pozo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Pozo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPozoToCollectionIfMissing', () => {
      it('should add a Pozo to an empty array', () => {
        const pozo: IPozo = sampleWithRequiredData;
        expectedResult = service.addPozoToCollectionIfMissing([], pozo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pozo);
      });

      it('should not add a Pozo to an array that contains it', () => {
        const pozo: IPozo = sampleWithRequiredData;
        const pozoCollection: IPozo[] = [
          {
            ...pozo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPozoToCollectionIfMissing(pozoCollection, pozo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Pozo to an array that doesn't contain it", () => {
        const pozo: IPozo = sampleWithRequiredData;
        const pozoCollection: IPozo[] = [sampleWithPartialData];
        expectedResult = service.addPozoToCollectionIfMissing(pozoCollection, pozo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pozo);
      });

      it('should add only unique Pozo to an array', () => {
        const pozoArray: IPozo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pozoCollection: IPozo[] = [sampleWithRequiredData];
        expectedResult = service.addPozoToCollectionIfMissing(pozoCollection, ...pozoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pozo: IPozo = sampleWithRequiredData;
        const pozo2: IPozo = sampleWithPartialData;
        expectedResult = service.addPozoToCollectionIfMissing([], pozo, pozo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pozo);
        expect(expectedResult).toContain(pozo2);
      });

      it('should accept null and undefined values', () => {
        const pozo: IPozo = sampleWithRequiredData;
        expectedResult = service.addPozoToCollectionIfMissing([], null, pozo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pozo);
      });

      it('should return initial array if no Pozo is added', () => {
        const pozoCollection: IPozo[] = [sampleWithRequiredData];
        expectedResult = service.addPozoToCollectionIfMissing(pozoCollection, undefined, null);
        expect(expectedResult).toEqual(pozoCollection);
      });
    });

    describe('comparePozo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePozo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePozo(entity1, entity2);
        const compareResult2 = service.comparePozo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePozo(entity1, entity2);
        const compareResult2 = service.comparePozo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePozo(entity1, entity2);
        const compareResult2 = service.comparePozo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
