import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
    selector: 'app-root',
    template: '<router-outlet></router-outlet>',
    styleUrls: ['./app.component.css'],
    standalone: false
})
export class AppComponent{
  title = 'Businesscenterservice-web';
}
