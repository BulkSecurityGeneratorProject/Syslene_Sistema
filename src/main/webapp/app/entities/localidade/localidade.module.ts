import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SysleneSharedModule } from 'app/shared';
import {
  LocalidadeComponent,
  LocalidadeDetailComponent,
  LocalidadeUpdateComponent,
  LocalidadeDeletePopupComponent,
  LocalidadeDeleteDialogComponent,
  localidadeRoute,
  localidadePopupRoute
} from './';

const ENTITY_STATES = [...localidadeRoute, ...localidadePopupRoute];

@NgModule({
  imports: [SysleneSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LocalidadeComponent,
    LocalidadeDetailComponent,
    LocalidadeUpdateComponent,
    LocalidadeDeleteDialogComponent,
    LocalidadeDeletePopupComponent
  ],
  entryComponents: [LocalidadeComponent, LocalidadeUpdateComponent, LocalidadeDeleteDialogComponent, LocalidadeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SysleneLocalidadeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
