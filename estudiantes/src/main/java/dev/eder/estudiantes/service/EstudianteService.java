package dev.eder.estudiantes.service;

import java.util.List;

import dev.eder.estudiantes.dto.EstudianteRequest;
import dev.eder.estudiantes.dto.EstudianteResponse;

public interface EstudianteService {

    List<EstudianteResponse> listar();

    EstudianteResponse obtenerPorId(Long id);

    EstudianteResponse crear(EstudianteRequest request);

    EstudianteResponse actualizar(Long id, EstudianteRequest request);

    void eliminar(Long id);
}
