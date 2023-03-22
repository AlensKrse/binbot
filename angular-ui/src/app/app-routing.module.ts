import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {PortalLayoutComponent} from "./components/portal-layout/portal-layout.component";
import {IsAuthenticatedGuard} from "./modules/authorization/guards/is-authenticated-guard";

const routes: Routes = [
  {
    children: [],

    path: '',
    component: PortalLayoutComponent,
    canActivate: [IsAuthenticatedGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
