import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AdminDashboardDTO } from '../admin-dashboard/admin-dashboard.interface'; // Importar la interfaz
import { NoticeRequestDTO } from '../admin-dashboard/admin-dashboard.interface';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/admin';

  constructor() { }

  getAdminDashboardData(): Observable<AdminDashboardDTO> {
    return this.http.get<AdminDashboardDTO>(`${this.apiUrl}/dashboard-data`);
  }

  createNotice(noticeData: NoticeRequestDTO): Observable<any> {
    return this.http.post(`${this.apiUrl}/create-notice`, noticeData);
  }
}
