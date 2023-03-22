import {NgModule} from "@angular/core";
import {AppComponent} from "./app.component";
import {PortalLayoutComponent} from "./components/portal-layout/portal-layout.component";
import {HeaderComponent} from "./components/header/header.component";
import {PageLoadingIndicatorComponent} from "./components/page-loading-indicator/page-loading-indicator.component";
import {BrowserModule} from "@angular/platform-browser";
import {AppRoutingModule} from "./app-routing.module";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AuthenticationModule} from "./modules/authorization/authentication.module";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {authInterceptorProviders} from "./helpers/auth.interceptor";
import {DashboardModule} from "./modules/dashboard/dashboard.module";

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

    AuthenticationModule,
    DashboardModule,
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
