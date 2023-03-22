import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {AuthenticationRoutingModule} from "./authentication-routing.module";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {AuthInterceptor} from "../../helpers/auth.interceptor";
import {IsAuthenticatedGuard} from "./guards/is-authenticated-guard";
import {AuthService} from "../../services/auth.service";
import {AuthDirective} from "./model/auth.directive";
import {LoginComponent} from "./components/login/login.component";

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    AuthenticationRoutingModule,
    BrowserModule,
    FormsModule,
  ],
  providers: [
    AuthInterceptor,
    IsAuthenticatedGuard,
    AuthService,
  ],
  exports: [
    AuthDirective,
  ],

  declarations: [
    LoginComponent,
    AuthDirective,
  ],

})
export class AuthenticationModule {
}
