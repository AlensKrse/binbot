import {Injectable} from '@angular/core';
import {HttpHeaders} from '@angular/common/http';
import {HttpClientService} from "./http-client.service";
import {TokenStorageService} from "./token-storage.service";

@Injectable()
export class SecureHttpClientService {

  constructor(private httpClient: HttpClientService, private tokenStorageService: TokenStorageService) {
  }

  private createAuthHeader() {
    const headers = new HttpHeaders({
      Authorization: 'Bearer ' + this.tokenStorageService.getToken(),
    });

    return {
      headers
    };
  }

  public get(url: string): any {
    return this.httpClient.get(url, this.createAuthHeader());
  }

  public post(url: string, body: any): any {
    return this.httpClient.post(url, body, this.createAuthHeader());
  }

  public put(url: string, body?: any, absoluteUrl?: boolean): any {
    return this.httpClient.put(url, body, this.createAuthHeader(), absoluteUrl);
  }

  public delete(url: string): any {
    return this.httpClient.delete(url, this.createAuthHeader());
  }

  public getBinary(url: string): Promise<ArrayBuffer> {
    return this.httpClient.getBinary(url, this.createAuthHeader());
  }

  public getApiUrl(url: string): string {
    return this.httpClient.getApiUrl(url);
  }
}
