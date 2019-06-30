/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DomicilioService } from 'app/entities/domicilio/domicilio.service';
import { IDomicilio, Domicilio } from 'app/shared/model/domicilio.model';

describe('Service Tests', () => {
  describe('Domicilio Service', () => {
    let injector: TestBed;
    let service: DomicilioService;
    let httpMock: HttpTestingController;
    let elemDefault: IDomicilio;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DomicilioService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Domicilio(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        currentDate,
        false,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dataCadastro: currentDate.format(DATE_FORMAT),
            dataAlteracao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Domicilio', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataCadastro: currentDate.format(DATE_FORMAT),
            dataAlteracao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataCadastro: currentDate,
            dataAlteracao: currentDate
          },
          returnedFromService
        );
        service
          .create(new Domicilio(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Domicilio', async () => {
        const returnedFromService = Object.assign(
          {
            nomeMorador: 'BBBBBB',
            endereco: 'BBBBBB',
            latitude: 'BBBBBB',
            longitude: 'BBBBBB',
            numeroHabitantes: 1,
            ligacaoDomiciliarAgua: true,
            poco: true,
            cisterna: true,
            reservatorioElevado: true,
            reservatorioSemiElevado: true,
            conjuntoSanitario: true,
            piaCozinha: true,
            tanqueLavarRoupa: true,
            filtroDomestico: true,
            tanqueSeptico: true,
            sumidouro: true,
            valaInfiltracao: true,
            sistemaReuso: true,
            ligacaoDomiciliarEsgoto: true,
            recipenteResiduosSolidos: true,
            dataCadastro: currentDate.format(DATE_FORMAT),
            levantamentoConcluido: true,
            dataAlteracao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataCadastro: currentDate,
            dataAlteracao: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Domicilio', async () => {
        const returnedFromService = Object.assign(
          {
            nomeMorador: 'BBBBBB',
            endereco: 'BBBBBB',
            latitude: 'BBBBBB',
            longitude: 'BBBBBB',
            numeroHabitantes: 1,
            ligacaoDomiciliarAgua: true,
            poco: true,
            cisterna: true,
            reservatorioElevado: true,
            reservatorioSemiElevado: true,
            conjuntoSanitario: true,
            piaCozinha: true,
            tanqueLavarRoupa: true,
            filtroDomestico: true,
            tanqueSeptico: true,
            sumidouro: true,
            valaInfiltracao: true,
            sistemaReuso: true,
            ligacaoDomiciliarEsgoto: true,
            recipenteResiduosSolidos: true,
            dataCadastro: currentDate.format(DATE_FORMAT),
            levantamentoConcluido: true,
            dataAlteracao: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataCadastro: currentDate,
            dataAlteracao: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Domicilio', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
