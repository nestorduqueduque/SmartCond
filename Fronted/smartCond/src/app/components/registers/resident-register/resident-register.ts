import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RegisterService } from '../../../services/register';
import { Router } from '@angular/router';
import { ResidentRequestDTO } from '../../dashboard/admin-dashboard/admin-dashboard.interface';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-resident-register',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './resident-register.html',
  styleUrl: './resident-register.css',
})
export class ResidentRegister {

  private fb = inject(FormBuilder);
  private registerService = inject(RegisterService);
  private router = inject(Router);

  isSubmitting = false;

  residentForm: FormGroup = this.fb.group({
    name: ['', Validators.required],
    lastName: ['', Validators.required],
    document: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    phoneNumber: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
    apartment: ['', Validators.required, Validators.pattern('^[0-9]*$')]
  });

  onSubmit(): void {
    if (this.residentForm.invalid || this.isSubmitting) {
      this.residentForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    const formValue = this.residentForm.value;


    const request: ResidentRequestDTO = {
      name: formValue.name,
      lastName: formValue.lastName,
      document: Number(formValue.document),
      email: formValue.email,
      password: formValue.password,
      phoneNumber: Number(formValue.phoneNumber),
      apartment: Number(formValue.apartment)
    };

    this.registerService.createResident(request).subscribe({
      next: () => {
        Swal.fire({
                  icon: 'success',
                  title: '¡Éxito!',
                  text: 'Residente registrado exitosamente.',
                  confirmButtonText: 'Aceptar',
                  timer: 3000
                });
        this.isSubmitting = false;
        this.router.navigate(['/admin-dashboard']);
      },
      error: (err) => {
        console.error('Error al crear residente:', err);
        Swal.fire({
                  icon: "error",
                  title: "Error",
                  text: "Error al Registrar Residente Verifique los Datos!",
       });
        this.isSubmitting = false;
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/admin-dashboard']);
  }


}
