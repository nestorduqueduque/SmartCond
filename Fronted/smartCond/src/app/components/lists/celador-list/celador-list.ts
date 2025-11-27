import { CommonModule, NgFor, NgIf } from '@angular/common';
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { RegisterService } from '../../../services/register';
import { Router, RouterLink } from '@angular/router';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';
import { CeladorResponseDTO } from '../../dashboard/celador-dashboard/celador-dashboard.interface';

@Component({
  selector: 'app-celador-list',
  imports: [CommonModule, RouterLink, FormsModule, NgFor, NgIf],
  templateUrl: './celador-list.html',
})
export class CeladorList {

  private adminUserService = inject(RegisterService);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  celadores: CeladorResponseDTO[] = [];
  filteredCeladores: CeladorResponseDTO[] = [];

  searchTerm: string = "";
  isLoading = true;

  ngOnInit() {
    this.loadCeladores();
  } 

  loadCeladores(): void {
    this.isLoading = true;

    this.adminUserService.getAllCeladors().subscribe({
      next: (resp) => {
        this.celadores = resp;
        this.filteredCeladores = resp;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        console.error('Error al cargar celadores');
        this.isLoading = false;
      }
    });
  }

  filterCeladores() {
    const term = this.searchTerm.toLowerCase().trim();

    this.filteredCeladores = this.celadores.filter(c =>
      c.name.toLowerCase().includes(term) ||
      c.lastName.toLowerCase().includes(term) ||
      c.document.toString().includes(term) ||
      c.email.toLowerCase().includes(term) ||
      c.phoneNumber.toString().includes(term)
    );
  }

  onEdit(id: number): void {
    this.router.navigate(['/editar-celadores', id]);
  }

  onDelete(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: "Se deshabilitará el acceso a este celador.",
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
    this.adminUserService.deleteCelador(id).subscribe({
      next: () => {
        Swal.fire('¡Eliminado!', 'El celador ha sido desactivado.', 'success');
        this.loadCeladores();
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
