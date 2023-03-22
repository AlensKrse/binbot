import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {NavigationPath} from "../../helpers/navigation-path";

@Component({
  selector: 'app-portal-layout',
  templateUrl: './portal-layout.component.html',
  styleUrls: ['./portal-layout.component.css']
})
export class PortalLayoutComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit() {
    this.router.navigateByUrl(NavigationPath.DASHBOARD);
  }

}
