import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { RegisterService } from '../../../services/register';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-resident-update',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './resident-update.html',
  styleUrl: './resident-update.css',
})
export class ResidentUpdate {

  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private adminService = inject(RegisterService);
  private cdr = inject(ChangeDetectorRef);

  residentForm!: FormGroup;
  residentId!: number;

  isSubmitting = false;
  isLoadingData = true;

  ngOnInit(): void {

    this.residentForm = this.fb.group({
      name: ['', Validators.required],
      lastName: ['', Validators.required],
      document: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', Validators.required],
      apartment: ['', Validators.required],
      password: ['']
    });

    this.route.paramMap.subscribe(params => {
          const id = params.get('id');

          if (!id) {
            Swal.fire('Error', 'No se encontró el ID del residente.', 'error');
            this.router.navigate(['/listar-residentes']);
            return;
          }

          this.residentId = +id;
          this.loadResidentData();
        });
  }

  loadResidentData(): void {
      this.isLoadingData = true;

      this.adminService.getResidentById(this.residentId).subscribe({
        next: (data) => {

          this.residentForm.patchValue({
            name: data.name,
            lastName: data.lastName,
            document: data.document,
            email: data.email,
            phoneNumber: data.phoneNumber,
            apartment: data.apartment ?? '',
            password: ''
          });

          this.isLoadingData = false;
          this.cdr.detectChanges();
        },
        error: (err) => {
          console.error('Error cargando datos del residente:', err);
          Swal.fire('Error', 'No se pudo cargar la información.', 'error');
          this.router.navigate(['/listar-residentes']);
        }
      });
    }

    onSubmit(): void {
        if (this.residentForm.invalid) {
          this.residentForm.markAllAsTouched();
          Swal.fire('Campos faltantes', 'Revisa el formulario.', 'warning');
          return;
        }

        this.isSubmitting = true;

        const formData = this.residentForm.value;

        const requestData = {
          name: formData.name,
          lastName: formData.lastName,
          document: formData.document,
          email: formData.email,
          phoneNumber: formData.phoneNumber,
          apartment: formData.apartment,
          ...(formData.password && { password: formData.password })
        };

        this.adminService.updateResident(this.residentId, requestData).subscribe({
          next: (resp) => {
            this.isSubmitting = false;

            Swal.fire(
              '¡Actualizado!',
              `Residente ${resp.name} ${resp.lastName} actualizado correctamente.`,
              'success'
            );

            this.router.navigate(['/listar-residentes']);
          },
          error: (err) => {
            this.isSubmitting = false;

            Swal.fire('Error', err.error?.message || 'Error actualizando el residente', 'error');
          }
        });
      }

      goBack(): void {
        this.router.navigate(['/listar-residentes']);
      }







}
