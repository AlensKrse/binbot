import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-google-code-dialog',
  templateUrl: './google-code-dialog.component.html',
  styleUrls: ['./google-code-dialog.component.css']
})
export class GoogleCodeDialogComponent implements OnInit {

  codeCreated: boolean;
  otpAutUrl: string;
  qrCode: any;

  constructor(@Inject(MAT_DIALOG_DATA) private data, private dialogRef: MatDialogRef<GoogleCodeDialogComponent>) {
    this.codeCreated = JSON.parse(data.codeCreated);
    this.otpAutUrl = data.otpAutUrl;
  }

  ngOnInit() {
    if (this.codeCreated) {
      this.generateQrCode();
    }
  }

  private generateQrCode() {
    this.qrCode = this.otpAutUrl;
  }

  onCancelClick(): void {
    this.dialogRef.close(null);
  }

  onCodeCompleted(code: string) {
    this.dialogRef.close(code);
  }

}
