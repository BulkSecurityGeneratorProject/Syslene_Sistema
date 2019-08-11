import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICidade, Cidade } from 'app/shared/model/cidade.model';
import { CidadeService } from './cidade.service';

@Component({
  selector: 'jhi-cidade-update',
  templateUrl: './cidade-update.component.html'
})
export class CidadeUpdateComponent implements OnInit {
  cidade: ICidade;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    descricao: [null, [Validators.required, Validators.maxLength(80)]],
    estado: [null, [Validators.required]]
  });

  constructor(protected cidadeService: CidadeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cidade }) => {
      this.updateForm(cidade);
      this.cidade = cidade;
    });
  }

  updateForm(cidade: ICidade) {
    this.editForm.patchValue({
      id: cidade.id,
      descricao: cidade.descricao,
      estado: cidade.estado
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cidade = this.createFromForm();
    if (cidade.id !== undefined) {
      this.subscribeToSaveResponse(this.cidadeService.update(cidade));
    } else {
      this.subscribeToSaveResponse(this.cidadeService.create(cidade));
    }
  }

  private createFromForm(): ICidade {
    const entity = {
      ...new Cidade(),
      id: this.editForm.get(['id']).value,
      descricao: this.editForm.get(['descricao']).value,
      estado: this.editForm.get(['estado']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICidade>>) {
    result.subscribe((res: HttpResponse<ICidade>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
