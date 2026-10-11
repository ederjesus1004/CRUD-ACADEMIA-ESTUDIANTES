import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EstudianteService } from './estudiante.service';
import { ErrorResponse, EstudianteRequest, EstudianteResponse } from './estudiante.model';

@Component({
  selector: 'app-root',
  imports: [FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {

  private readonly estudianteService = inject(EstudianteService);

  // Signals: cuando cambian, la pantalla se redibuja
  estudiantes = signal<EstudianteResponse[]>([]);
  editandoId = signal<number | null>(null);    // null = creando, número = editando ese id
  mensajeError = signal('');
  erroresCampo = signal<Record<string, string>>({});

  // Lo que el usuario escribe en el formulario
  form: EstudianteRequest = this.formVacio();

  // Angular lo llama solo, una vez, cuando la pantalla aparece
  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.estudianteService.listar().subscribe({
      next: (lista) => this.estudiantes.set(lista),
      error: (err: HttpErrorResponse) => this.mostrarError(err)
    });
  }

  guardar(): void {
    this.limpiarErrores();
    const id = this.editandoId();

    let peticion: Observable<EstudianteResponse>;
    if (id === null) {
      peticion = this.estudianteService.crear(this.form);           // POST
    } else {
      peticion = this.estudianteService.actualizar(id, this.form);  // PUT
    }

    peticion.subscribe({
      next: () => {
        this.cancelar();   // limpia el formulario
        this.cargar();     // pide la lista otra vez
      },
      error: (err: HttpErrorResponse) => this.mostrarError(err)
    });
  }

  editar(e: EstudianteResponse): void {
    this.limpiarErrores();
    this.editandoId.set(e.id);
    this.form = {
      codigo: e.codigo,
      nombres: e.nombres,
      apellidos: e.apellidos,
      email: e.email,
      carrera: e.carrera,
      ciclo: e.ciclo
    };
  }

  eliminar(e: EstudianteResponse): void {
    if (!confirm('¿Eliminar a ' + e.nombres + ' ' + e.apellidos + '?')) {
      return;
    }
    this.estudianteService.eliminar(e.id).subscribe({
      next: () => this.cargar(),
      error: (err: HttpErrorResponse) => this.mostrarError(err)
    });
  }

  cancelar(): void {
    this.editandoId.set(null);
    this.form = this.formVacio();
    this.limpiarErrores();
  }

  private mostrarError(err: HttpErrorResponse): void {
    // status 0 = no llegó a Spring (apagado o CORS mal configurado)
    if (err.status === 0) {
      this.mensajeError.set('No hay conexión con la API. Revisa que Spring Boot esté corriendo.');
      return;
    }
    // Aquí llega el ErrorResponse de tu GlobalExceptionHandler
    const cuerpo = err.error as ErrorResponse;
    this.mensajeError.set(cuerpo.mensaje);
    this.erroresCampo.set(cuerpo.errores ?? {});
  }

  private limpiarErrores(): void {
    this.mensajeError.set('');
    this.erroresCampo.set({});
  }

  private formVacio(): EstudianteRequest {
    return { codigo: '', nombres: '', apellidos: '', email: '', carrera: '', ciclo: null };
  }
}