export class ChangePasswordData {
  private _currentPassword: string;
  private _password: string;


  constructor(currentPassword: string, rawPassword: string) {
    this._currentPassword = currentPassword;
    this._password = rawPassword;
  }


  get currentPassword(): string {
    return this._currentPassword;
  }

  set currentPassword(value: string) {
    this._currentPassword = value;
  }

  get password(): string {
    return this._password;
  }

  set password(value: string) {
    this._password = value;
  }
}
