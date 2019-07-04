import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SysleneSharedLibsModule, SysleneSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { AgmCoreModule } from '@agm/core';
@NgModule({
  imports: [
    SysleneSharedLibsModule,
    SysleneSharedCommonModule,
    AgmCoreModule.forRoot({
      apiKey: 'YOUR_KEY'
    })
  ],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [SysleneSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SysleneSharedModule {
  static forRoot() {
    return {
      ngModule: SysleneSharedModule
    };
  }
}
