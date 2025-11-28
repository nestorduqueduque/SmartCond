import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RegisterService } from '../../../services/register';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-notice-update',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './notice-update.html',
  styleUrl: './notice-update.css',
})
export class NoticeUpdate {
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private adminService = inject(RegisterService);
  private cdr = inject(ChangeDetectorRef);

  noticeForm!: FormGroup;
  noticeId!: number;

  isSubmitting = false;
  isLoadingData = true;

  ngOnInit(): void {
    this.noticeForm = this.fb.group({
      title: ['', Validators.required],
      content: ['', Validators.required],
    });

    this.route.paramMap.subscribe(params => {
      const id = params.get('id');

      if (!id) {
        Swal.fire('Error', 'No se encontró el ID del aviso.', 'error');
        this.router.navigate(['/listar-noticias']);
        return;
      }

      this.noticeId = +id;
      this.loadNoticeData();
    });
  }

  loadNoticeData() {
    this.isLoadingData = true;

    this.adminService.getNoticeById(this.noticeId).subscribe({
      next: (data) => {

        this.noticeForm.patchValue({
          title: data.title,
          content: data.content,
        });

        this.isLoadingData = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error cargando aviso:', err);
        Swal.fire('Error', 'No se pudo cargar la información.', 'error');
        this.router.navigate(['/listar-noticias']);
      }
    });
  }

  onSubmit() {
    if (this.noticeForm.invalid) {
      this.noticeForm.markAllAsTouched();
      Swal.fire('Campos faltantes', 'Revisa el formulario.', 'warning');
      return;
    }

    this.isSubmitting = true;

    const request = {
      title: this.noticeForm.value.title,
      content: this.noticeForm.value.content,
    };

    this.adminService.updateNotice(this.noticeId, request).subscribe({
      next: (resp) => {
        this.isSubmitting = false;

        Swal.fire(
          '¡Actualizado!',
          `El aviso fue actualizado correctamente.`,
          'success'
        );

        this.router.navigate(['/listar-noticias']);
      },
      error: (err) => {
        this.isSubmitting = false;

        Swal.fire('Error', err.error?.message || 'No se pudo actualizar el aviso', 'error');
      }
    });
  }

  goBack() {
    this.router.navigate(['/listar-noticias']);
  }


}
