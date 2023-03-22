import {Component, OnInit} from '@angular/core';
import {User} from "../../models/user";

@Component({
  selector: 'app-user-creation',
  templateUrl: './user-creation.component.html',
  styleUrls: ['./user-creation.component.css']
})
export class UserCreationComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }

  setUser(user: User) {

  }
}
