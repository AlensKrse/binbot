import {Component, Input, OnDestroy, OnInit} from '@angular/core';

@Component({
  selector: 'app-page-loading-indicator',
  templateUrl: './page-loading-indicator.component.html',
  styleUrls: ['./page-loading-indicator.component.css'],
})
export class PageLoadingIndicatorComponent implements OnInit, OnDestroy {

  @Input() private isLoadingInProgress = false;

  ngOnInit(): void {
  }

  ngOnDestroy() {
  }

  isPageLoading(): boolean {
    return this.isLoadingInProgress;
  }

}
