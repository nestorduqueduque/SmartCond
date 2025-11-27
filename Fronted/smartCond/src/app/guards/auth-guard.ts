import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { CanActivateFn, Router, ActivatedRouteSnapshot } from '@angular/router';
import { Auth } from '../services/auth';

export const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const auth = inject(Auth);
  const router = inject(Router);
  const platformId = inject(PLATFORM_ID);

  if (!isPlatformBrowser(platformId)) {

    return true;
  }

  if (!auth.isLoggedIn()) {
    router.navigate(['/login']);
    return false;
  }

  const userRole = auth.getRole();
  const allowedRoles: string[] = route.data['roles'] || [];
  if (allowedRoles.length > 0 && !allowedRoles.includes(userRole!)) {
    router.navigate(['/']);
    return false;
  }

  return true;
};
