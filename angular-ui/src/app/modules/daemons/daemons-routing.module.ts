import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PortalLayoutComponent} from "../../components/portal-layout/portal-layout.component";
import {IsAuthenticatedGuard} from "../authorization/guards/is-authenticated-guard";
import {DaemonsListComponent} from "./components/daemons-list/daemons-list.component";
import {DaemonCreationComponent} from "./components/daemon-creation/daemon-creation.component";

const routes: Routes = [
  {
    path: 'daemons',
    component: PortalLayoutComponent,
    canActivate: [IsAuthenticatedGuard],
    children: [
      {
        path: '',
        component: DaemonsListComponent,
      },
      {
        path: 'create',
        component: DaemonCreationComponent,
      },
      {
        path: ':daemonId',
        component: DaemonCreationComponent,
      },
    ]
  },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class DaemonsRoutingModule {
}
