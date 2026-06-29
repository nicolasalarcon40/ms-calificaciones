package com.colegio.mscalificaciones.service;

import com.colegio.mscalificaciones.client.AsignaturaClient;
import com.colegio.mscalificaciones.client.EstudianteClient;
import com.colegio.mscalificaciones.dto.CalificacionRequestDTO;
import com.colegio.mscalificaciones.dto.CalificacionResponseDTO;
import com.colegio.mscalificaciones.exception.ResourceNotFoundException;
import com.colegio.mscalificaciones.model.Calificacion;
import com.colegio.mscalificaciones.repository.CalificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalificacionService {

    private final CalificacionRepository repository;
    private final EstudianteClient estudianteClient;
    private final AsignaturaClient asignaturaClient;

    public CalificacionResponseDTO crear(CalificacionRequestDTO request) {
    
        estudianteClient.obtenerPorId(request.getIdEstudiante());
        asignaturaClient.obtenerPorId(request.getIdAsignatura());

        Calificacion entidad = new Calificacion();
        entidad.setNota(request.getNota());
        entidad.setFecha(request.getFecha());
    
        entidad.setIdEstudiante(request.getIdEstudiante());
        entidad.setIdAsignatura(request.getIdAsignatura());

        Calificacion guardada = repository.save(entidad);
        return mapearDTO(guardada);
    }

    public List<CalificacionResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(this::mapearDTO)
                .collect(Collectors.toList());
    }

    public CalificacionResponseDTO actualizar(Long id, CalificacionRequestDTO request) {
        Calificacion entidad = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID de calificacion no encontrado: " + id));


        estudianteClient.obtenerPorId(request.getIdEstudiante());
        asignaturaClient.obtenerPorId(request.getIdAsignatura());

        entidad.setNota(request.getNota());
        entidad.setFecha(request.getFecha());
        entidad.setIdEstudiante(request.getIdEstudiante());
        entidad.setIdAsignatura(request.getIdAsignatura());

        Calificacion actualizada = repository.save(entidad);
        return mapearDTO(actualizada);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ID de calificacion no encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private CalificacionResponseDTO mapearDTO(Calificacion entidad) {
        CalificacionResponseDTO dto = new CalificacionResponseDTO();
        dto.setId(entidad.getId());
        dto.setNota(entidad.getNota());
        dto.setFecha(entidad.getFecha());
        // 3. MAPEAMOS EL ID AL DTO DE RESPUESTA
        dto.setIdEstudiante(entidad.getIdEstudiante());
        dto.setIdAsignatura(entidad.getIdAsignatura());
        return dto;
    }
}