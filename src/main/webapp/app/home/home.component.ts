import { Component, OnInit } from '@angular/core';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService, AccountService, Account, LoginService, StateStorageService } from 'app/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
  authenticationError: boolean;
  account: Account;
  loginForm = this.fb.group({
    username: [''],
    password: [''],
    rememberMe: [true]
  });
  view: any[] = [700, 400];
  options: Object;

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private eventManager: JhiEventManager,
    private fb: FormBuilder,
    private router: Router,
    private loginService: LoginService,
    private stateStorageService: StateStorageService
  ) {}

  ngOnInit() {
    this.accountService.identity().then((account: Account) => {
      this.account = account;
    });
    this.registerAuthenticationSuccess();
  }

  onSelect(event) {
    console.log(event);
  }

  registerAuthenticationSuccess() {
    this.eventManager.subscribe('authenticationSuccess', message => {
      this.accountService.identity().then(account => {
        this.account = account;

        // if(this.accountService.isAuthenticated()) {
        //   alert('ok');
        // } else {
        //   alert('não autentic');
        // }
      });
    });
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.loginService
      .login({
        username: this.loginForm.get('username').value,
        password: this.loginForm.get('password').value,
        rememberMe: this.loginForm.get('rememberMe').value
      })
      .then(() => {
        this.authenticationError = false;
        this.router.navigate(['/domicilio']);
        this.eventManager.broadcast({
          name: 'authenticationSuccess',
          content: 'Sending Authentication Success'
        });
        const redirect = this.stateStorageService.getUrl();
        if (redirect) {
          this.stateStorageService.storeUrl(null);
          this.router.navigateByUrl(redirect);
        }
      })
      .catch(() => {
        this.authenticationError = true;
      });
  }

  requestResetPassword() {
    this.router.navigate(['/reset', 'request']);
  }

  register() {
    this.router.navigate(['/register']);
  }
}
