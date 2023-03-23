import {Injectable} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {ToastrService} from 'ngx-toastr';

@Injectable()
export class NotificationService {

  constructor(private translate: TranslateService, private toastr: ToastrService) {
  }

  success(resourceKey: string, resourceStringParams?: Object) {
    this.translate.get(resourceKey, resourceStringParams).subscribe((res: string) => {
      this.toastr.success(res);
    });
  }

  info(resourceKey: string, resourceStringParams?: Object) {
    this.translate.get(resourceKey, resourceStringParams).subscribe((res: string) => {
      this.toastr.info(res);
    });
  }

  error(resourceKey: string, resourceStringParams?: Object) {
    this.translate.get(resourceKey, resourceStringParams).subscribe((res: string) => {
      this.toastr.error(res);
    });
  }
}
