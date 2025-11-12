import { mergeApplicationConfig, ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { FormsModule } from '@angular/forms'; // O ReactiveFormsModule
import { provideServerRendering, withRoutes } from '@angular/ssr';
import { appConfig } from './app.config';
import { serverRoutes } from './app.routes.server';
import { routes } from './app.routes';
import { Auth } from './services/auth';
import { authInterceptor } from './interceptors/auth-interceptor';


const serverConfig: ApplicationConfig = {
  providers: [
    provideServerRendering(withRoutes(serverRoutes)),
    provideRouter(routes),
    provideHttpClient(withInterceptors([authInterceptor])),
  ]
};

export const config = mergeApplicationConfig(appConfig, serverConfig);
