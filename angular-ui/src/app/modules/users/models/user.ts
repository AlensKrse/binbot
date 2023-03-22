export class User {

  private _id: number;
  private _name: string;
  private _username: string;
  private _roleId: number;
  private _role: string;
  private _password: string;
  private _active: boolean;
  private _accountNonLocked: boolean;
  private _qrCodeEnabled: boolean;


  constructor(id: number, name: string, username: string, roleId: number, role: string, password: string, active: boolean, accountNonLocked: boolean, qrCodeEnabled: boolean) {
    this._id = id;
    this._name = name;
    this._username = username;
    this._roleId = roleId;
    this._role = role;
    this._password = password;
    this._active = active;
    this._accountNonLocked = accountNonLocked;
    this._qrCodeEnabled = qrCodeEnabled;
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

  get roleId(): number {
    return this._roleId;
  }

  set roleId(value: number) {
    this._roleId = value;
  }

  get role(): string {
    return this._role;
  }

  set role(value: string) {
    this._role = value;
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

  get accountNonLocked(): boolean {
    return this._accountNonLocked;
  }

  set accountNonLocked(value: boolean) {
    this._accountNonLocked = value;
  }

  get qrCodeEnabled(): boolean {
    return this._qrCodeEnabled;
  }

  set qrCodeEnabled(value: boolean) {
    this._qrCodeEnabled = value;
  }
}
