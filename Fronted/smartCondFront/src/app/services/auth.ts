import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface LoginRequest {
  email: string;
  password?: string; // Hacemos password opcional por si en el futuro hay otros métodos
}

export interface LoginResponse {
  token: string;
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Usamos la nueva inyección de dependencias 'inject()'
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/auth/login'; // URL de tu backend Spring Boot

  /**
   * Envía las credenciales al backend para autenticar al usuario.
   * @param credentials - Objeto con email y password.
   * @returns Un Observable con la respuesta del servidor (token y mensaje).
   */
  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.apiUrl, credentials);
  }
}
