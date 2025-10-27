import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'ngx-bootstrap/datepicker/bs-datepicker.css';
import '@angular/material/prebuilt-themes/indigo-pink.css';
import 'ngx-toastr/toastr.css';

import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config';

bootstrapApplication(AppComponent, appConfig)
  .catch(err => console.error(err));
