export class AppSettings {
  public static API_ENDPOINT = 'http://localhost:8443/api';
  public static LANGUAGE = 'en';

}

export function isNullOrUndefined(value: any) {
  return value === null || value === undefined;
}
