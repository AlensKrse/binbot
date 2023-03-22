import {Directive, ElementRef, Input} from '@angular/core';
import {RoleEnum} from "../enums/role-enum";
import {AuthService} from "../../../services/auth.service";
import * as $ from 'jquery';

@Directive({
  selector: '[auth]'
})
export class AuthDirective {

  @Input() auth: string = '';

  private authRoles: string[] = [];

  constructor(private elementRef: ElementRef, private authService: AuthService) {
  }

  ngOnInit() {
    $(this.elementRef.nativeElement).addClass('auth-hidden');

    this.authRoles = this.auth.split(',');

    for (const authRole of this.authRoles) {
      let role = null;
      switch (authRole.replace(' ', '')) {
        case 'administrator':
          role = RoleEnum.ADMINISTRATOR;
          break;
        case 'client':
          role = RoleEnum.CLIENT;
          break;
      }

      if (this.authService.getRole() === role) {
        $(this.elementRef.nativeElement).removeClass('auth-hidden').addClass('auth-visible');
        return;
      }
    }
  }
}
