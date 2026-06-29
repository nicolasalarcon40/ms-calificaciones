package com.colegio.mscalificaciones.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CalificacionResponseDTO {
    private Long id;
    private Double nota;
    private LocalDate fecha;
    private Long idEstudiante;
    private Long idAsignatura;
}