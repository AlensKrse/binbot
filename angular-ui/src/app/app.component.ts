import {Component} from '@angular/core';
import {TokenStorageService} from './services/token-storage.service';
import {TranslateService} from "@ngx-translate/core";
import {AppSettings} from "./helpers/app-settings";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private tokenStorageService: TokenStorageService, private translate: TranslateService) {
    translate.setDefaultLang(AppSettings.LANGUAGE);
    translate.use(AppSettings.LANGUAGE);
  }

  ngOnInit(): void {
  }

  logout(): void {
    this.tokenStorageService.cleanStorage();
    window.location.reload();
  }
}
