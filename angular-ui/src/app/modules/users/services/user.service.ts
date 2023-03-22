import {Injectable} from '@angular/core';
import {SecureHttpClientService} from "../../../services/secure-http-client.service";
import {User} from "../models/user";
import {MessageResourceResponse} from "../../../shared/message-resource-response";
import {ChangePasswordData} from "../models/change-password-data";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  protected url = '/users';

  constructor(private secureHttpClient: SecureHttpClientService) {

  }

  getCurrentUser(): Promise<MessageResourceResponse<User>> {
    const url = this.url + '/current';
    return this.secureHttpClient.get(url);
  }

  getUser(userId: number): Promise<MessageResourceResponse<User>> {
    const url = this.url + '/' + userId;
    return this.secureHttpClient.get(url);
  }

  getUsers(): Promise<MessageResourceResponse<User[]>> {
    const url = this.url + '/list';
    return this.secureHttpClient.get(url);
  }

  create(user: User): Promise<MessageResourceResponse<User>> {
    return this.secureHttpClient.put(this.url, user);
  }

  update(userId: number, user: User): Promise<MessageResourceResponse<User>> {
    const url = this.url + '/' + userId;
    return this.secureHttpClient.post(url, user);
  }

  changePassword(changePasswordData: ChangePasswordData): Promise<MessageResourceResponse<null>> {
    const url = this.url + '/change-password';
    return this.secureHttpClient.post(url, changePasswordData);
  }

  resetUserLoginDataAfterSuccessfulAuth() {
    const url = this.url + '/login-data';
    return this.secureHttpClient.put(url);
  }

  isPasswordExpired(): Promise<MessageResourceResponse<boolean>> {
    const url = this.url + '/password-expire-check';
    return this.secureHttpClient.get(url);
  }

  regenerateQrCode(userId: number): Promise<MessageResourceResponse<boolean>> {
    const url = this.url + '/' + userId + '/regenerate-qr-code';
    return this.secureHttpClient.post(url, null);
  }


}
