import { Component, inject } from '@angular/core';
import { RegisterService } from '../../../services/register';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-notice-register',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './notice-register.html',
  styleUrl: './notice-register.css',
})
export class NoticeRegister {

  private fb = inject(FormBuilder);
  private router = inject(Router);
  private adminService = inject(RegisterService);

  noticeForm!: FormGroup;
  isSubmitting = false;

  ngOnInit(): void {
    this.noticeForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(3)]],
      content: ['', [Validators.required, Validators.minLength(10)]]
    });
  }

  onSubmit(): void {
    if (this.noticeForm.invalid) {
      this.noticeForm.markAllAsTouched();
      Swal.fire('Campos incompletos', 'Por favor completa todos los campos.', 'warning');
      return;
    }

    this.isSubmitting = true;

    const request = {
      title: this.noticeForm.value.title,
      content: this.noticeForm.value.content
    };

    this.adminService.createNotice(request).subscribe({
      next: (resp) => {
        this.isSubmitting = false;

        Swal.fire(
          '¡Creado!',
          'El aviso fue publicado correctamente.',
          'success'
        );

        this.router.navigate(['/listar-noticias']);
      },
      error: (err) => {
        this.isSubmitting = false;

        Swal.fire(
          'Error',
          err.error?.message || 'Hubo un error creando el aviso.',
          'error'
        );
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/listar-noticias']);
  }

}
