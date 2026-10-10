package dev.eder.estudiantes.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record EstudianteResponse(
        Long id,
        String codigo,
        String nombres,
        String apellidos,
        String descripcion,
        String email,
        String carrera,
        Integer ciclo,
        LocalDateTime fechaRegistro) {

}
