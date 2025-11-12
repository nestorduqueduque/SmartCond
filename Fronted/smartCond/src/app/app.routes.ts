import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { AdminDashboard } from './components/dashboard/admin-dashboard/admin-dashboard';
import { ResidentDashboard } from './components/dashboard/resident-dashboard/resident-dashboard';
import { CeladorDashboard } from './components/dashboard/celador-dashboard/celador-dashboard';
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [
  // Ruta de Login (pública)
  { path: 'login', component: Login},

  // Ruta para Admin (protegida)
  {
    path: 'admin-dashboard',
    component: AdminDashboard,
    canActivate: [authGuard],
    data: { roles: ['ADMIN'] }
  },

   {
    path: 'resident-dashboard',
    component: ResidentDashboard,
    canActivate: [authGuard],
    data: { roles: ['RESIDENT'] }
  },

    {
    path: 'celador-dashboard',
    component: CeladorDashboard,
    canActivate: [authGuard],
    data: { roles: ['CELADOR'] }
  },


  // Redirecciones
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];
