import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../../../services/auth.service';
import {TokenStorageService} from '../../../../services/token-storage.service';
import {Router} from "@angular/router";
import {NavigationPath} from "../../../../helpers/navigation-path";
import {UserService} from "../../../users/services/user.service";
import {MatDialog} from "@angular/material/dialog";
import {GoogleCodeDialogComponent} from "../google-code-dialog/google-code-dialog.component";
import {AlertService} from "../../../../services/alert.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {
    username: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private userService: UserService,
              private router: Router, private matDialog: MatDialog, private alertService: AlertService) {
  }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.navigateToDashboard();
    }
  }

  onSubmit(): void {
    const {username, password} = this.form;

    this.authService.login(username, password, '').subscribe(
      result => {
        if (result.success) {
          this.isLoginFailed = true;
          return;
        }
        this.matDialog.open(GoogleCodeDialogComponent, {
          width: '400px',
          data: {
            codeCreated: result.qrCodeGenerated,
            otpAutUrl: result.otpAuthURL
          },
        }).afterClosed().subscribe(code => {
          this.authService.login(username, password, code).subscribe(
            finalResult => {
              if (finalResult.success) {
                this.tokenStorage.saveToken(finalResult.token);
                this.tokenStorage.saveUser(finalResult);

                this.isLoginFailed = false;
                this.isLoggedIn = true;
                this.userService.resetUserLoginDataAfterSuccessfulAuth();
                this.navigateToDashboard();
              } else {
                this.showError('Invalid MFA code');
              }
            }, () => {
              this.showError('Invalid MFA code');
            });
        })
      },
      () => {
        this.showError('Invalid Credentials');
      }
    );
  }

  private showError(message: string) {
    this.alertService.error(message);
    this.isLoginFailed = true;
  }

  private navigateToDashboard() {
    this.router.navigateByUrl(NavigationPath.DASHBOARD);
  }
}
