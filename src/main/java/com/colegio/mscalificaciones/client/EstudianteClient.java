package com.colegio.mscalificaciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-estudiantes", url = "${ms.estudiantes.url}")
public interface EstudianteClient {

    @GetMapping("/api/estudiantes/{id}")
    Object obtenerPorId(@PathVariable("id") Long id);
}