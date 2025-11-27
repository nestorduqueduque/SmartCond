import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Auth } from '../../services/auth';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  credentials = {
    email: '',
    password: ''
  };

  private authService = inject(Auth);
  public errorMessage: string | null = null;

  onSubmit(): void {
    this.errorMessage = null;
    this.authService.login(this.credentials).subscribe({
      next: (response) => {

        console.log('Login exitoso');
      },
      error: (err) => {
        console.error("Error en el login", err);

        if (err.status === 400) {
          // si tu backend envía mensaje en body
          this.errorMessage = err.error?.message || "Credenciales incorrectas";
        } else if (err.status === 0) {
          this.errorMessage = "No hay conexión con el servidor";
        } else {
          this.errorMessage = "Error desconocido. Intenta de nuevo.";
        }
      }
    });
  }

}
