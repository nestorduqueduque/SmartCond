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
import { ResidentUpdate } from './components/lists/resident-update/resident-update';
import { ResidentList } from './components/lists/resident-list/resident-list';
import { NoticeList } from './components/lists/notice-list/notice-list';
import { NoticeUpdate } from './components/lists/notice-update/notice-update';
import { NoticeRegister } from './components/registers/notice-register/notice-register';
import { PackageList } from './components/lists/package-list/package-list';
import { VisitorList } from './components/lists/visitor-list/visitor-list';
import { VehicleResidentList } from './components/lists/vehicle-resident-list/vehicle-resident-list';
import { VehicleVisitorList } from './components/lists/vehicle-visitor-list/vehicle-visitor-list';
import { VehicleVisitorRegister } from './components/registers/vehicle-visitor-register/vehicle-visitor-register';

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
  { path: 'editar-residentes/:id', component: ResidentUpdate, canActivate: [authGuard] },
  { path: 'listar-residentes', component: ResidentList, canActivate: [authGuard] },
  { path: 'listar-noticias', component: NoticeList, canActivate: [authGuard] },
  { path: 'editar-noticias/:id', component: NoticeUpdate, canActivate: [authGuard] },
  { path: 'registro-noticias', component: NoticeRegister, canActivate: [authGuard] },

  //Celador Dashboard
  { path: 'registro-paquete', component: PackageRegister, canActivate: [authGuard] },
  { path: 'registro-visitante', component: VisitorRegister, canActivate: [authGuard] },
  { path: 'listar-paquetes', component: PackageList, canActivate: [authGuard] },
  { path: 'listar-visitantes', component: VisitorList, canActivate: [authGuard] },
  { path: 'listar-vehiculos-residentes', component: VehicleResidentList, canActivate: [authGuard] },
  { path: 'listar-vehiculos-visitantes', component: VehicleVisitorList, canActivate: [authGuard] },
  { path: 'registro-vehiculo-visitante', component: VehicleVisitorRegister, canActivate: [authGuard] },

  // Redirecciones
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }


];
