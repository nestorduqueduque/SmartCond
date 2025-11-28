import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable, tap, firstValueFrom } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class Auth {
  private apiUrl = 'https://smartcond-production.up.railway.app/auth';
  private tokenKey = 'authToken';
  private isBrowser: boolean;
  private _initialized = false;
  private _userRole: string | null = null;
  private _readyPromise: Promise<void>;

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(this.platformId);

    // 🔹 Promesa que asegura que la sesión se restaure antes de usar el servicio
    this._readyPromise = new Promise((resolve) => {
      if (this.isBrowser) {
        this.restoreSession();
      }
      this._initialized = true;
      resolve();
    });
  }

  // 🔹 Método para esperar que la sesión esté lista
  async ready(): Promise<void> {
    await this._readyPromise;
  }

  // ✅ LOGIN
  login(credentials: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        const token = response.jwt;
        if (token) {
          this.saveToken(token);
          this._userRole = this.getRoleFromToken(token);
          this.redirectByRole();
        }
      })
    );
  }

  // ✅ Guarda token
  private saveToken(token: string): void {
    if (this.isBrowser) {
      localStorage.setItem(this.tokenKey, token);
    }
  }

  // ✅ Obtiene token
  getToken(): string | null {
    return this.isBrowser ? localStorage.getItem(this.tokenKey) : null;
  }

  // ✅ Logout
  logout(): void {
    if (this.isBrowser) {
      localStorage.removeItem(this.tokenKey);
      this._userRole = null;
      this.router.navigate(['/login']);
    }
  }

  // ✅ Verifica sesión
  isLoggedIn(): boolean {
    if (!this._initialized) this.restoreSession();

    const token = this.getToken();
    if (!token) return false;

    try {
      const decoded: any = jwtDecode(token);
      const exp = decoded.exp ? decoded.exp * 1000 : null;
      if (exp && Date.now() > exp) {
        this.logout();
        return false;
      }
      return true;
    } catch {
      this.logout();
      return false;
    }
  }

  // ✅ Rol de usuario
  getRole(): string | null {
    if (!this._initialized) this.restoreSession();
    return this._userRole;
  }

  // ✅ Extrae el rol del token JWT
  private getRoleFromToken(token: string): string | null {
    try {
      const decoded: any = jwtDecode(token);
      const role = decoded.roles?.[0] || decoded.authorities?.[0] || null;
      return role ? role.replace('ROLE_', '') : null;
    } catch {
      return null;
    }
  }

  // ✅ Restaurar sesión desde localStorage
  private restoreSession(): void {
    if (!this.isBrowser) return;
    const token = this.getToken();
    if (token) {
      this._userRole = this.getRoleFromToken(token);
    }
    this._initialized = true;
  }

  // ✅ Redirección según rol
  private redirectByRole(): void {
    const role = this.getRole();
    switch (role) {
      case 'ADMIN':
        this.router.navigate(['/admin-dashboard']);
        break;
      case 'RESIDENT':
        this.router.navigate(['/resident-dashboard']);
        break;
      case 'CELADOR':
        this.router.navigate(['/celador-dashboard']);
        break;
      default:
        this.router.navigate(['/']);
        break;
    }
  }
}
