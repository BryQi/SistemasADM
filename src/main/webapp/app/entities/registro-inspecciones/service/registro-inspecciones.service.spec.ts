import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRegistroInspecciones } from '../registro-inspecciones.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../registro-inspecciones.test-samples';

import { RegistroInspeccionesService, RestRegistroInspecciones } from './registro-inspecciones.service';

const requireRestSample: RestRegistroInspecciones = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
};

describe('RegistroInspecciones Service', () => {
  let service: RegistroInspeccionesService;
  let httpMock: HttpTestingController;
  let expectedResult: IRegistroInspecciones | IRegistroInspecciones[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RegistroInspeccionesService);
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

    it('should create a RegistroInspecciones', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const registroInspecciones = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(registroInspecciones).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RegistroInspecciones', () => {
      const registroInspecciones = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(registroInspecciones).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RegistroInspecciones', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RegistroInspecciones', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RegistroInspecciones', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRegistroInspeccionesToCollectionIfMissing', () => {
      it('should add a RegistroInspecciones to an empty array', () => {
        const registroInspecciones: IRegistroInspecciones = sampleWithRequiredData;
        expectedResult = service.addRegistroInspeccionesToCollectionIfMissing([], registroInspecciones);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(registroInspecciones);
      });

      it('should not add a RegistroInspecciones to an array that contains it', () => {
        const registroInspecciones: IRegistroInspecciones = sampleWithRequiredData;
        const registroInspeccionesCollection: IRegistroInspecciones[] = [
          {
            ...registroInspecciones,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRegistroInspeccionesToCollectionIfMissing(registroInspeccionesCollection, registroInspecciones);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RegistroInspecciones to an array that doesn't contain it", () => {
        const registroInspecciones: IRegistroInspecciones = sampleWithRequiredData;
        const registroInspeccionesCollection: IRegistroInspecciones[] = [sampleWithPartialData];
        expectedResult = service.addRegistroInspeccionesToCollectionIfMissing(registroInspeccionesCollection, registroInspecciones);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(registroInspecciones);
      });

      it('should add only unique RegistroInspecciones to an array', () => {
        const registroInspeccionesArray: IRegistroInspecciones[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const registroInspeccionesCollection: IRegistroInspecciones[] = [sampleWithRequiredData];
        expectedResult = service.addRegistroInspeccionesToCollectionIfMissing(registroInspeccionesCollection, ...registroInspeccionesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const registroInspecciones: IRegistroInspecciones = sampleWithRequiredData;
        const registroInspecciones2: IRegistroInspecciones = sampleWithPartialData;
        expectedResult = service.addRegistroInspeccionesToCollectionIfMissing([], registroInspecciones, registroInspecciones2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(registroInspecciones);
        expect(expectedResult).toContain(registroInspecciones2);
      });

      it('should accept null and undefined values', () => {
        const registroInspecciones: IRegistroInspecciones = sampleWithRequiredData;
        expectedResult = service.addRegistroInspeccionesToCollectionIfMissing([], null, registroInspecciones, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(registroInspecciones);
      });

      it('should return initial array if no RegistroInspecciones is added', () => {
        const registroInspeccionesCollection: IRegistroInspecciones[] = [sampleWithRequiredData];
        expectedResult = service.addRegistroInspeccionesToCollectionIfMissing(registroInspeccionesCollection, undefined, null);
        expect(expectedResult).toEqual(registroInspeccionesCollection);
      });
    });

    describe('compareRegistroInspecciones', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRegistroInspecciones(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRegistroInspecciones(entity1, entity2);
        const compareResult2 = service.compareRegistroInspecciones(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRegistroInspecciones(entity1, entity2);
        const compareResult2 = service.compareRegistroInspecciones(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRegistroInspecciones(entity1, entity2);
        const compareResult2 = service.compareRegistroInspecciones(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
