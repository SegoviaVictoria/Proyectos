package com.OrbitExpress.pasajes_intergalacticos.Service.impl;

import com.OrbitExpress.pasajes_intergalacticos.model.Viaje;
import com.OrbitExpress.pasajes_intergalacticos.repository.ViajeRepository;
import com.OrbitExpress.pasajes_intergalacticos.service.impl.ViajeServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ViajeServiceImplTest {

    @Mock
    private ViajeRepository viajeRepository;

    @InjectMocks
    private ViajeServiceImpl viajeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerTodosLosViajes() {
        List<Viaje> mockViajes = Arrays.asList(new Viaje(), new Viaje());
        when(viajeRepository.findAll()).thenReturn(mockViajes);

        List<Viaje> result = viajeService.obtenerTodosLosViajes();

        assertEquals(2, result.size());
        verify(viajeRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerViajePorId_Existe() {
        Viaje viaje = new Viaje();
        viaje.setId(1L);
        when(viajeRepository.findById(1L)).thenReturn(Optional.of(viaje));

        Optional<Viaje> result = viajeService.obtenerViajePorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(viajeRepository).findById(1L);
    }

    @Test
    public void testObtenerViajePorId_NoExiste() {
        when(viajeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Viaje> result = viajeService.obtenerViajePorId(1L);

        assertFalse(result.isPresent());
        verify(viajeRepository).findById(1L);
    }

    @Test
    public void testCrearViaje() {
        Viaje viaje = new Viaje();
        viaje.setId(5L);

        when(viajeRepository.save(viaje)).thenReturn(viaje);

        Viaje result = viajeService.crearViaje(viaje);

        assertNotNull(result);
        assertEquals(5L, result.getId());
        verify(viajeRepository, times(1)).save(viaje);
    }

    @Test
    public void testEliminarViaje() {
        Long id = 2L;

        viajeService.eliminarViaje(id);

        verify(viajeRepository, times(1)).deleteById(id);
    }
}
