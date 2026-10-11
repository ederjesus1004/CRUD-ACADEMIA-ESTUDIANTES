// Lo que Angular ENVÍA (igual a EstudianteRequest)
export interface EstudianteRequest {
  codigo: string;
  nombres: string;
  apellidos: string;
  email: string;
  carrera: string;
  ciclo: number | null;     // null = todavía no escribió nada
}

// Lo que Angular RECIBE (igual a EstudianteResponse)
export interface EstudianteResponse {
  id: number;
  codigo: string;
  nombres: string;
  apellidos: string;
  descripcion: string;
  email: string;
  carrera: string;
  ciclo: number;
  fechaRegistro: string;    // LocalDateTime llega como texto
}

// Lo que llega cuando hay error (igual a tu ErrorResponse)
export interface ErrorResponse {
  fecha: string;
  estado: number;
  mensaje: string;
  errores: Record<string, string>;   // como Map<String, String>
}