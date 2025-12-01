import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Celador } from '../../../services/celador';
import { VehicleRequestDTO, VehicleTypeOption } from '../../dashboard/admin-dashboard/admin-dashboard.interface';
import { Auth } from '../../../services/auth';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-vehicle-visitor-register',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './vehicle-visitor-register.html',
  styleUrl: './vehicle-visitor-register.css',
})
export class VehicleVisitorRegister {

    private fb = inject(FormBuilder);
    private vehicleService = inject(Celador);
    private router = inject(Router);
    private authService = inject(Auth);

    isSubmitting = false;

    vehicleTypes: VehicleTypeOption[] = [
      { label: 'Carro', value: 'CAR' },
      { label: 'Motocicleta', value: 'MOTORCYCLE' },
    ];

    vehicleForm: FormGroup = this.fb.group({
      plate: ['', Validators.required],
      type: ['', Validators.required],
      brand: ['', Validators.required],
      model: ['', Validators.required],
      apartment: ['', [Validators.required, Validators.pattern('^[0-9]*$')]]
    });

    private navigateToDashboard(): void {
      const role = this.authService.getRole();

      if (role === 'ADMIN') {
        this.router.navigate(['/admin-dashboard']);
      } else if (role === 'CELADOR') {
        this.router.navigate(['/celador-dashboard']);
      } else {
        this.router.navigate(['/']);
      }
    }

    onSubmit(): void {
      if (this.vehicleForm.invalid || this.isSubmitting) {
        this.vehicleForm.markAllAsTouched();
        return;
      }

      this.isSubmitting = true;
      const formValue = this.vehicleForm.value;

      const request: VehicleRequestDTO = {
        plate: formValue.plate.toUpperCase(),
        type: formValue.type,
        brand: formValue.brand,
        model: formValue.model,
        apartment: Number(formValue.apartment)
      };

      this.vehicleService.createVisitorVehicle(request).subscribe({
        next: () => {
          Swal.fire({
            icon: 'success',
            title: '¡Éxito!',
            text: 'Vehículo registrado exitosamente.',
            confirmButtonText: 'Aceptar',
            timer: 3000
          });
          this.isSubmitting = false;
          this.navigateToDashboard();
        },
        error: (err) => {
          console.error('Error al registrar vehículo:', err);
          Swal.fire({
              icon: "error",
              title: "Error",
              text: "Error al Registrar vehículo Verifique los Datos!",
  });
          this.isSubmitting = false;
        }
      });
    }
    goBack(): void {
    this.navigateToDashboard();
    }

}
