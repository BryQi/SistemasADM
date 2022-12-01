import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFotoPozo } from '../foto-pozo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../foto-pozo.test-samples';

import { FotoPozoService, RestFotoPozo } from './foto-pozo.service';

const requireRestSample: RestFotoPozo = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
};

describe('FotoPozo Service', () => {
  let service: FotoPozoService;
  let httpMock: HttpTestingController;
  let expectedResult: IFotoPozo | IFotoPozo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FotoPozoService);
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

    it('should create a FotoPozo', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const fotoPozo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fotoPozo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FotoPozo', () => {
      const fotoPozo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fotoPozo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FotoPozo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FotoPozo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FotoPozo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFotoPozoToCollectionIfMissing', () => {
      it('should add a FotoPozo to an empty array', () => {
        const fotoPozo: IFotoPozo = sampleWithRequiredData;
        expectedResult = service.addFotoPozoToCollectionIfMissing([], fotoPozo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoPozo);
      });

      it('should not add a FotoPozo to an array that contains it', () => {
        const fotoPozo: IFotoPozo = sampleWithRequiredData;
        const fotoPozoCollection: IFotoPozo[] = [
          {
            ...fotoPozo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFotoPozoToCollectionIfMissing(fotoPozoCollection, fotoPozo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FotoPozo to an array that doesn't contain it", () => {
        const fotoPozo: IFotoPozo = sampleWithRequiredData;
        const fotoPozoCollection: IFotoPozo[] = [sampleWithPartialData];
        expectedResult = service.addFotoPozoToCollectionIfMissing(fotoPozoCollection, fotoPozo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoPozo);
      });

      it('should add only unique FotoPozo to an array', () => {
        const fotoPozoArray: IFotoPozo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fotoPozoCollection: IFotoPozo[] = [sampleWithRequiredData];
        expectedResult = service.addFotoPozoToCollectionIfMissing(fotoPozoCollection, ...fotoPozoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fotoPozo: IFotoPozo = sampleWithRequiredData;
        const fotoPozo2: IFotoPozo = sampleWithPartialData;
        expectedResult = service.addFotoPozoToCollectionIfMissing([], fotoPozo, fotoPozo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fotoPozo);
        expect(expectedResult).toContain(fotoPozo2);
      });

      it('should accept null and undefined values', () => {
        const fotoPozo: IFotoPozo = sampleWithRequiredData;
        expectedResult = service.addFotoPozoToCollectionIfMissing([], null, fotoPozo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fotoPozo);
      });

      it('should return initial array if no FotoPozo is added', () => {
        const fotoPozoCollection: IFotoPozo[] = [sampleWithRequiredData];
        expectedResult = service.addFotoPozoToCollectionIfMissing(fotoPozoCollection, undefined, null);
        expect(expectedResult).toEqual(fotoPozoCollection);
      });
    });

    describe('compareFotoPozo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFotoPozo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFotoPozo(entity1, entity2);
        const compareResult2 = service.compareFotoPozo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFotoPozo(entity1, entity2);
        const compareResult2 = service.compareFotoPozo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFotoPozo(entity1, entity2);
        const compareResult2 = service.compareFotoPozo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
