import {Component, NgZone, OnInit, ViewChild} from '@angular/core';
import {isNullOrUndefined} from "../../../../helpers/app-settings";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {AuthService} from "../../../../services/auth.service";
import {FormBuilder} from "@angular/forms";
import {MatDialog} from "@angular/material/dialog";
import {User} from "../../models/user";
import {UserService} from "../../services/user.service";
import {AlertService} from "../../../../services/alert.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {


  users!: User[];

  userDataSource!: MatTableDataSource<User>;

  userDisplayedColumns: string[] = ['id', 'name', 'username', 'active', 'accountNonLocked'];

  @ViewChild('userPaginator') userPaginator!: MatPaginator;
  @ViewChild('userSort') userSort!: MatSort;


  constructor(private zone: NgZone, private formBuilder: FormBuilder, private authService: AuthService, private userService: UserService,
              private dialog: MatDialog, private alertService: AlertService, private router: Router) {
  }

  ngOnInit(): void {
    if (this.isAdmin()) {
      this.userService.getUsers().then(result => {
        if (!isNullOrUndefined(result) && result.success) {
          this.userDataSource = new MatTableDataSource(result.body);
          this.userDataSource.paginator = this.userPaginator;
          this.userDataSource.sort = this.userSort;
        } else {
          this.alertService.error("Error during user list loading")
        }
      });
    }
  }

  applyUserFilter() {
    const filterValue = (event!.target as HTMLInputElement).value;
    this.userDataSource.filter = filterValue.trim().toLowerCase();

    if (this.userDataSource.paginator) {
      this.userDataSource.paginator.firstPage();
    }
  }

  createUser() {
    this.router.navigate(['/users/create']);

  }

  openDetails(user) {
    this.zone.run(() => {
      this.router.navigate(['/users/' + user.id]);
    });
  }

  isAdmin() {
    return this.authService.isAdmin();
  }
}
