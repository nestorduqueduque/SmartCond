import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { AdminDashboard } from './components/dashboard/admin-dashboard/admin-dashboard';
import { ResidentDashboard } from './components/dashboard/resident-dashboard/resident-dashboard';
import { CeladorDashboard } from './components/dashboard/celador-dashboard/celador-dashboard';
import { authGuard } from './guards/auth-guard';
import { CeladorRegister } from './components/registers/celador-register/celador-register';
import { ResidentRegister } from './components/registers/resident-register/resident-register';
import { VehicleRegister } from './components/registers/vehicle-register/vehicle-register';
import { PackageRegister } from './components/registers/package-register/package-register';
import { VisitorRegister } from './components/registers/visitor-register/visitor-register';
import { CeladorList } from './components/lists/celador-list/celador-list';
import { CeladorUpdate } from './components/lists/celador-update/celador-update';

export const routes: Routes = [

  { path: 'login', component: Login},


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

  //Admin Dashboard
  { path: 'registro-celador', component: CeladorRegister, canActivate: [authGuard] },
  { path: 'registro-residente', component: ResidentRegister, canActivate: [authGuard] },
  { path: 'registro-vehiculo', component: VehicleRegister, canActivate: [authGuard] },
  { path: 'listar-celadores', component: CeladorList, canActivate: [authGuard] },
  { path: 'editar-celadores/:id', component: CeladorUpdate, canActivate: [authGuard] },
  
  //Celador Dashboard
  { path: 'registro-paquete', component: PackageRegister, canActivate: [authGuard] },
  { path: 'registro-visitante', component: VisitorRegister, canActivate: [authGuard] },

  // Redirecciones
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }


];
