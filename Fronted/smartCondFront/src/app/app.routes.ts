import { Routes } from '@angular/router';
import { Login } from './components/login/login';

export const routes: Routes = [
   { path: '', redirectTo: '/login', pathMatch: 'full' },
  // Cuando el usuario visite /login, se mostrará el LoginComponent
  { path: 'login', component: Login }

];
