import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Auth } from '../services/auth';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(Auth);
  const token = auth.getToken(); // Esto ya es seguro para SSR gracias a tu servicio

  // 1. Ignorar la petición de login
  if (req.url.includes('/auth/login')) {
    return next(req);
  }

  // 2. Adjuntar token para otras peticiones
  if (token) {
    const cloned = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` }
    });
    return next(cloned);
  }

  return next(req);
};
