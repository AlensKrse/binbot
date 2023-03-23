import {Validators} from '@angular/forms';

export class PasswordValidator {
  static NON_REQUIRED = [Validators.minLength(8), Validators.maxLength(40), Validators.pattern(/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)/)];
  static REQUIRED = [Validators.required, Validators.minLength(8), Validators.maxLength(40), Validators.pattern(/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)/)];
}
