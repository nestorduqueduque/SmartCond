import { inject, PLATFORM_ID } from '@angular/core'; // <-- 1. Importar PLATFORM_ID
import { isPlatformBrowser } from '@angular/common'; // <-- 2. Importar isPlatformBrowser
import { CanActivateFn, Router, ActivatedRouteSnapshot } from '@angular/router';
import { Auth } from '../services/auth';

export const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const auth = inject(Auth);
  const router = inject(Router);
  const platformId = inject(PLATFORM_ID); // <-- 3. Inyectar el Platform ID

  // 4. Lógica de Plataforma
  if (!isPlatformBrowser(platformId)) {
    // ESTAMOS EN EL SERVIDOR (SSR)
    // Permitir siempre la navegación. El servidor renderizará el "caparazón"
    // de la ruta protegida. La validación real ocurrirá en el navegador.
    return true;
  }

  // --- Si llegamos aquí, ESTAMOS EN EL NAVEGADOR ---

  // 5. Validar si está logueado (en el navegador)
  if (!auth.isLoggedIn()) {
    router.navigate(['/login']);
    return false;
  }

  // 6. Validar roles (en el navegador)
  const userRole = auth.getRole();
  const allowedRoles: string[] = route.data['roles'] || [];
  if (allowedRoles.length > 0 && !allowedRoles.includes(userRole!)) {
    router.navigate(['/']); // Redirigir a la página principal si el rol no coincide
    return false;
  }

  return true;
};
