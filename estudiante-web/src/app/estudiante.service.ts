import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EstudianteRequest, EstudianteResponse } from './estudiante.model';

@Injectable({ providedIn: 'root' })
export class EstudianteService {

  private readonly http = inject(HttpClient);
  private readonly url = 'http://localhost:8080/api/estudiantes';

  listar(): Observable<EstudianteResponse[]> {
    return this.http.get<EstudianteResponse[]>(this.url);
  }

  crear(request: EstudianteRequest): Observable<EstudianteResponse> {
    return this.http.post<EstudianteResponse>(this.url, request);
  }

  actualizar(id: number, request: EstudianteRequest): Observable<EstudianteResponse> {
    return this.http.put<EstudianteResponse>(`${this.url}/${id}`, request);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}