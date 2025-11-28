import { CommonModule, NgFor, NgIf } from '@angular/common';
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { RegisterService } from '../../../services/register';
import { Router, RouterLink } from '@angular/router';
import Swal from 'sweetalert2';
import { FormsModule, NgForm } from '@angular/forms';
import { Notice } from '../../dashboard/admin-dashboard/admin-dashboard.interface';

@Component({
  selector: 'app-notice-list',
  imports: [CommonModule, RouterLink, FormsModule, NgFor, NgIf],
  templateUrl: './notice-list.html',
  styleUrl: './notice-list.css',
})
export class NoticeList {

  private noticeService = inject(RegisterService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  notices: Notice[] = [];
  filteredNotices: Notice[] = [];

  searchTerm: string = "";
  isLoading: boolean = true;

  ngOnInit() {
    this.loadNotices();
  }

  loadNotices(): void {
    this.isLoading = true;

    this.noticeService.getAllNotices().subscribe({
      next: (resp) => {
        this.notices = resp;
        this.filteredNotices = resp;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        console.error("Error al cargar los avisos");
        this.isLoading = false;
      }
    });
  }

  filterNotices() {
    const term = this.searchTerm.toLowerCase().trim();

    this.filteredNotices = this.notices.filter(n =>
      n.title.toLowerCase().includes(term) ||
      n.content.toLowerCase().includes(term) ||
      n.authorName.toLowerCase().includes(term)
    );
  }

  onEdit(id: number): void {
    this.router.navigate(['/editar-noticias', id]);
  }

  onDelete(id: number): void {
    Swal.fire({
      title: '¿Eliminar aviso?',
      text: "Esta acción es permanente.",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((res) => {
      if (res.isConfirmed) {
        this.executeDelete(id);
      }
    });
  }

  private executeDelete(id: number): void {
    this.noticeService.deleteNotice(id).subscribe({
      next: () => {
        Swal.fire("Eliminado", "El aviso ha sido eliminado.", "success");
        this.loadNotices();
      },
      error: () => {
        Swal.fire("Error", "No se pudo eliminar el aviso.", "error");
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/admin-dashboard']);
  }

  }



