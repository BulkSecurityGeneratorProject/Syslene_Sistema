import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDomicilio } from 'app/shared/model/domicilio.model';

type EntityResponseType = HttpResponse<IDomicilio>;
type EntityArrayResponseType = HttpResponse<IDomicilio[]>;

@Injectable({ providedIn: 'root' })
export class DomicilioService {
  public resourceUrl = SERVER_API_URL + 'api/domicilios';

  constructor(protected http: HttpClient) {}

  create(domicilio: IDomicilio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(domicilio);
    return this.http
      .post<IDomicilio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(domicilio: IDomicilio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(domicilio);
    return this.http
      .put<IDomicilio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDomicilio>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDomicilio[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(domicilio: IDomicilio): IDomicilio {
    const copy: IDomicilio = Object.assign({}, domicilio, {
      dataCadastro: domicilio.dataCadastro != null && domicilio.dataCadastro.isValid() ? domicilio.dataCadastro.format(DATE_FORMAT) : null,
      dataAlteracao:
        domicilio.dataAlteracao != null && domicilio.dataAlteracao.isValid() ? domicilio.dataAlteracao.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataCadastro = res.body.dataCadastro != null ? moment(res.body.dataCadastro) : null;
      res.body.dataAlteracao = res.body.dataAlteracao != null ? moment(res.body.dataAlteracao) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((domicilio: IDomicilio) => {
        domicilio.dataCadastro = domicilio.dataCadastro != null ? moment(domicilio.dataCadastro) : null;
        domicilio.dataAlteracao = domicilio.dataAlteracao != null ? moment(domicilio.dataAlteracao) : null;
      });
    }
    return res;
  }
}
