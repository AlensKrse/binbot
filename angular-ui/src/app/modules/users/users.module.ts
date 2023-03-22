import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {SharedModule} from "../../shared/shared.module";
import {UsersListComponent} from './components/users-list/users-list.component';
import {MatSortModule} from "@angular/material/sort";
import {UserCreationComponent} from './components/user-creation/user-creation.component';
import {MatPaginatorModule} from "@angular/material/paginator";
import {UsersRoutingModule} from "./users-routing.module";

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    UsersRoutingModule,

    MatSortModule,
    MatPaginatorModule,
  ],
  declarations: [
    UsersListComponent,
    UserCreationComponent
  ],
  providers: []
})
export class UsersModule {
}
