import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CurrentUserData} from "../modules/authorization/model/current-user-data";
import {RoleEnum} from "../modules/authorization/enums/role-enum";
import {TokenStorageService} from "./token-storage.service";
import {AppSettings} from "../helpers/app-settings";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class AuthService {

  protected path = '/auth';

  constructor(private http: HttpClient, private tokenStorageService: TokenStorageService) {
  }

  login(username: string, password: string, code: string): Observable<any> {
    return this.http.post(AppSettings.API_ENDPOINT + this.path + '/login', {
      username,
      password,
      code
    }, httpOptions);
  }

  getUser(): CurrentUserData {
    return this.tokenStorageService.getUser();
  }

  logout(): void {
    return this.tokenStorageService.cleanStorage();
  }

  isLoggedIn(): boolean {
    return this.tokenStorageService.getToken() !== null;
  }

  getName(): string {
    const user = this.getUser();
    return user.name;
  }

  getUsername(): string {
    const user = this.getUser();
    return user.username;
  }

  getRole(): number {
    const user = this.getUser();
    return user.roleId;
  }


  isAdmin() {
    const role = this.getRole();
    return role === RoleEnum.ADMINISTRATOR;
  }

  isClient() {
    const role = this.getRole();
    return role === RoleEnum.CLIENT;
  }

  isAdminOrClient() {
    const role = this.getRole();
    return (role === RoleEnum.ADMINISTRATOR) || (role === RoleEnum.CLIENT);
  }

}
