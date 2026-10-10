package dev.eder.estudiantes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.eder.estudiantes.entity.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdNot(String codigo, Long id);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
