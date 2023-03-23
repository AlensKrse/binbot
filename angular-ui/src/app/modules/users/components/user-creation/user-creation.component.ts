import {Component, OnInit} from '@angular/core';
import {User} from "../../models/user";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {RoleEnum} from "../../../authorization/enums/role-enum";
import {AlertService} from "../../../../services/alert.service";
import {NotificationService} from "../../../../helpers/notification.service";
import {AuthService} from "../../../../services/auth.service";
import {PasswordValidator} from "../../../../validators/password-validator";
import {FormUtils} from "../../../../shared/form-utils";

@Component({
  selector: 'app-user-creation',
  templateUrl: './user-creation.component.html',
  styleUrls: ['./user-creation.component.css']
})
export class UserCreationComponent implements OnInit {

  form: FormGroup;

  userId: number;

  adminOrClientRoles = [
    {id: RoleEnum.ADMINISTRATOR, name: 'users.roles.1'},
    {id: RoleEnum.CLIENT, name: 'users.roles.2'},
  ];

  private requestInProgress = false;


  constructor(private formBuilder: FormBuilder, private userService: UserService, private route: ActivatedRoute,
              private router: Router, private alertService: AlertService,
              private notificationService: NotificationService,
              private authService: AuthService) {
    this.userId = this.route.snapshot.params.userId || null;

    let passwordValidator;
    if (this.userId !== null) {
      passwordValidator = PasswordValidator.NON_REQUIRED;
    } else {
      passwordValidator = PasswordValidator.REQUIRED;
    }

    this.form = formBuilder.group({
      username: ['', Validators.compose([Validators.required])],
      name: ['', Validators.compose([Validators.required])],
      password: ['', Validators.compose(passwordValidator)],
      confirmPassword: ['', Validators.compose(passwordValidator)],
      active: [false],
      accountNonLocked: [false],
      roleId: ['', Validators.compose([Validators.required])],
    });

    if (this.userId !== null) {
      this.form.get('username')!.disable();
      if (this.authService.isClient()) {
        this.form.get('name')!.disable();
      }
    }

    this.loadUserData();
  }

  ngOnInit() {
  }

  passwordConfirmed(): boolean {
    return this.form.get('password')!.value === this.form.get('confirmPassword')!.value;
  }

  getRequestInProgress(): boolean {
    return this.requestInProgress;
  }

  submit() {
    FormUtils.validateAllFormFields(this.form);

    if (this.form.valid) {
      this.requestInProgress = true;
      const user: User = this.form.getRawValue() as User;

      if (this.userId === null) {
        this.create(user);
      } else {
        this.update(user);
      }

    }
  }

  backToList() {
    this.router.navigate(['/users']);
  }

  private create(user: User) {
    this.userService.create(user).then(result => {
      if (result.success) {
        this.router.navigate(['/users/' + result.body.id]);
        this.notificationService.success('User has been created with ID: ' + result.body.id);
      } else {
        this.alertService.error(result.errorMessage);
      }

      this.requestInProgress = false;
    }, () => {
      this.alertService.genericError();
      this.requestInProgress = false;
    });
  }

  private update(user: User) {
    this.userService.update(this.userId, user).then(result => {
      if (result.success) {
        this.notificationService.success('User has been updated.');
        this.backToList();
      } else {
        this.alertService.error(result.errorMessage);
      }

      this.requestInProgress = false;
    }, () => {
      this.alertService.genericError();
      this.requestInProgress = false;
    });
  }

  private loadUserData() {
    if (this.userId !== null) {
      this.userService.getUser(this.userId).then(result => {
        if (result.success) {
          const user = result.body;
          this.form.setValue({
            username: user.username,
            name: user.name,
            active: user.active,
            accountNonLocked: user.accountNonLocked,
            password: '',
            confirmPassword: '',
            roleId: user.roleId
          });

        } else {
          this.alertService.error(result.errorMessage);
        }
      }, () => {
        this.alertService.genericError();
      });
    }
  }

  isAdmin() {
    return this.authService.isAdmin();
  }

  getRoles() {
    return this.adminOrClientRoles;
  }

  regenerateQrCode() {
    if (confirm("Are you sure to regenerate QR code for this user?")) {
      this.userService.regenerateQrCode(this.userId).then(result => {
        if (result) {
          this.notificationService.info('Qr code regenerated.')
          this.ngOnInit();
        } else {
          this.alertService.error('Unable to regenerate qr code.');
        }
      }, () => {
        this.alertService.genericError();
      });
    }
  }


}
