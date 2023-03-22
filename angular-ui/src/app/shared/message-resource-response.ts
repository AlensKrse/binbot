export class MessageResourceResponse<DATA_TYPE> {

  private _success: boolean;
  private _messageResource: string;
  private _errorMessage: string;
  private _messageResourceParameters: Map<string, string>;
  private _body: DATA_TYPE;


  constructor(success: boolean, messageResource: string, errorMessage: string, messageResourceParameters: Map<string, string>, body: DATA_TYPE) {
    this._success = success;
    this._messageResource = messageResource;
    this._errorMessage = errorMessage;
    this._messageResourceParameters = messageResourceParameters;
    this._body = body;
  }


  get success(): boolean {
    return this._success;
  }

  set success(value: boolean) {
    this._success = value;
  }

  get messageResource(): string {
    return this._messageResource;
  }

  set messageResource(value: string) {
    this._messageResource = value;
  }

  get errorMessage(): string {
    return this._errorMessage;
  }

  set errorMessage(value: string) {
    this._errorMessage = value;
  }

  get messageResourceParameters(): Map<string, string> {
    return this._messageResourceParameters;
  }

  set messageResourceParameters(value: Map<string, string>) {
    this._messageResourceParameters = value;
  }

  get body(): DATA_TYPE {
    return this._body;
  }

  set body(value: DATA_TYPE) {
    this._body = value;
  }
}
