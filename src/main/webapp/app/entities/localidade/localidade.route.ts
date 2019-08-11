import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Localidade } from 'app/shared/model/localidade.model';
import { LocalidadeService } from './localidade.service';
import { LocalidadeComponent } from './localidade.component';
import { LocalidadeDetailComponent } from './localidade-detail.component';
import { LocalidadeUpdateComponent } from './localidade-update.component';
import { LocalidadeDeletePopupComponent } from './localidade-delete-dialog.component';
import { ILocalidade } from 'app/shared/model/localidade.model';

@Injectable({ providedIn: 'root' })
export class LocalidadeResolve implements Resolve<ILocalidade> {
  constructor(private service: LocalidadeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILocalidade> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Localidade>) => response.ok),
        map((localidade: HttpResponse<Localidade>) => localidade.body)
      );
    }
    return of(new Localidade());
  }
}

export const localidadeRoute: Routes = [
  {
    path: '',
    component: LocalidadeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'sysleneApp.localidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LocalidadeDetailComponent,
    resolve: {
      localidade: LocalidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sysleneApp.localidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LocalidadeUpdateComponent,
    resolve: {
      localidade: LocalidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sysleneApp.localidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LocalidadeUpdateComponent,
    resolve: {
      localidade: LocalidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sysleneApp.localidade.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const localidadePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LocalidadeDeletePopupComponent,
    resolve: {
      localidade: LocalidadeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sysleneApp.localidade.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
