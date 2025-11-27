import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResidentDashboardDTO } from '../components/dashboard/resident-dashboard/resident-dashboard.interface';

@Injectable({
  providedIn: 'root'
})
export class ResidentService {
    private http = inject(HttpClient);
    private apiUrl = 'http://localhost:8080/resident';

    getResidentDashboardData(): Observable<any> {
        return this.http.get<ResidentDashboardDTO>(`${this.apiUrl}/dashboard-data-resident`);
      }

}
