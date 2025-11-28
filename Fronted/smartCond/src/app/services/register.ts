import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CeladorRequestDTO, NoticeRequestDTO, ResidentRequestDTO } from '../components/dashboard/admin-dashboard/admin-dashboard.interface';
import { CeladorResponseDTO } from '../components/dashboard/celador-dashboard/celador-dashboard.interface';
import { ResidentResponseDTO } from '../components/dashboard/resident-dashboard/resident-dashboard.interface';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/admin';


  //Celador
  getCeladorById(id: number): Observable<CeladorResponseDTO> {
    return this.http.get<CeladorResponseDTO>(`${this.apiUrl}/find-celador-byID/${id}`);
  }

  getAllCeladors(): Observable<any[]> {
  return this.http.get<CeladorResponseDTO[]>(`${this.apiUrl}/find-all-celador`);
  }
  createCelador(celador: CeladorRequestDTO): Observable<any> {
    return this.http.post(`${this.apiUrl}/create-celador`, celador);
  }

  updateCelador(id: number, data: CeladorRequestDTO): Observable<any> {
    return this.http.put(`${this.apiUrl}/update-celador/${id}`, data);
  }

  deleteCelador(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete-celador/${id}`);
  }

  //Resident

  getResidentById(id: number): Observable<ResidentResponseDTO> {
    return this.http.get<ResidentResponseDTO>(`${this.apiUrl}/find-resident-byID/${id}`);
  }

  getAllResidents(): Observable<any[]> {
  return this.http.get<ResidentResponseDTO[]>(`${this.apiUrl}/find-all-resident`);
}

  createResident(resident: ResidentRequestDTO): Observable<any> {
    return this.http.post(`${this.apiUrl}/create-resident`, resident);
  }

  updateResident(id: number, data: ResidentRequestDTO): Observable<any> {
    return this.http.put(`${this.apiUrl}/update-resident/${id}`, data);
  }

  deleteResident(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete-resident/${id}`);
  }

  //Notices
  createNotice(noticeData: NoticeRequestDTO): Observable<any> {
      return this.http.post(`${this.apiUrl}/create-notice`, noticeData);
  }
  getAllNotices(): Observable<any[]> {
  return this.http.get<any[]>(`${this.apiUrl}/find-all-notices`);
}

  getNoticeById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/find-notice-byID/${id}`);
  }

  updateNotice(id: number, data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/update-notice/${id}`, data);
  }

  deleteNotice(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete-notice/${id}`);
  }



}
