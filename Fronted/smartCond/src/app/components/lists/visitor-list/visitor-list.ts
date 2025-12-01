import { CommonModule, NgFor, NgIf } from '@angular/common';
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Celador } from '../../../services/celador';

@Component({
  selector: 'app-visitor-list',
  imports: [CommonModule, RouterLink, FormsModule, NgFor, NgIf],
  templateUrl: './visitor-list.html',
  styleUrl: './visitor-list.css',
})
export class VisitorList {

   private registerService = inject(Celador);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  visitors: any[] = [];
  filteredVisitors: any[] = [];

  searchTerm: string = "";
  isLoading = true;

  ngOnInit() {
    this.loadVisitors();
  }

  loadVisitors(): void {
    this.isLoading = true;

    this.registerService.findAllVisitors().subscribe({
      next: (resp) => {
        this.visitors = resp;
        this.filteredVisitors = resp;
        this.isLoading = false;
        this.cdr.markForCheck();
      },
      error: () => {
        console.error('Error al cargar visitantes');
        this.isLoading = false;
      }
    });
  }

  filterVisitors() {
    const term = this.searchTerm.toLowerCase().trim();

    this.filteredVisitors = this.visitors.filter(v =>
      v.name.toLowerCase().includes(term) ||
      v.document.toString().includes(term) ||
      v.reason.toLowerCase().includes(term) ||
      v.apartment.toString().includes(term)
    );
  }

  goBack(): void {
    this.router.navigate(['/celador-dashboard']);
  }
}
