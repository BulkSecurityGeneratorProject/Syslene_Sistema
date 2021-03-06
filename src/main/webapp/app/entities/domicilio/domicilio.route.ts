import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Domicilio } from 'app/shared/model/domicilio.model';
import { DomicilioService } from '../../services/domicilio.service';
import { DomicilioComponent } from './domicilio.component';
import { DomicilioDetailComponent } from './domicilio-detail.component';
import { DomicilioUpdateComponent } from './domicilio-update.component';
import { DomicilioDeletePopupComponent } from './domicilio-delete-dialog.component';
import { IDomicilio } from 'app/shared/model/domicilio.model';

@Injectable({ providedIn: 'root' })
export class DomicilioResolve implements Resolve<IDomicilio> {
  constructor(private service: DomicilioService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDomicilio> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Domicilio>) => response.ok),
        map((domicilio: HttpResponse<Domicilio>) => domicilio.body)
      );
    }
    return of(new Domicilio());
  }
}

export const domicilioRoute: Routes = [
  {
    path: '',
    component: DomicilioComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'sysleneApp.domicilio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DomicilioDetailComponent,
    resolve: {
      domicilio: DomicilioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sysleneApp.domicilio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DomicilioUpdateComponent,
    resolve: {
      domicilio: DomicilioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sysleneApp.domicilio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DomicilioUpdateComponent,
    resolve: {
      domicilio: DomicilioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sysleneApp.domicilio.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const domicilioPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DomicilioDeletePopupComponent,
    resolve: {
      domicilio: DomicilioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sysleneApp.domicilio.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
