import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { SysleneSharedModule } from 'app/shared';
import {
  DomicilioComponent,
  DomicilioDetailComponent,
  DomicilioUpdateComponent,
  DomicilioDeletePopupComponent,
  DomicilioDeleteDialogComponent,
  domicilioRoute,
  domicilioPopupRoute
} from './';

const ENTITY_STATES = [...domicilioRoute, ...domicilioPopupRoute];

@NgModule({
  imports: [SysleneSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DomicilioComponent,
    DomicilioDetailComponent,
    DomicilioUpdateComponent,
    DomicilioDeleteDialogComponent,
    DomicilioDeletePopupComponent
  ],
  entryComponents: [DomicilioComponent, DomicilioUpdateComponent, DomicilioDeleteDialogComponent, DomicilioDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SysleneDomicilioModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
