import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILocalidade, Localidade } from 'app/shared/model/localidade.model';
import { LocalidadeService } from './localidade.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-localidade-update',
  templateUrl: './localidade-update.component.html'
})
export class LocalidadeUpdateComponent implements OnInit {
  localidade: ILocalidade;
  isSaving: boolean;

  users: IUser[];
  dataAlteracaoDp: any;

  editForm = this.fb.group({
    id: [],
    descricao: [null, [Validators.required, Validators.maxLength(80)]],
    abastecimentoAgua: [null, [Validators.required]],
    esgotoSanitario: [null, [Validators.required]],
    coletaResiduos: [null, [Validators.required]],
    dataAlteracao: [],
    userId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected localidadeService: LocalidadeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ localidade }) => {
      this.updateForm(localidade);
      this.localidade = localidade;
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(localidade: ILocalidade) {
    this.editForm.patchValue({
      id: localidade.id,
      descricao: localidade.descricao,
      abastecimentoAgua: localidade.abastecimentoAgua,
      esgotoSanitario: localidade.esgotoSanitario,
      coletaResiduos: localidade.coletaResiduos,
      dataAlteracao: localidade.dataAlteracao,
      userId: localidade.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const localidade = this.createFromForm();
    if (localidade.id !== undefined) {
      this.subscribeToSaveResponse(this.localidadeService.update(localidade));
    } else {
      this.subscribeToSaveResponse(this.localidadeService.create(localidade));
    }
  }

  private createFromForm(): ILocalidade {
    const entity = {
      ...new Localidade(),
      id: this.editForm.get(['id']).value,
      descricao: this.editForm.get(['descricao']).value,
      abastecimentoAgua: this.editForm.get(['abastecimentoAgua']).value,
      esgotoSanitario: this.editForm.get(['esgotoSanitario']).value,
      coletaResiduos: this.editForm.get(['coletaResiduos']).value,
      dataAlteracao: this.editForm.get(['dataAlteracao']).value,
      userId: this.editForm.get(['userId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocalidade>>) {
    result.subscribe((res: HttpResponse<ILocalidade>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
