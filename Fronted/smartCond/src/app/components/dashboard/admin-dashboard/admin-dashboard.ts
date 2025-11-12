import {
  Component,
  OnInit,
  ChangeDetectorRef,
  ViewChild,
  inject
} from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import { DashboardService } from './admin-dashboard.service';
import { AdminDashboardDTO, Notice, NoticeRequestDTO } from './admin-dashboard.interface';
import { Auth } from '../../../services/auth';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, DatePipe, RouterLink, FormsModule],
  templateUrl: './admin-dashboard.html',
  // styleUrls: ['./admin-dashboard.css'],
})
export class AdminDashboard implements OnInit {
  private dashboardService = inject(DashboardService);
  public authService = inject(Auth);
  private cdr = inject(ChangeDetectorRef);

  @ViewChild('noticeForm') noticeForm!: NgForm;

  // 🧭 Propiedades del dashboard
  adminName: string = '';
  latestNotices: Notice[] = [];
  newNotice: NoticeRequestDTO = { title: '', content: '' };

  // 🔁 Estados UI
  isLoading = true;
  isSubmitting = false;
  error: string | null = null;
  noticeError: string | null = null;
  noticeSuccess: string | null = null;

  ngOnInit(): void {
    this.loadDashboardData();
  }

  /**
   * 🔹 Carga inicial del dashboard
   */
  private loadDashboardData(): void {
    this.isLoading = true;
    this.error = null;

    this.dashboardService.getAdminDashboardData().subscribe({
      next: (data: AdminDashboardDTO) => {
        this.adminName = data.adminName;
        this.latestNotices = data.latestNotices;
        this.isLoading = false;
        this.cdr.markForCheck(); // más eficiente que detectChanges
      },
      error: (err) => {
        console.error('Error cargando dashboard:', err);
        this.isLoading = false;
        this.cdr.markForCheck();
      }
    });
  }

  /**
   * 📨 Envía un nuevo comunicado
   */
  onSubmitNotice(): void {
    if (this.noticeForm.invalid || this.isSubmitting) return;

    this.isSubmitting = true;
    this.noticeError = null;
    this.noticeSuccess = null;

    this.dashboardService.createNotice(this.newNotice).subscribe({
      next: () => {
        this.noticeSuccess = 'Comunicado publicado con éxito.';
        this.newNotice = { title: '', content: '' };
        this.noticeForm.resetForm(this.newNotice);
        this.isSubmitting = false;

        // 🔄 Recarga lista de comunicados
        this.loadDashboardData();

        // 🔔 Oculta mensaje después de unos segundos
        setTimeout(() => (this.noticeSuccess = null), 3000);
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error en createNotice:', err);
        this.noticeError = 'Error al publicar el comunicado.';
        this.isSubmitting = false;
        this.cdr.markForCheck();
      },
    });
  }
}
