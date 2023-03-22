export class CurrentUserData {
  private _id: number;
  private _name: string;
  private _username: string;
  private _password: string;
  private _active: boolean;
  private _qrCodeEnabled: boolean;
  private _roleId: number;

  constructor(id: number, name: string, username: string, password: string, active: boolean, qrCodeEnabled: boolean, roleId: number) {
    this._id = id;
    this._name = name;
    this._username = username;
    this._password = password;
    this._active = active;
    this._qrCodeEnabled = qrCodeEnabled;
    this._roleId = roleId;
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get username(): string {
    return this._username;
  }

  set username(value: string) {
    this._username = value;
  }

  get password(): string {
    return this._password;
  }

  set password(value: string) {
    this._password = value;
  }

  get active(): boolean {
    return this._active;
  }

  set active(value: boolean) {
    this._active = value;
  }

  get qrCodeEnabled(): boolean {
    return this._qrCodeEnabled;
  }

  set qrCodeEnabled(value: boolean) {
    this._qrCodeEnabled = value;
  }

  get roleId(): number {
    return this._roleId;
  }

  set roleId(value: number) {
    this._roleId = value;
  }
}
