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
  showPassword = false;
  showPasswordConfirm = false;

  readonly textPattern = /^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/;
  readonly numberPattern = /^[0-9]+$/;
  readonly emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  // ASYNC VALIDATORS
  documentExistsValidator(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {

      if (!control.value || control.value.length < 5) return of(null);

      return of(control.value).pipe(
        debounceTime(600),
        switchMap(value => this.registerService.checkDocumentExists(value)),
        map(res => res?.exists ? { documentTaken: true } : null),
        catchError(() => of(null))
      );
    };
  }

  emailExistsValidator(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {

      if (!control.value) return of(null);

      return of(control.value).pipe(
        debounceTime(600),
        switchMap(value => this.registerService.checkEmailExists(value)),
        map(res => res?.exists ? { emailTaken: true } : null),
        catchError(() => of(null))
      );
    };
  }

  // FORM
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
      document: ['', [
        Validators.required,
        Validators.maxLength(11),
        Validators.pattern(this.numberPattern)
      ], [this.documentExistsValidator()]],

      email: ['', [
        Validators.required,
        Validators.pattern(this.emailPattern)
      ], [this.emailExistsValidator()]],

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
    { validators: this.matchFields.bind(this) }
  );

  // SANITIZADORES
  stripNonNumeric(controlName: string): void {
    const ctrl = this.celadorForm.get(controlName);
    if (!ctrl) return;
    const clean = ctrl.value?.replace(/\D+/g, '') ?? '';
    if (clean !== ctrl.value) ctrl.setValue(clean, { emitEvent: false });
  }

  stripNonAlpha(controlName: string): void {
    const ctrl = this.celadorForm.get(controlName);
    if (!ctrl) return;
    const clean = ctrl.value?.replace(/[^a-zA-ZñÑáéíóúÁÉÍÓÚ\s]/g, '') ?? '';
    if (clean !== ctrl.value) ctrl.setValue(clean, { emitEvent: false });
  }

  // MATCH EMAIL / PASSWORD
  private matchFields(form: AbstractControl) {
    if (!form) return null;

    const email = form.get('email');
    const emailConfirm = form.get('emailConfirm');
    const pass = form.get('password');
    const passConfirm = form.get('passwordConfirm');

    if (email && emailConfirm) {
      emailConfirm.setErrors(
        email.value && emailConfirm.value && email.value !== emailConfirm.value
          ? { emailMismatch: true }
          : null
      );
    }

    if (pass && passConfirm) {
      passConfirm.setErrors(
        pass.value && passConfirm.value && pass.value !== passConfirm.value
          ? { passwordMismatch: true }
          : null
      );
    }

    return null;
  }

  toggleShowPassword() {
    this.showPassword = !this.showPassword;
  }

  toggleShowPasswordConfirm() {
    this.showPasswordConfirm = !this.showPasswordConfirm;
  }

  // SUBMIT
  onSubmit(): void {
    if (this.celadorForm.invalid) {
      this.celadorForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;

    const v = this.celadorForm.value;

    const request: CeladorRequestDTO = {
      name: v.name,
      lastName: v.lastName,
      document: Number(v.document),
      email: v.email,
      password: v.password,
      phoneNumber: Number(v.phoneNumber),
      direction: v.direction
    };

    this.registerService.createCelador(request).subscribe({
      next: () => {
        Swal.fire({
          icon: 'success',
          title: '¡Éxito!',
          text: 'Celador registrado exitosamente.',
          timer: 3000
        });
        this.router.navigate(['/admin-dashboard']);
        this.isSubmitting = false;
      },
      error: (err) => {
        let msg = "Error al Registrar Celador. Verifique los Datos!";
        const backend = err.error?.message || err.error?.error || "";

        if (backend.toLowerCase().includes("documento")) msg = "El número de documento ya está registrado.";
        if (backend.toLowerCase().includes("correo") || backend.toLowerCase().includes("email")) msg = "El correo ya está registrado.";

        Swal.fire({ icon: "error", title: "Error", text: msg });
        this.isSubmitting = false;
      }
    });
  }

  goBack() {
    this.router.navigate(['/admin-dashboard']);
  }
}
