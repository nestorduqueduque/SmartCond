import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RegisterService } from '../../../services/register';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-celador-update',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './celador-update.html',
  styleUrl: './celador-update.css',
})
export class CeladorUpdate {

  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private adminService = inject(RegisterService);
  private cdr = inject(ChangeDetectorRef);

  celadorForm!: FormGroup;
  celadorId!: number;

  isSubmitting = false;
  isLoadingData = true;

  ngOnInit(): void {
    // inicializar form aquí es más seguro
    this.celadorForm = this.fb.group({
      name: ['', Validators.required],
      lastName: ['', Validators.required],
      document: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', Validators.required],
      direction: ['', Validators.required],
      password: ['']
    });

    this.route.paramMap.subscribe(params => {
      const id = params.get('id');

      if (!id) {
        Swal.fire('Error', 'No se encontró el ID del celador.', 'error');
        this.router.navigate(['/listar-celadores']);
        return;
      }

      this.celadorId = +id;
      this.loadCeladorData();
    });
  }

  loadCeladorData(): void {
    this.isLoadingData = true;

    this.adminService.getCeladorById(this.celadorId).subscribe({
      next: (data) => {

        // parchamos los valores que sí existan
        this.celadorForm.patchValue({
          name: data.name,
          lastName: data.lastName,
          document: data.document,
          email: data.email,
          phoneNumber: data.phoneNumber,
          direction: data.direction ?? '',   // seguridad extra
          password: ''
        });

        this.isLoadingData = false;

        // evitar problemas con Angular 20 y zoneless
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error cargando datos del celador:', err);
        Swal.fire('Error', 'No se pudo cargar la información.', 'error');
        this.router.navigate(['/listar-celadores']);
      }
    });
  }

  onSubmit(): void {
    if (this.celadorForm.invalid) {
      this.celadorForm.markAllAsTouched();
      Swal.fire('Campos faltantes', 'Revisa el formulario.', 'warning');
      return;
    }

    this.isSubmitting = true;

    const formData = this.celadorForm.value;

    const requestData = {
      name: formData.name,
      lastName: formData.lastName,
      document: formData.document,
      email: formData.email,
      phoneNumber: formData.phoneNumber,
      direction: formData.direction,
      ...(formData.password && { password: formData.password })
    };

    this.adminService.updateCelador(this.celadorId, requestData).subscribe({
      next: (resp) => {
        this.isSubmitting = false;

        Swal.fire(
          '¡Actualizado!',
          `Celador ${resp.name} ${resp.lastName} actualizado correctamente.`,
          'success'
        );

        this.router.navigate(['/listar-celadores']);
      },
      error: (err) => {
        this.isSubmitting = false;

        Swal.fire('Error', err.error?.message || 'Error actualizando el celador', 'error');
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/listar-celadores']);
  }
}
