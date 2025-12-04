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
import { CeladorRequestDTO } from '../../dashboard/admin-dashboard/admin-dashboard.interface';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Observable, of } from 'rxjs';
import { debounceTime, switchMap, map, catchError } from 'rxjs/operators';

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

  // toggles for eye icons
  showPassword = false;
  showPasswordConfirm = false;

  readonly textPattern = /^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/;
  readonly numberPattern = /^[0-9]+$/;
  readonly emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  // Async validators with debounce (you had these — las dejé)
  documentExistsValidator(registerService: RegisterService): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      if (!control.value || control.pristine) {
        return of(null);
      }
      return of(control.value).pipe(
        debounceTime(500),
        switchMap(documentValue => registerService.checkDocumentExists(documentValue)),
        map((response: any) => (response && response.exists === true) ? { documentTaken: true } : null),
        catchError(() => of(null))
      );
    };
  }

  emailExistsValidator(registerService: RegisterService): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      if (!control.value || control.pristine) {
        return of(null);
      }
      return of(control.value).pipe(
        debounceTime(500),
        switchMap(emailValue => registerService.checkEmailExists(emailValue)),
        map((response: any) => (response && response.exists === true) ? { emailTaken: true } : null),
        catchError(() => of(null))
      );
    };
  }

  // Form
  celadorForm: FormGroup = this.fb.group(
    {
      name: ['', [
        Validators.required,
        Validators.maxLength(30),
        Validators.pattern(this.textPattern)
      ]],
      lastName: ['', [
        Validators.required,
        Validators.maxLength(30),
        Validators.pattern(this.textPattern)
      ]],
      document: ['', {
        validators: [
          Validators.required,
          Validators.maxLength(11),
          Validators.pattern(this.numberPattern)
        ],
        asyncValidators: [this.documentExistsValidator(this.registerService)],
        updateOn: 'blur'
      }],
      email: ['', {
        validators: [
          Validators.required,
          Validators.pattern(this.emailPattern)
        ],
        asyncValidators: [this.emailExistsValidator(this.registerService)],
        updateOn: 'blur'
      }],
      emailConfirm: ['', [
        Validators.required,
        Validators.pattern(this.emailPattern)
      ]],
      password: ['', [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(15)
      ]],
      passwordConfirm: ['', [
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(15)
      ]],
      phoneNumber: ['', [
        Validators.required,
        Validators.pattern(this.numberPattern),
        Validators.maxLength(15)
      ]],
      direction: ['', [
        Validators.required,
        Validators.maxLength(50)
      ]]
    },
    { validators: this.matchFields.bind(this) } // group validator
  );

  // Strip helper para inputs
  stripNonNumeric(controlName: string): void {
    const ctrl = this.celadorForm.get(controlName);
    if (!ctrl) return;
    const cleaned = (ctrl.value || '').toString().replace(/[^0-9]/g, '');
    if (cleaned !== ctrl.value) {
      ctrl.setValue(cleaned, { emitEvent: false });
    }
  }

  stripNonAlpha(controlName: string): void {
    const ctrl = this.celadorForm.get(controlName);
    if (!ctrl) return;
    const cleaned = (ctrl.value || '').toString().replace(/[^a-zA-ZñÑáéíóúÁÉÍÓÚ\s]/g, '');
    if (cleaned !== ctrl.value) {
      ctrl.setValue(cleaned, { emitEvent: false });
    }
  }

  // Group validator to compare email/password and their confirms
  private matchFields(control: AbstractControl | null) {
    if (!control) return null;
    const email = control.get('email');
    const emailConfirm = control.get('emailConfirm');
    const password = control.get('password');
    const passwordConfirm = control.get('passwordConfirm');

    // Email match
    if (email && emailConfirm) {
      if (email.value && emailConfirm.value && email.value !== emailConfirm.value) {
        emailConfirm.setErrors({ ...(emailConfirm.errors || {}), emailMismatch: true });
      } else {
        // remove only emailMismatch (keep other errors)
        if (emailConfirm.errors) {
          const { emailMismatch, ...rest } = emailConfirm.errors;
          const newErr = Object.keys(rest).length ? rest : null;
          emailConfirm.setErrors(newErr);
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

  // Eye toggles
  toggleShowPassword(): void {
    this.showPassword = !this.showPassword;
  }
  toggleShowPasswordConfirm(): void {
    this.showPasswordConfirm = !this.showPasswordConfirm;
  }

  // Submit
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
        let errorMessage = "Error al Registrar Celador. Verifique los Datos!";

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

        console.error('Error al crear celador:', err);
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
