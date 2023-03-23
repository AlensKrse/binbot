import {Component, NgZone, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  name: string = '';

  constructor(private authService: AuthService, private router: Router, private zone: NgZone) {
  }

  ngOnInit(): void {
    this.name = this.authService.getName();
  }

  onLogout() {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }


  logout() {
    if (confirm("Are you sure you want to logout?")) {
      this.authService.logout();
      this.router.navigateByUrl('/login');
    }
  }

  navigateToMyUser() {
    const user = this.authService.getUser();
    this.zone.run(() => {
      this.router.navigate(['/users/' + user.id]);
    });
  }

  isAdminOrClient() {
    return this.authService.isAdminOrClient();
  }

  isAdmin() {
    return this.authService.isAdmin();
  }
}
