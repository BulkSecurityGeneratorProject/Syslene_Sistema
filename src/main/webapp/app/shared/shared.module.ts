import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SysleneSharedLibsModule, SysleneSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { AgmCoreModule } from '@agm/core';
import { ExcelService } from 'app/services/excel.service';

@NgModule({
  imports: [
    SysleneSharedLibsModule,
    SysleneSharedCommonModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyAcPUnsuBcJTWz0An8GXazaUmz0DKxwxmY'
    })
  ],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [SysleneSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective, AgmCoreModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SysleneSharedModule {
  static forRoot() {
    return {
      ngModule: SysleneSharedModule
    };
  }
}
