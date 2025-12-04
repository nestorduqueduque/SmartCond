import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
  AbstractControl,
  AsyncValidatorFn,
  ValidationErrors
} from '@angular/forms';
import { RegisterService } from '../../../services/register';
import { ResidentRequestDTO } from '../../dashboard/admin-dashboard/admin-dashboard.interface';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Observable, of } from 'rxjs';
import { debounceTime, switchMap, map, catchError } from 'rxjs/operators';

@Component({
  selector: 'app-resident-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './resident-register.html',
  styleUrl: './resident-register.css',
})
export class ResidentRegister {

  private fb = inject(FormBuilder);
  private registerService = inject(RegisterService);
  private router = inject(Router);

  isSubmitting = false;

  showPassword = false;
  showPasswordConfirm = false;

  readonly textPattern = /^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/;
  readonly numberPattern = /^[0-9]+$/;
  readonly emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  /** --------------------------
   *  ASYNC VALIDATORS
   *  -------------------------- */
  documentExistsValidator(registerService: RegisterService): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      if (!control.value || control.pristine) return of(null);

      return of(control.value).pipe(
        debounceTime(500),
        switchMap(value => registerService.checkDocumentExists(value)),
        map(res => (res?.exists ? { documentTaken: true } : null)),
        catchError(() => of(null))
      );
    };
  }

  emailExistsValidator(registerService: RegisterService): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      if (!control.value || control.pristine) return of(null);

      return of(control.value).pipe(
        debounceTime(500),
        switchMap(value => registerService.checkEmailExists(value)),
        map(res => (res?.exists ? { emailTaken: true } : null)),
        catchError(() => of(null))
      );
    };
  }

  /** --------------------------
   *  FORMULARIO
   *  -------------------------- */
  residentForm: FormGroup = this.fb.group(
    {
      name: ['', [Validators.required, Validators.maxLength(30), Validators.pattern(this.textPattern)]],
      lastName: ['', [Validators.required, Validators.maxLength(30), Validators.pattern(this.textPattern)]],

      document: ['', {
        validators: [Validators.required, Validators.pattern(this.numberPattern), Validators.maxLength(11)],
        asyncValidators: [this.documentExistsValidator(this.registerService)],
        updateOn: 'blur'
      }],

      email: ['', {
        validators: [Validators.required, Validators.pattern(this.emailPattern)],
        asyncValidators: [this.emailExistsValidator(this.registerService)],
        updateOn: 'blur'
      }],

      emailConfirm: ['', [Validators.required, Validators.pattern(this.emailPattern)]],

      password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(15)]],
      passwordConfirm: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(15)]],

      phoneNumber: ['', [Validators.required, Validators.pattern(this.numberPattern), Validators.maxLength(15)]],

      apartment: ['', [Validators.required, Validators.pattern(this.numberPattern)]],
    },
    { validators: this.matchFields.bind(this) }
  );

  /** --------------------------
   * MATCH EMAIL Y PASSWORD
   * -------------------------- */
  private matchFields(form: AbstractControl | null) {
    if (!form) return null;

    const email = form.get('email');
    const emailConfirm = form.get('emailConfirm');
    const password = form.get('password');
    const passwordConfirm = form.get('passwordConfirm');

    // Email
    if (email && emailConfirm) {
      if (email.value && emailConfirm.value && email.value !== emailConfirm.value) {
        emailConfirm.setErrors({ ...(emailConfirm.errors || {}), emailMismatch: true });
      } else {
        if (emailConfirm.errors) {
          const { emailMismatch, ...rest } = emailConfirm.errors;
          emailConfirm.setErrors(Object.keys(rest).length ? rest : null);
        }
      }
    }

     // Password match
    if (password && passwordConfirm) {
      if (password.value && passwordConfirm.value && password.value !== passwordConfirm.value) {
        passwordConfirm.setErrors({ ...(passwordConfirm.errors || {}), passwordMismatch: true });
      } else {
        if (passwordConfirm.errors) {
          const { passwordMismatch, ...rest } = passwordConfirm.errors;
          const newErr = Object.keys(rest).length ? rest : null;
          passwordConfirm.setErrors(newErr);
        }
      }
    }

    return null;
  }

  /** --------------------------
   * LIMPIEZA DE INPUTS
   * -------------------------- */
  stripNonNumeric(controlName: string): void {
    const ctrl = this.residentForm.get(controlName);
    if (!ctrl) return;
    const cleaned = (ctrl.value || '').toString().replace(/[^0-9]/g, '');
    if (cleaned !== ctrl.value) ctrl.setValue(cleaned, { emitEvent: false });
  }

  stripNonAlpha(controlName: string): void {
    const ctrl = this.residentForm.get(controlName);
    if (!ctrl) return;
    const cleaned = (ctrl.value || '').toString().replace(/[^a-zA-ZñÑáéíóúÁÉÍÓÚ\s]/g, '');
    if (cleaned !== ctrl.value) ctrl.setValue(cleaned, { emitEvent: false });
  }

  /** --------------------------
   * TOGGLES
   * -------------------------- */
  toggleShowPassword() { this.showPassword = !this.showPassword; }
  toggleShowPasswordConfirm() { this.showPasswordConfirm = !this.showPasswordConfirm; }

  /** --------------------------
   * SUBMIT
   * -------------------------- */
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
        let errorMessage = "Error al Registrar Residente. Verifique los Datos!";

        if (err.status === 409 || err.status === 400) {
          const backendMessage = err.error?.message || err.error?.error || '';

          if (backendMessage.toLowerCase().includes('documento')) {
            errorMessage = "El número de documento ya está registrado.";
          } else if (backendMessage.toLowerCase().includes('correo') || backendMessage.toLowerCase().includes('email')) {
            errorMessage = "El correo electrónico ya está registrado.";
          } else {
            errorMessage = backendMessage || errorMessage;
          }
        }

        console.error('Error al crear residente:', err);
        Swal.fire({
          icon: "error",
          title: "Error",
          text: errorMessage,
        });
        this.isSubmitting = false;
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/admin-dashboard']);
  }
}
