import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Celador } from '../../../services/celador';
import { Router } from '@angular/router';
import { PackageRequestDTO } from '../../dashboard/celador-dashboard/celador-dashboard.interface';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-package-register',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './package-register.html',
  //styleUrl: './package-register.css',
})
export class PackageRegister {
  private fb = inject(FormBuilder);
  private celadorService = inject(Celador);
  private router = inject(Router);

  isSubmitting = false;

    packageForm: FormGroup = this.fb.group({
    description: ['', Validators.required],
    apartment: ['', [Validators.required, Validators.pattern('^[0-9]*$')]]
  });

  onSubmit(): void {
    if (this.packageForm.invalid || this.isSubmitting) {
      this.packageForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    const formValue = this.packageForm.value;

    const request: PackageRequestDTO = {
      description: formValue.description,
      apartment: Number(formValue.apartment)
    };

    this.celadorService.createPackage(request).subscribe({
      next: () => {
      Swal.fire({ icon: 'success',
                title: '¡Éxito!',
                text: 'Paquete registrado exitosamente.',
                confirmButtonText: 'Aceptar',
                timer: 3000
                });
        this.isSubmitting = false;
        this.router.navigate(['/celador-dashboard']);
      },
      error: (err) => {
        console.error('Error al registrar paquete:', err);
      Swal.fire({
                  icon: "error",
                  title: "Error",
                  text: "Error al Registrar Paquete Verifique los Datos!",
                });
        this.isSubmitting = false;
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/celador-dashboard']);
  }
}
