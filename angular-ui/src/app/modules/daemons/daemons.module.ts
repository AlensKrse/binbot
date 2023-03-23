import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {SharedModule} from "../../shared/shared.module";
import {DaemonsListComponent} from "./components/daemons-list/daemons-list.component";
import {DaemonCreationComponent} from './components/daemon-creation/daemon-creation.component';
import {DaemonsRoutingModule} from "./daemons-routing.module";

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    DaemonsRoutingModule

  ],
  declarations: [
    DaemonsListComponent,
    DaemonCreationComponent
  ],
  providers: []
})
export class DaemonsModule {
}
