import { CommonModule, NgFor, NgIf } from '@angular/common';
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';
import { Celador } from '../../../services/celador';
import { VehicleResponseDTO } from '../../dashboard/celador-dashboard/celador-dashboard.interface';

@Component({
  selector: 'app-vehicle-resident-list',
  imports: [CommonModule, RouterLink, FormsModule, NgFor, NgIf],
  templateUrl: './vehicle-resident-list.html',
  //styleUrl: './vehicle-resident-list.css',
})
export class VehicleResidentList {
  private registerService = inject(Celador);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  vehicles: VehicleResponseDTO[] = [];
  filteredVehicles: VehicleResponseDTO[] = [];

  searchTerm: string = "";
  isLoading = true;

  ngOnInit() {
    this.loadVehicles();
  }

  loadVehicles(): void {
    this.isLoading = true;

    this.registerService.findResidentVehicles().subscribe({
      next: (resp) => {
        this.vehicles = resp;
        this.filteredVehicles = resp;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        console.error('Error al cargar vehículos');
        this.isLoading = false;
      }
    });
  }

  filterVehicles() {
    const term = this.searchTerm.toLowerCase().trim();

    this.filteredVehicles = this.vehicles.filter(v =>
      v.plate.toLowerCase().includes(term) ||
      v.type.toLowerCase().includes(term) ||
      v.brand.toLowerCase().includes(term) ||
      v.model.toLowerCase().includes(term) ||
      v.apartment.toString().includes(term)
    );
  }

  onDelete(id: number): void {
    Swal.fire({
      title: '¿Eliminar Vehículo?',
      text: "Este vehículo será eliminado del sistema.",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.executeDelete(id);
      }
    });
  }

  private executeDelete(id: number): void {
    this.registerService.deleteVehicle(id).subscribe({
      next: () => {
        Swal.fire('¡Eliminado!', 'El vehículo ha sido eliminado.', 'success');
        this.loadVehicles();
      },
      error: () => {
        Swal.fire('Error', 'No se pudo eliminar el vehículo.', 'error');
      }
    });
  }
  getVehicleTypeName(type: string): string {
  switch (type) {
    case 'CAR':
      return 'Carro';
    case 'MOTORCYCLE':
      return 'Motocicleta';
    default:
      return type;
  }
}

  goRegister(): void {
    this.router.navigate(['/registrar-vehiculo-residente']);
  }

  goBack(): void {
    this.router.navigate(['/celador-dashboard']);
  }

}
