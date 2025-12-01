import { CommonModule, NgFor, NgIf } from '@angular/common';
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Celador } from '../../../services/celador';
import { VehicleResponseDTO } from '../../dashboard/celador-dashboard/celador-dashboard.interface';

@Component({
  selector: 'app-vehicle-visitor-list',
  imports: [CommonModule, RouterLink, FormsModule, NgFor, NgIf],
  templateUrl: './vehicle-visitor-list.html',
  styleUrl: './vehicle-visitor-list.css',
})
export class VehicleVisitorList {

  private registerService = inject(Celador);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  vehicles: VehicleResponseDTO[] = [];
  filteredVehicles: VehicleResponseDTO[] = [];

  searchTerm: string = "";
  isLoading: boolean = true;

  ngOnInit() {
    this.loadVehicles();
  }

  loadVehicles() {
    this.isLoading = true;

    this.registerService.findVisitorVehicles().subscribe({
      next: (resp) => {
        this.vehicles = resp;
        this.filteredVehicles = resp;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        this.isLoading = false;
        console.error("Error al cargar vehículos.");
      }
    });
  }

  filterVehicles() {
    const term = this.searchTerm.toLowerCase().trim();

    this.filteredVehicles = this.vehicles.filter(v =>
      v.plate.toLowerCase().includes(term) ||
      v.brand.toLowerCase().includes(term) ||
      v.model.toLowerCase().includes(term) ||
      v.apartment.toString().includes(term)
    );
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

  goBack() {
    this.router.navigate(['/celador-dashboard']);
  }

}
