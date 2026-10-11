package dev.eder.estudiantes.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.eder.estudiantes.dto.EstudianteRequest;
import dev.eder.estudiantes.dto.EstudianteResponse;
import dev.eder.estudiantes.entity.Estudiante;
import dev.eder.estudiantes.exception.RecursoDuplicadoException;
import dev.eder.estudiantes.exception.RecursoNoEncontradoException;
import dev.eder.estudiantes.mapper.EstudianteMapper;
import dev.eder.estudiantes.repository.EstudianteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteResponse> listar() {
        return estudianteRepository.findAll()
                .stream()
                .map(EstudianteMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EstudianteResponse obtenerPorId(Long id) {
        return EstudianteMapper.toResponse(buscarEstudiante(id));
    }

    @Override
    @Transactional
    public EstudianteResponse crear(EstudianteRequest request) {
        String email = EstudianteMapper.normalizarEmail(request.email());
        if (estudianteRepository.existsByCodigo(request.codigo())) {
            throw new RecursoDuplicadoException("Ya existe un estudiante con este codigo" + request.codigo());
        }
        if (estudianteRepository.existsByEmail(email)) {
            throw new RecursoDuplicadoException("Ya existe un estudiante con el email " + email);
        }
        Estudiante estudiante = EstudianteMapper.toEntity(request);
        return EstudianteMapper.toResponse(estudianteRepository.save(estudiante));

    }

    @Override
    @Transactional
    public EstudianteResponse actualizar(Long id, EstudianteRequest request) {
        Estudiante estudiante = buscarEstudiante(id);
        String email = EstudianteMapper.normalizarEmail(request.email());

        if (estudianteRepository.existsByCodigoAndIdNot(request.codigo(), id)) {
            throw new RecursoDuplicadoException("Ya existe un estudiante con este codigo" + request.codigo());
        }
        if (estudianteRepository.existsByEmailAndIdNot(email, id)) {
            throw new RecursoDuplicadoException("Ya existe un estudiante con el email " + email);
        }
        EstudianteMapper.actualizarEntidad(request, estudiante);
        return EstudianteMapper.toResponse(estudianteRepository.save(estudiante));

    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Estudiante estudiante = buscarEstudiante(id);
        estudianteRepository.delete(estudiante);
    }

    private Estudiante buscarEstudiante(Long id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe estudiante con id: " + id));
    }

}
