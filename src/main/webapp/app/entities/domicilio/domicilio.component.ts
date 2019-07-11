import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IDomicilio } from 'app/shared/model/domicilio.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { DomicilioService } from '../../services/domicilio.service';
import { saveAs } from 'file-saver';
import { ExcelService } from 'app/services/excel.service';

@Component({
  selector: 'jhi-domicilio',
  templateUrl: './domicilio.component.html',
  styleUrls: ['./domicilio.component.scss']
})
export class DomicilioComponent implements OnInit, OnDestroy {
  currentAccount: any;
  domicilios: IDomicilio[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  necessitaAgua = 'null';
  poco = 'null';
  cisterna = 'null';
  reservatorioElevado = 'null';
  reservatorioSemiElevado = 'null';
  conjuntoSanitario = 'null';
  piaCozinha = 'null';
  tanqueLavarRoupa = 'null';
  filtroDomestico = 'null';
  tanqueSeptico = 'null';
  sumidouro = 'null';
  valaInfiltracao = 'null';
  sistemaReuso = 'null';
  ligacaoDomiciliarEsgoto = 'null';
  recipenteResiduosSolidos = 'null';
  levantamentoConcluido = 'null';

  constructor(
    protected domicilioService: DomicilioService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    private excelService: ExcelService
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    var req = {
      page: this.page - 1,
      size: this.itemsPerPage
    };

    if (this.necessitaAgua && this.necessitaAgua != 'null') req['ligacaoDomiciliarAgua.equals'] = this.necessitaAgua;
    if (this.poco && this.poco != 'null') req['poco.equals'] = this.poco;
    if (this.cisterna && this.cisterna != 'null') req['cisterna.equals'] = this.cisterna;
    if (this.reservatorioElevado && this.reservatorioElevado != 'null') req['reservatorioElevado.equals'] = this.reservatorioElevado;
    if (this.reservatorioSemiElevado && this.reservatorioSemiElevado != 'null')
      req['reservatorioSemiElevado.equals'] = this.reservatorioSemiElevado;
    if (this.conjuntoSanitario && this.conjuntoSanitario != 'null') req['conjuntoSanitario.equals'] = this.conjuntoSanitario;
    if (this.piaCozinha && this.piaCozinha != 'null') req['piaCozinha.equals'] = this.piaCozinha;
    if (this.tanqueLavarRoupa && this.tanqueLavarRoupa != 'null') req['tanqueLavarRoupa.equals'] = this.tanqueLavarRoupa;
    if (this.filtroDomestico && this.filtroDomestico != 'null') req['filtroDomestico.equals'] = this.filtroDomestico;
    if (this.tanqueSeptico && this.tanqueSeptico != 'null') req['tanqueSeptico.equals'] = this.tanqueSeptico;
    if (this.sumidouro && this.sumidouro != 'null') req['sumidouro.equals'] = this.sumidouro;
    if (this.valaInfiltracao && this.valaInfiltracao != 'null') req['valaInfiltracao.equals'] = this.valaInfiltracao;
    if (this.sistemaReuso && this.sistemaReuso != 'null') req['sistemaReuso.equals'] = this.sistemaReuso;
    if (this.ligacaoDomiciliarEsgoto && this.ligacaoDomiciliarEsgoto != 'null')
      req['ligacaoDomiciliarEsgoto.equals'] = this.ligacaoDomiciliarEsgoto;
    if (this.recipenteResiduosSolidos && this.recipenteResiduosSolidos != 'null')
      req['recipenteResiduosSolidos.equals'] = this.recipenteResiduosSolidos;
    if (this.levantamentoConcluido && this.levantamentoConcluido != 'null')
      req['levantamentoConcluido.equals'] = this.levantamentoConcluido;

    this.domicilioService
      .query(req)
      .subscribe(
        (res: HttpResponse<IDomicilio[]>) => this.paginateDomicilios(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  exportaExcel() {
    var req = {
      page: this.page - 1,
      size: this.itemsPerPage
    };
    this.domicilioService.query(req).subscribe(dados => {
      console.log(dados);
      saveAs(dados, 'domicilios.pdf');
    });
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/domicilio'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/domicilio',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDomicilios();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDomicilio) {
    return item.id;
  }

  registerChangeInDomicilios() {
    this.eventSubscriber = this.eventManager.subscribe('domicilioListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDomicilios(data: IDomicilio[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.domicilios = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  exportAsXLSX(): void {
    this.excelService.exportAsExcelFile(this.domicilios, 'syslene');
  }
}
