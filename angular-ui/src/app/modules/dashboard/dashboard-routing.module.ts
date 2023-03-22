import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {PortalLayoutComponent} from "../../components/portal-layout/portal-layout.component";
import {IsAuthenticatedGuard} from "../authorization/guards/is-authenticated-guard";

const routes: Routes = [
  {
    path: 'dashboard',
    component: PortalLayoutComponent,
    canActivate: [IsAuthenticatedGuard],
    children: [
      {
        path: '',
        component: DashboardComponent,
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule {
}
