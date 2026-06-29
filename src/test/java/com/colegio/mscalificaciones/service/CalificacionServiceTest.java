package com.colegio.mscalificaciones.service;

import com.colegio.mscalificaciones.client.AsignaturaClient;
import com.colegio.mscalificaciones.client.EstudianteClient;
import com.colegio.mscalificaciones.dto.CalificacionRequestDTO;
import com.colegio.mscalificaciones.dto.CalificacionResponseDTO;
import com.colegio.mscalificaciones.exception.ResourceNotFoundException;
import com.colegio.mscalificaciones.model.Calificacion;
import com.colegio.mscalificaciones.repository.CalificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalificacionServiceTest {

    @Mock
    private CalificacionRepository repository;

    @Mock
    private EstudianteClient estudianteClient;

    @Mock
    private AsignaturaClient asignaturaClient;

    @InjectMocks
    private CalificacionService calificacionService;

    private Calificacion calificacion;
    private CalificacionRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        calificacion = new Calificacion(1L, 6.5, LocalDate.of(2026, 6, 29), 1L, 1L);

        requestDTO = new CalificacionRequestDTO();
        requestDTO.setNota(6.5);
        requestDTO.setFecha(LocalDate.of(2026, 6, 29));
        requestDTO.setIdEstudiante(1L);
        requestDTO.setIdAsignatura(1L);
    }

  
    @Test
    void testCrear_debeRetornarResponseDTO() {
        
        when(estudianteClient.obtenerPorId(1L)).thenReturn(new Object());
        when(asignaturaClient.obtenerPorId(1L)).thenReturn(new Object());
        when(repository.save(any(Calificacion.class))).thenReturn(calificacion);

       
        CalificacionResponseDTO resultado = calificacionService.crear(requestDTO);

   
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(6.5, resultado.getNota());
        assertEquals(1L, resultado.getIdEstudiante());
        assertEquals(1L, resultado.getIdAsignatura());
        verify(repository, times(1)).save(any(Calificacion.class));
    }

  
    @Test
    void testListarTodas_debeRetornarLista() {
        
        Calificacion calificacion2 = new Calificacion(2L, 5.0, LocalDate.of(2026, 6, 28), 2L, 2L);
        when(repository.findAll()).thenReturn(List.of(calificacion, calificacion2));

        // Act
        List<CalificacionResponseDTO> resultado = calificacionService.listarTodas();

       
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(6.5, resultado.get(0).getNota());
        assertEquals(5.0, resultado.get(1).getNota());
        verify(repository, times(1)).findAll();
    }


    @Test
    void testEliminar_cuandoNoExiste_debeLanzarExcepcion() {
        
        when(repository.existsById(99L)).thenReturn(false);

        
        ResourceNotFoundException ex = assertThrows(
            ResourceNotFoundException.class,
            () -> calificacionService.eliminar(99L)
        );

        assertEquals("ID de calificacion no encontrado: 99", ex.getMessage());
        verify(repository, never()).deleteById(any());
    }
}