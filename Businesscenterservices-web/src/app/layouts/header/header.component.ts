import { Component } from '@angular/core';
import { AuthServices } from '../../authentification/Services/auth.services';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedModule } from 'src/app/shared/shared.module';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css'],
    standalone: true,
     imports: [
        CommonModule,
        RouterModule,
        SharedModule,
        NgbDropdownModule,  
    ],
})
export class HeaderComponent {
  constructor(private authService: AuthServices) { }

  toggleSidebar() {
    // Revenir au comportement d'origine: simplement ouvrir/fermer le panneau
    const sidebar = document.querySelector('.sidebar') as HTMLElement;
    if (!sidebar) return;
    sidebar.classList.toggle('show');
  }

  logout() {
    this.authService.logout();
  }
}
