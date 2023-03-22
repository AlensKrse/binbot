import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PortalLayoutComponent} from "../../components/portal-layout/portal-layout.component";
import {IsAuthenticatedGuard} from "../authorization/guards/is-authenticated-guard";
import {UsersListComponent} from "./components/users-list/users-list.component";
import {UserCreationComponent} from "./components/user-creation/user-creation.component";

const routes: Routes = [
  {
    path: 'users',
    component: PortalLayoutComponent,
    canActivate: [IsAuthenticatedGuard],
    children: [
      {
        path: '',
        component: UsersListComponent,
      },
      {
        path: 'create',
        component: UserCreationComponent,
      },
      {
        path: ':userId',
        component: UserCreationComponent,
      },
    ]
  },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class UsersRoutingModule {
}
