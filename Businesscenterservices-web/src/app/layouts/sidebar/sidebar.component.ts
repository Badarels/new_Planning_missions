import { CommonModule } from '@angular/common';
import { Component, HostListener } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { SharedModule } from 'src/app/shared/shared.module';

@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.css'],
    standalone: true,
       imports: [
        CommonModule,
        RouterModule,
        SharedModule,
        NgbDropdownModule,
],
})
export class SidebarComponent {
  isMobile = window.innerWidth < 1200;
  isCollapsed = false;

  @HostListener('window:resize', ['$event'])
  onResize() {
    this.isMobile = window.innerWidth < 1200;
    if (!this.isMobile) {
      this.isCollapsed = false;
    }
  }

  toggle() {
    if (this.isMobile) {
      const sidebar = document.querySelector('.sidebar') as HTMLElement;
      sidebar.classList.toggle('show');
      this.isCollapsed = !this.isCollapsed;
    }
  }
}
