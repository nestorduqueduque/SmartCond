import { CommonModule, NgFor, NgIf } from '@angular/common';
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { Celador } from '../../../services/celador';
import { PackageResponseDTO } from '../../dashboard/celador-dashboard/celador-dashboard.interface';

@Component({
  selector: 'app-package-list',
  imports: [CommonModule, FormsModule, NgFor, NgIf],
  templateUrl: './package-list.html',
  styleUrl: './package-list.css',
})
export class PackageList {
  private service = inject(Celador);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  packages: PackageResponseDTO[] = [];
  filteredPackages: PackageResponseDTO[] = [];

  searchTerm: string = "";
  isLoading = true;

  ngOnInit() {
    this.loadPackages();
  }

  loadPackages(): void {
    this.isLoading = true;

    this.service.findAllPackages().subscribe({
      next: (resp) => {
        this.packages = resp;
        this.filteredPackages = resp;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        console.error('Error al cargar paquetes');
        this.isLoading = false;
      }
    });
  }

  filterPackages() {
    const term = this.searchTerm.toLowerCase().trim();

    this.filteredPackages = this.packages.filter(p =>
      p.description.toLowerCase().includes(term) ||
      p.apartment.toString().includes(term) ||
      (p.status ?? '').toLowerCase().includes(term)
    );
  }

  markAsDelivered(id: number): void {
  Swal.fire({
    title: '¿Marcar como entregado?',
    text: "El paquete dejará de estar disponible como recibido.",
    icon: 'question',
    showCancelButton: true,
    confirmButtonText: 'Sí, entregar',
    cancelButtonText: 'Cancelar'
  }).then(result => {
    if (result.isConfirmed) {
      this.service.markPackageAsDelivered(id).subscribe({
        next: () => {
          Swal.fire('Listo', 'El paquete fue marcado como entregado.', 'success');
          this.loadPackages();
        },
        error: () => {
          Swal.fire('Error', 'No se pudo actualizar el paquete.', 'error');
        }
      });
    }
  });
}

  translateStatus(status: string): string {
  switch (status) {
    case 'RECEIVED':
      return 'RECIBIDO';
    case 'DELIVERED':
      return 'ENTREGADO';
    default:
      return status;
  }
}

  goCreate(): void {
    this.router.navigate(['/registro-paquete']);
  }

  goBack(): void {
    this.router.navigate(['/celador-dashboard']);
  }

}
