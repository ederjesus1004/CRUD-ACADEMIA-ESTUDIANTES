package dev.eder.estudiantes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.eder.estudiantes.dto.EstudianteRequest;
import dev.eder.estudiantes.dto.EstudianteResponse;
import dev.eder.estudiantes.service.EstudianteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteService estudianteService;

    @GetMapping
    public List<EstudianteResponse> listar() {
        return estudianteService.listar();
    }

    @GetMapping("/{id}")
    public EstudianteResponse obtenerPorId(@PathVariable Long id) {
        return estudianteService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstudianteResponse crear(@Valid @RequestBody EstudianteRequest request) {
        return estudianteService.crear(request);
    }

    @PutMapping("/{id}")
    public EstudianteResponse actualizar(@PathVariable long id, @Valid @RequestBody EstudianteRequest request) {
        return estudianteService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
    }
}
