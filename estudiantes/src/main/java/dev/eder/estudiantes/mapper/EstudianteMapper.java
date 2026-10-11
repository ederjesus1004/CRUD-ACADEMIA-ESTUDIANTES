package dev.eder.estudiantes.mapper;

import dev.eder.estudiantes.dto.EstudianteRequest;
import dev.eder.estudiantes.dto.EstudianteResponse;
import dev.eder.estudiantes.entity.Estudiante;

public final class EstudianteMapper {

    private EstudianteMapper() {
    }

    public static Estudiante toEntity(EstudianteRequest request) {
        Estudiante estudiante = new Estudiante();
        actualizarEntidad(request, estudiante);
        return estudiante;
    }

    public static void actualizarEntidad(EstudianteRequest request, Estudiante estudiante) {
        estudiante.setCodigo(request.codigo().trim());
        estudiante.setNombres(request.nombres().trim());
        estudiante.setApellidos(request.apellidos().trim());
        estudiante.setEmail(normalizarEmail(request.email()));
        estudiante.setCarrera(request.carrera().trim());
        estudiante.setCiclo(request.ciclo());
    }

    public static EstudianteResponse toResponse(Estudiante estudiante) {
        return EstudianteResponse.builder()
                .id(estudiante.getId())
                .codigo(estudiante.getCodigo())
                .nombres(estudiante.getNombres())
                .apellidos(estudiante.getApellidos())
                .descripcion(
                        estudiante.getNombres() + " " + estudiante.getApellidos() + " - " + estudiante.getCarrera())
                .email(estudiante.getEmail())
                .ciclo(estudiante.getCiclo())
                .fechaRegistro(estudiante.getFechaRegistro())
                .build();
    }

    private static String normalizarEmail(String email) {
        return email.trim().toLowerCase();
    }
}
