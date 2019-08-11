import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'localidade',
        loadChildren: './localidade/localidade.module#SysleneLocalidadeModule'
      },
      {
        path: 'domicilio',
        loadChildren: './domicilio/domicilio.module#SysleneDomicilioModule'
      },
      {
        path: 'cidade',
        loadChildren: './cidade/cidade.module#SysleneCidadeModule'
      },
      {
        path: 'localidade',
        loadChildren: './localidade/localidade.module#SysleneLocalidadeModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SysleneEntityModule {}
