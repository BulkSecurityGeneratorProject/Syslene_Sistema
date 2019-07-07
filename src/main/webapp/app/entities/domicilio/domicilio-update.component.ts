import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDomicilio, Domicilio } from 'app/shared/model/domicilio.model';
import { DomicilioService } from '../../services/domicilio.service';
import { IUser, UserService } from 'app/core';
import { ILocalidade } from 'app/shared/model/localidade.model';
import { LocalidadeService } from 'app/entities/localidade';

@Component({
  selector: 'jhi-domicilio-update',
  templateUrl: './domicilio-update.component.html'
})
export class DomicilioUpdateComponent implements OnInit {
  domicilio: IDomicilio;
  isSaving: boolean;

  users: IUser[];

  localidades: ILocalidade[];
  dataCadastroDp: any;
  dataAlteracaoDp: any;

  editForm = this.fb.group({
    id: [],
    nomeMorador: [null, [Validators.required, Validators.maxLength(80)]],
    endereco: [null, [Validators.required, Validators.maxLength(80)]],
    latitude: [null, [Validators.required]],
    longitude: [null, [Validators.required]],
    numeroHabitantes: [],
    ligacaoDomiciliarAgua: [],
    poco: [],
    cisterna: [],
    reservatorioElevado: [],
    reservatorioSemiElevado: [],
    conjuntoSanitario: [],
    piaCozinha: [],
    tanqueLavarRoupa: [],
    filtroDomestico: [],
    tanqueSeptico: [],
    sumidouro: [],
    valaInfiltracao: [],
    sistemaReuso: [],
    ligacaoDomiciliarEsgoto: [],
    recipenteResiduosSolidos: [],
    dataCadastro: [],
    levantamentoConcluido: [],
    dataAlteracao: [],
    userId: [],
    localidadeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected domicilioService: DomicilioService,
    protected userService: UserService,
    protected localidadeService: LocalidadeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ domicilio }) => {
      this.updateForm(domicilio);
      this.domicilio = domicilio;
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.localidadeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILocalidade[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILocalidade[]>) => response.body)
      )
      .subscribe((res: ILocalidade[]) => (this.localidades = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(domicilio: IDomicilio) {
    this.editForm.patchValue({
      id: domicilio.id,
      nomeMorador: domicilio.nomeMorador,
      endereco: domicilio.endereco,
      latitude: domicilio.latitude,
      longitude: domicilio.longitude,
      numeroHabitantes: domicilio.numeroHabitantes,
      ligacaoDomiciliarAgua: domicilio.ligacaoDomiciliarAgua,
      poco: domicilio.poco,
      cisterna: domicilio.cisterna,
      reservatorioElevado: domicilio.reservatorioElevado,
      reservatorioSemiElevado: domicilio.reservatorioSemiElevado,
      conjuntoSanitario: domicilio.conjuntoSanitario,
      piaCozinha: domicilio.piaCozinha,
      tanqueLavarRoupa: domicilio.tanqueLavarRoupa,
      filtroDomestico: domicilio.filtroDomestico,
      tanqueSeptico: domicilio.tanqueSeptico,
      sumidouro: domicilio.sumidouro,
      valaInfiltracao: domicilio.valaInfiltracao,
      sistemaReuso: domicilio.sistemaReuso,
      ligacaoDomiciliarEsgoto: domicilio.ligacaoDomiciliarEsgoto,
      recipenteResiduosSolidos: domicilio.recipenteResiduosSolidos,
      dataCadastro: domicilio.dataCadastro,
      levantamentoConcluido: domicilio.levantamentoConcluido,
      dataAlteracao: domicilio.dataAlteracao,
      userId: domicilio.userId,
      localidadeId: domicilio.localidadeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const domicilio = this.createFromForm();
    if (domicilio.id !== undefined) {
      this.subscribeToSaveResponse(this.domicilioService.update(domicilio));
    } else {
      this.subscribeToSaveResponse(this.domicilioService.create(domicilio));
    }
  }

  private createFromForm(): IDomicilio {
    const entity = {
      ...new Domicilio(),
      id: this.editForm.get(['id']).value,
      nomeMorador: this.editForm.get(['nomeMorador']).value,
      endereco: this.editForm.get(['endereco']).value,
      latitude: this.editForm.get(['latitude']).value,
      longitude: this.editForm.get(['longitude']).value,
      numeroHabitantes: this.editForm.get(['numeroHabitantes']).value,
      ligacaoDomiciliarAgua: this.editForm.get(['ligacaoDomiciliarAgua']).value,
      poco: this.editForm.get(['poco']).value,
      cisterna: this.editForm.get(['cisterna']).value,
      reservatorioElevado: this.editForm.get(['reservatorioElevado']).value,
      reservatorioSemiElevado: this.editForm.get(['reservatorioSemiElevado']).value,
      conjuntoSanitario: this.editForm.get(['conjuntoSanitario']).value,
      piaCozinha: this.editForm.get(['piaCozinha']).value,
      tanqueLavarRoupa: this.editForm.get(['tanqueLavarRoupa']).value,
      filtroDomestico: this.editForm.get(['filtroDomestico']).value,
      tanqueSeptico: this.editForm.get(['tanqueSeptico']).value,
      sumidouro: this.editForm.get(['sumidouro']).value,
      valaInfiltracao: this.editForm.get(['valaInfiltracao']).value,
      sistemaReuso: this.editForm.get(['sistemaReuso']).value,
      ligacaoDomiciliarEsgoto: this.editForm.get(['ligacaoDomiciliarEsgoto']).value,
      recipenteResiduosSolidos: this.editForm.get(['recipenteResiduosSolidos']).value,
      dataCadastro: this.editForm.get(['dataCadastro']).value,
      levantamentoConcluido: this.editForm.get(['levantamentoConcluido']).value,
      dataAlteracao: this.editForm.get(['dataAlteracao']).value,
      userId: this.editForm.get(['userId']).value,
      localidadeId: this.editForm.get(['localidadeId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDomicilio>>) {
    result.subscribe((res: HttpResponse<IDomicilio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackLocalidadeById(index: number, item: ILocalidade) {
    return item.id;
  }
}
