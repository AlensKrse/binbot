import {Component} from '@angular/core';
import {TokenStorageService} from './services/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
  }

  logout(): void {
    this.tokenStorageService.cleanStorage();
    window.location.reload();
  }
}
