import {NgModule} from "@angular/core";
import {AppComponent} from "./app.component";
import {PortalLayoutComponent} from "./components/portal-layout/portal-layout.component";
import {HeaderComponent} from "./components/header/header.component";
import {PageLoadingIndicatorComponent} from "./components/page-loading-indicator/page-loading-indicator.component";
import {BrowserModule} from "@angular/platform-browser";
import {AppRoutingModule} from "./app-routing.module";
import {FormsModule} from "@angular/forms";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {AuthenticationModule} from "./modules/authorization/authentication.module";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {authInterceptorProviders} from "./helpers/auth.interceptor";
import {DashboardModule} from "./modules/dashboard/dashboard.module";
import {MdbCollapseModule} from "mdb-angular-ui-kit/collapse";
import {MdbDropdownModule} from "mdb-angular-ui-kit/dropdown";
import {MatButtonModule} from "@angular/material/button";
import {UsersModule} from "./modules/users/users.module";
import {SecureHttpClientService} from "./services/secure-http-client.service";
import {HttpClientService} from "./services/http-client.service";
import {PageLoadingService} from "./services/page-loading.service";
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {AlertService} from "./services/alert.service";
import {MatIconModule} from "@angular/material/icon";


@NgModule({
  declarations: [
    AppComponent,
    PortalLayoutComponent,
    HeaderComponent,
    PageLoadingIndicatorComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    MatProgressBarModule,
    HttpClientModule,
    MdbDropdownModule,
    MdbCollapseModule,
    MatButtonModule,
    MatIconModule,

    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: httpTranslateLoader,
        deps: [HttpClient]
      }
    }),

    AuthenticationModule,
    DashboardModule,
    UsersModule,

  ],
  exports: [TranslateModule],
  providers: [authInterceptorProviders, SecureHttpClientService, HttpClientService, PageLoadingService, AlertService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

export function httpTranslateLoader(http: HttpClient): any {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}
