import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth'

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class Login {
  private authService = inject(AuthService);

  // Modelo para los datos del formulario
  credentials = {
    email: '',
    password: ''
  };

  // Variables para gestionar el estado de la UI
  isLoading = false;
  errorMessage = '';

  // Función que se llama al enviar el formulario
  onSubmit(): void {
    if (!this.credentials.email || !this.credentials.password) {
      this.errorMessage = 'Por favor, introduce tu email y contraseña.';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        // Éxito
        this.isLoading = false;
        console.log('Login exitoso! Token:', response.token);
        // Aquí podrías guardar el token y redirigir al usuario
        // Ejemplo: localStorage.setItem('authToken', response.token);
        // this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        // Error
        this.isLoading = false;
        console.error('Error en el login:', err);
        this.errorMessage = 'Credenciales incorrectas. Por favor, inténtalo de nuevo.';
      }
    });
  }

}
