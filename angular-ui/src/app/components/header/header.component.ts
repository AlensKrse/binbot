import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  name: string = '';

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    this.name = this.authService.getName();
  }

  onLogout() {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }

  openChangePasswordDialog() {

  }
}
