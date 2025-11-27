import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { ResidentDashboardDTO, ResidentNotice, ResidentPackage, ResidentVisitor } from './resident-dashboard.interface';
import { ResidentService } from '../../../services/resident-service';
import { Auth } from '../../../services/auth';

@Component({
  selector: 'app-resident-dashboard',
  imports: [CommonModule],
  templateUrl: './resident-dashboard.html',
  styleUrl: './resident-dashboard.css',
})
export class ResidentDashboard {
  private dashboardService = inject(ResidentService);
  public authService = inject(Auth);
  private cdr = inject(ChangeDetectorRef);

  dashboardData: ResidentDashboardDTO | null = null;
  isLoading = true;
  isSubmitting = false;
  error: string | null = null;
  noticeError: string | null = null;
  noticeSuccess: string | null = null;

  residentName: string = '';
  apartmentNumber: number | null = null;
  towerNumber: number | null = null;

  latestPackages: ResidentPackage[] = [];
  latestVisitors: ResidentVisitor[] = [];
  latestNotices: ResidentNotice[] = [];

  ngOnInit(): void {
    this.loadDashboardData();
  }

  private loadDashboardData(): void {
    this.isLoading = true;
    this.error = null;

    this.dashboardService.getResidentDashboardData().subscribe({
      next: (data: ResidentDashboardDTO) => {

        this.residentName = data.residentName;
        this.apartmentNumber = data.apartment.number;
        this.towerNumber = data.apartment.tower.number;

        
        this.latestPackages = data.latestPackages;
        this.latestVisitors = data.latestVisitors;
        this.latestNotices = data.latestNotices;

        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error cargando dashboard del Residente:', err);
        this.error = 'No se pudo cargar la información del dashboard.';
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

}
