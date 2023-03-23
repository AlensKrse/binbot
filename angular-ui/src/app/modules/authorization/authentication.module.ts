import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {AuthenticationRoutingModule} from "./authentication-routing.module";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AuthInterceptor} from "../../helpers/auth.interceptor";
import {IsAuthenticatedGuard} from "./guards/is-authenticated-guard";
import {AuthService} from "../../services/auth.service";
import {AuthDirective} from "./model/auth.directive";
import {LoginComponent} from "./components/login/login.component";
import {MatCardModule} from "@angular/material/card";
import {MatInputModule} from "@angular/material/input";
import {GoogleCodeDialogComponent} from "./components/google-code-dialog/google-code-dialog.component";
import {QRCodeModule} from "angularx-qrcode";
import {CodeInputModule} from "angular-code-input";
import {MatDialogModule} from "@angular/material/dialog";
import {TranslateModule} from "@ngx-translate/core";
import {MatButtonModule} from "@angular/material/button";

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    AuthenticationRoutingModule,
    BrowserModule,
    FormsModule,
    MatCardModule,
    MatInputModule,
    ReactiveFormsModule,
    QRCodeModule,
    CodeInputModule,
    MatDialogModule,
    TranslateModule,
    MatButtonModule,

  ],
  providers: [
    AuthInterceptor,
    IsAuthenticatedGuard,
    AuthService,
  ],
  exports: [
    AuthDirective,
    TranslateModule,
  ],

  declarations: [
    LoginComponent,
    GoogleCodeDialogComponent,
    AuthDirective,
  ],

})
export class AuthenticationModule {
}

