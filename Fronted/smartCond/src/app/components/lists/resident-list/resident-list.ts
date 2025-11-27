import { CommonModule, NgFor, NgIf } from '@angular/common';
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RegisterService } from '../../../services/register';
import { ResidentResponseDTO } from '../../dashboard/resident-dashboard/resident-dashboard.interface';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-resident-list',
  imports: [CommonModule, RouterLink, FormsModule, NgFor, NgIf],
  templateUrl: './resident-list.html',
  styleUrl: './resident-list.css',
})
export class ResidentList {

  private adminUserService = inject(RegisterService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  residents: ResidentResponseDTO[] = [];
  filteredResidents: ResidentResponseDTO[] = [];
  searchTerm: string = "";
  isLoading = true;

  ngOnInit() {
    this.loadResidents();
  }

  loadResidents(): void {
    this.isLoading = true;

    this.adminUserService.getAllResidents().subscribe({
      next: (resp) => {
        this.residents = resp;
        this.filteredResidents = resp;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        console.error('Error al cargar celadores');
        this.isLoading = false;
      }
    });
  }

  filterResidents() {
    const term = this.searchTerm.toLowerCase().trim();

    this.filteredResidents = this.residents.filter(c =>
      c.name.toLowerCase().includes(term) ||
      c.lastName.toLowerCase().includes(term) ||
      c.document.toString().includes(term) ||
      c.email.toLowerCase().includes(term) ||
      c.phoneNumber.toString().includes(term) ||
      c.apartment.toString().includes(term)
    );
  }

  onEdit(id: number): void {
      this.router.navigate(['/editar-residentes', id]);
    }

    onDelete(id: number): void {
      Swal.fire({
        title: '¿Estás seguro?',
        text: "Se deshabilitará el acceso a este residente.",
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
      this.adminUserService.deleteResident(id).subscribe({
        next: () => {
          Swal.fire('¡Eliminado!', 'El residente ha sido desactivado.', 'success');
          this.loadResidents();
        },
        error: () => {
          Swal.fire('Error', 'No se pudo eliminar el usuario.', 'error');
        }
      });
    }

    goBack(): void {
      this.router.navigate(['/admin-dashboard']);
    }

}
