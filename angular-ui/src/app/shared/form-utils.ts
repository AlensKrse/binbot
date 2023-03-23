import {AbstractControl, FormArray, FormControl, FormGroup, ValidationErrors} from '@angular/forms';

export class FormUtils {

  public static validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({onlySelf: true});

      } else if (control instanceof FormGroup) {
        control.updateValueAndValidity();
        this.validateAllFormFields(control);

      } else if (control instanceof FormArray) {
        for (const item of control.controls) {
          if (item instanceof FormGroup) {
            item.updateValueAndValidity();
            this.validateAllFormFields(item);

          }
        }
      }
    });
  }

  public static getFormValidationErrors(control: AbstractControl): ValidationErrors[] {
    const errors: ValidationErrors[] = [];
    const controlErrors: ValidationErrors = control.errors!;
    if (controlErrors !== null) {
      Object.keys(controlErrors).forEach(keyError => {
        errors.push({
          error_name: keyError,
          error_value: controlErrors[keyError]
        });
      });
    }

    return errors;
  }


  public static debugFormValidationErrors(form: FormGroup) {
    Object.keys(form.controls).forEach(key => {

      const controlErrors: ValidationErrors = form.get(key)!.errors!;
      if (controlErrors != null) {
        Object.keys(controlErrors).forEach(keyError => {
          console.log('Key control: ' + key + ', keyError: ' + keyError + ', err value: ', controlErrors[keyError]);
        });
      }
    });
  }

  static isFormValid(form: FormGroup): boolean {
    FormUtils.validateAllFormFields(form);
    let formIsValid = true;

    Object.keys(form.controls).forEach(field => {
      const control = form.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({onlySelf: true});
        if (control.invalid) {
          formIsValid = false;
        }
      } else if (control instanceof FormGroup) {
        control.updateValueAndValidity();
        return this.isFormValid(control);

      } else if (control instanceof FormArray) {
        for (const item of control.controls) {
          if (item instanceof FormGroup) {
            if (!this.isFormValid(item)) {
              formIsValid = false;
            }
          }
        }
      }
    });

    return formIsValid;
  }

  public static resetForm(formGroup: FormGroup) {
    formGroup.reset();
    Object.keys(formGroup.controls).forEach(key => {
      formGroup.get(key)!.setErrors(null);
    });
  }
}
