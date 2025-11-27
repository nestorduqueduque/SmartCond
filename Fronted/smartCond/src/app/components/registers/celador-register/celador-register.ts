import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, AbstractControl } from '@angular/forms';
import { RegisterService } from '../../../services/register';
import { CeladorRequestDTO } from '../../dashboard/admin-dashboard/admin-dashboard.interface';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-celador-register',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './celador-register.html',
  styleUrl: './celador-register.css',
})
export class CeladorRegister {

  private fb = inject(FormBuilder);
  private registerService = inject(RegisterService);
  private router = inject(Router);

  isSubmitting = false;

  // 🔥 VALIDACIÓN PERSONALIZADA A NIVEL DE FORMULARIO
  private matchFields(control: AbstractControl) {
    const email = control.get('email')?.value;
    const emailConfirm = control.get('emailConfirm')?.value;

    const password = control.get('password')?.value;
    const passwordConfirm = control.get('passwordConfirm')?.value;

    if (email && emailConfirm && email !== emailConfirm) {
      control.get('emailConfirm')?.setErrors({ emailMismatch: true });
    }

    if (password && passwordConfirm && password !== passwordConfirm) {
      control.get('passwordConfirm')?.setErrors({ passwordMismatch: true });
    }

    return null;
  }

  celadorForm: FormGroup = this.fb.group(
    {
      name: ['', Validators.required],
      lastName: ['', Validators.required],
      document: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      email: ['', [Validators.required, Validators.email]],
      emailConfirm: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      passwordConfirm: ['', [Validators.required, Validators.minLength(6)]],
      phoneNumber: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      direction: ['', Validators.required]
    },
    { validators: this.matchFields.bind(this) }
  );

  onSubmit(): void {
    if (this.celadorForm.invalid || this.isSubmitting) {
      this.celadorForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;
    const formValue = this.celadorForm.value;

    const request: CeladorRequestDTO = {
      name: formValue.name,
      lastName: formValue.lastName,
      document: Number(formValue.document),
      email: formValue.email,
      password: formValue.password,
      phoneNumber: Number(formValue.phoneNumber),
      direction: formValue.direction
    };

    this.registerService.createCelador(request).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: '¡Éxito!',
          text: 'Celador registrado exitosamente.',
          confirmButtonText: 'Aceptar',
          timer: 3000
        });
        this.isSubmitting = false;
        this.router.navigate(['/admin-dashboard']);
      },
      error: (err) => {
        console.error('Error al crear celador:', err);
        Swal.fire({
          icon: "error",
          title: "Error",
          text: "Error al Registrar Celador Verifique los Datos!",
        });
        this.isSubmitting = false;
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/admin-dashboard']);
  }
}
