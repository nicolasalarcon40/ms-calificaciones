package com.colegio.mscalificaciones.controller;

import com.colegio.mscalificaciones.dto.CalificacionRequestDTO;
import com.colegio.mscalificaciones.dto.CalificacionResponseDTO;
import com.colegio.mscalificaciones.service.CalificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calificaciones")
@RequiredArgsConstructor
public class CalificacionController {

    private final CalificacionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CalificacionResponseDTO crear(@RequestBody CalificacionRequestDTO request) {
        return service.crear(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CalificacionResponseDTO> listar() {
        return service.listarTodas();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CalificacionResponseDTO actualizar(@PathVariable Long id, @RequestBody CalificacionRequestDTO request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}