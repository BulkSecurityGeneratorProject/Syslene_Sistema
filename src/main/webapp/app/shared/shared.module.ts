import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SysleneSharedLibsModule, SysleneSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [SysleneSharedLibsModule, SysleneSharedCommonModule],
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
