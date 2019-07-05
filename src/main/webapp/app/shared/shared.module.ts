import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SysleneSharedLibsModule, SysleneSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { AgmCoreModule } from '@agm/core';
@NgModule({
  imports: [
    SysleneSharedLibsModule,
    SysleneSharedCommonModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyA3PqiYidX6C7jB0firiGMyLS0L_3mIYko'
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
