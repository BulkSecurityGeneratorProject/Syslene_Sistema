import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILocalidade } from 'app/shared/model/localidade.model';
import { IDomicilio } from 'app/shared/model/domicilio.model';

type EntityResponseType = HttpResponse<ILocalidade>;
type EntityArrayResponseType = HttpResponse<ILocalidade[]>;

@Injectable({ providedIn: 'root' })
export class LocalidadeService {
  public resourceUrl = SERVER_API_URL + 'api/localidades';

  constructor(protected http: HttpClient) {}

  create(localidade: ILocalidade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(localidade);
    return this.http
      .post<ILocalidade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(localidade: ILocalidade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(localidade);
    return this.http
      .put<ILocalidade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILocalidade>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILocalidade[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryNoPage(req?: any): Promise<any> {
    return this.http
      .get<ILocalidade[]>(this.resourceUrl + '-nopage')
      .toPromise()
      .then(res => {
        return res;
      });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  count(): Promise<any> {
    return this.http
      .get(`${this.resourceUrl}/count`)
      .toPromise()
      .then(response => response);
  }

  protected convertDateFromClient(localidade: ILocalidade): ILocalidade {
    const copy: ILocalidade = Object.assign({}, localidade, {
      dataAlteracao:
        localidade.dataAlteracao != null && localidade.dataAlteracao.isValid() ? localidade.dataAlteracao.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataAlteracao = res.body.dataAlteracao != null ? moment(res.body.dataAlteracao) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((localidade: ILocalidade) => {
        localidade.dataAlteracao = localidade.dataAlteracao != null ? moment(localidade.dataAlteracao) : null;
      });
    }
    return res;
  }
}
