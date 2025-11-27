import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Celador } from '../../../services/celador';
import { Router } from '@angular/router';
import { VisitorRequestDTO } from '../../dashboard/celador-dashboard/celador-dashboard.interface';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-visitor-register',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './visitor-register.html',
  //styleUrl: './visitor-register.css',
})
export class VisitorRegister {
  private fb = inject(FormBuilder);
  private celadorService = inject(Celador);
  private router = inject(Router);

  isSubmitting = false;


  visitorForm: FormGroup = this.fb.group({
    name: ['', Validators.required],
    document: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
    reason: ['', Validators.required],
    apartment: ['', [Validators.required, Validators.pattern('^[0-9]*$')]]
  });

  onSubmit(): void {
    if (this.visitorForm.invalid || this.isSubmitting) {
      this.visitorForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    const formValue = this.visitorForm.value;


    const request: VisitorRequestDTO = {
      name: formValue.name,
      document: Number(formValue.document),
      reason: formValue.reason,
      apartment: Number(formValue.apartment)
    };

    this.celadorService.createVisitor(request).subscribe({
      next: () => {
        Swal.fire({ icon: 'success',
                        title: '¡Éxito!',
                        text: 'Visitante registrado exitosamente.',
                        confirmButtonText: 'Aceptar',
                        timer: 3000
                        });
        this.isSubmitting = false;
        this.router.navigate(['/celador-dashboard']);
      },
      error: (err) => {
        console.error('Error al registrar visitante:', err);
        Swal.fire({
                          icon: "error",
                          title: "Error",
                          text: "Error al Registrar Visitante Verifique los Datos!",
                        });
        this.isSubmitting = false;
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/celador-dashboard']);
  }



}
