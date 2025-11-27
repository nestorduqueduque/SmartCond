import { Component,
  OnInit,
  ChangeDetectorRef,
  ViewChild,
  inject } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import { Celador } from '../../../services/celador';
import { CeladorDashboardDTO, Notice } from './celador-dashboard.interface';
import { Auth } from '../../../services/auth';

@Component({
  selector: 'app-celador-dashboard',
  imports: [CommonModule, DatePipe, RouterLink, FormsModule],
  templateUrl: './celador-dashboard.html',
  //styleUrl: './celador-dashboard.css',
})
export class CeladorDashboard {
      private dashboardService = inject(Celador);
      public authService = inject(Auth);
      private cdr = inject(ChangeDetectorRef);

      
      celadorName: string = '';
      latestNotices: Notice[] = [];

      isLoading = true;
      isSubmitting = false;
      error: string | null = null;
      noticeError: string | null = null;
      noticeSuccess: string | null = null;

      ngOnInit(): void {
      this.loadDashboardData();
      }
        private loadDashboardData(): void {
          this.isLoading = true;
          this.error = null;

          this.dashboardService.getCeladorDashboardData().subscribe({
            next: (data: CeladorDashboardDTO) => {
              this.celadorName = data.celadorName;
              this.latestNotices = data.latestNotices;
              this.isLoading = false;
              this.cdr.markForCheck();
            },
            error: (err) => {
              console.error('Error cargando dashboard:', err);
              this.isLoading = false;
              this.cdr.markForCheck();
            }
          });
        }
}
