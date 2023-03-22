import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {SharedModule} from "../../shared/shared.module";
import {AuthenticationModule} from "../authorization/authentication.module";
import {DashboardRoutingModule} from "./dashboard-routing.module";
import {DashboardComponent} from "./components/dashboard/dashboard.component";

@NgModule({
  imports: [
    CommonModule,
    SharedModule,

    AuthenticationModule,
    DashboardRoutingModule,
  ],
  declarations: [
    DashboardComponent
  ],
  providers: []
})
export class DashboardModule {
}
