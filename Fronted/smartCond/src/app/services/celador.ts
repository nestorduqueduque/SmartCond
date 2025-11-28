import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { VehicleRequestDTO } from '../components/dashboard/admin-dashboard/admin-dashboard.interface';
import { Observable } from 'rxjs';
import { VehicleResponseDTO, VisitorRequestDTO, VisitorResponseDTO, PackageRequestDTO, PackageResponseDTO, CeladorDashboardDTO } from '../components/dashboard/celador-dashboard/celador-dashboard.interface';

@Injectable({
  providedIn: 'root'
})
export class Celador {
  private http = inject(HttpClient);
  private apiUrl = 'https://smartcond-production.up.railway.app/celador';


  getCeladorDashboardData(): Observable<any> {
    return this.http.get<CeladorDashboardDTO>(`${this.apiUrl}/dashboard-data-celador`);
  }

  createVehicle(vehicle: VehicleRequestDTO): Observable<any> {
      return this.http.post(`${this.apiUrl}/create-vehicle`, vehicle);
    }

  findAllVehicles(): Observable<VehicleResponseDTO[]> {
    return this.http.get<VehicleResponseDTO[]>(`${this.apiUrl}/find-all-vehicles`);
  }

  findVehiclesByApartment(apartmentNumber: number): Observable<any[]> {
    return this.http.get<VehicleResponseDTO[]>(`${this.apiUrl}/find-vehicles-apartment/${apartmentNumber}`);
  }

  //VISITANTES (VISITORS)


  createVisitor(visitor: VisitorRequestDTO): Observable<any> {
    return this.http.post<VisitorResponseDTO>(`${this.apiUrl}/create-visitor`, visitor);
  }

  findVisitorsByApartment(apartmentNumber: number): Observable<VisitorResponseDTO[]> {
    return this.http.get<VisitorResponseDTO[]>(`${this.apiUrl}/find-visitors-apartment/${apartmentNumber}`);
  }

  //PAQUETERÍA (PACKAGES)


  createPackage(pkg: PackageRequestDTO): Observable<any> {
    return this.http.post<PackageResponseDTO>(`${this.apiUrl}/create-package`, pkg);
  }


  findPackagesByApartment(apartmentNumber: number): Observable<any[]> {
    return this.http.get<PackageResponseDTO[]>(`${this.apiUrl}/find-package-apartment/${apartmentNumber}`);
  }


  findPackagesNotDelivered(): Observable<any[]> {
    return this.http.get<PackageResponseDTO[]>(`${this.apiUrl}/find-package-not-delivered`);
  }


  markPackageAsDelivered(packageId: number): Observable<any> {
    return this.http.put<PackageResponseDTO>(`${this.apiUrl}/package-delivered/${packageId}`, {});
  }



}
