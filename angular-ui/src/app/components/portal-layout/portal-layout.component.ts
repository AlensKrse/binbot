import {Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {AppSettings} from "../../helpers/app-settings";

@Component({
  selector: 'app-portal-layout',
  templateUrl: './portal-layout.component.html',
  styleUrls: ['./portal-layout.component.css']
})
export class PortalLayoutComponent implements OnInit {

  constructor(private translate: TranslateService) {
    translate.setDefaultLang(AppSettings.LANGUAGE);
  }

  ngOnInit() {
  }

}
