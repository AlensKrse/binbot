import {Injectable} from '@angular/core';
import Swal from 'sweetalert2'
import {TranslateService} from '@ngx-translate/core';

@Injectable()
export class AlertService {

  constructor(private translateService: TranslateService) {
  }

  info(resourceKey: string, resourceStringParams?: Object) {
    this.translateService.get(resourceKey, resourceStringParams).subscribe((res: string) => {
      Swal.fire(res);
    });
  }

  error(resourceKey: string, resourceStringParams?: Object) {
    this.translateService.get(resourceKey, resourceStringParams).subscribe((res: string) => {
      Swal.fire(res, '', 'error');
    });
  }

  confirm(callback: () => void, resourceKey: string, resourceStringParams?: Object) {
    this.translateService.get([resourceKey, "general.confirmations.yes", "general.confirmations.no"], resourceStringParams).subscribe((result: any) => {
      Swal.fire({
        text: result[resourceKey],
        showCancelButton: true,
        confirmButtonColor: '#026DB7',
        cancelButtonColor: '#ff5e57',
        confirmButtonText: result["general.confirmations.yes"],
        cancelButtonText: result["general.confirmations.no"]
      }).then((result) => {
        if (result.value) {
          callback();
        }
      });
    });
  }

  public genericError() {
    this.translateService.get('general.generic_error').subscribe((res: string) => {
      Swal.fire(res, '', 'error');
    });
  }
}
