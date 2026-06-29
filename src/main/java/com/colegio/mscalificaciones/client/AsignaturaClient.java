package com.colegio.mscalificaciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-asignaturas", url = "${ms.asignaturas.url}")
public interface AsignaturaClient {

    @GetMapping("/api/asignaturas/{id}")
    Object obtenerPorId(@PathVariable("id") Long id);
}