package com.OrbitExpress.pasajes_intergalacticos.Service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.OrbitExpress.pasajes_intergalacticos.model.Nave;
import com.OrbitExpress.pasajes_intergalacticos.model.Reserva;
import com.OrbitExpress.pasajes_intergalacticos.model.Usuario;
import com.OrbitExpress.pasajes_intergalacticos.model.Viaje;
import com.OrbitExpress.pasajes_intergalacticos.repository.NaveRepository;
import com.OrbitExpress.pasajes_intergalacticos.service.impl.NaveServiceImpl;

public class NaveServiceImplTest {

    @Mock
    private NaveRepository naveRepository;

    @InjectMocks
    private NaveServiceImpl naveService;

    @BeforeEach
    public void setUp1() {
        MockitoAnnotations.openMocks(this);
    }

    private Nave nave;
    private Usuario usuario;
    private Viaje viaje;

    @BeforeEach
    public void setUp2() {
        // Crear la nave con 3 filas y 4 columnas
        nave = new Nave();
        nave.setFilas(3);
        nave.setColumnas(4);
        
        // Crear un usuario de ejemplo
        usuario = new Usuario(); // Asegúrate de inicializar el usuario correctamente
        usuario.setId(1L);
        usuario.setNombre("Juan");

        // Crear un viaje de ejemplo
        viaje = new Viaje(); // Asegúrate de inicializar el viaje correctamente
        viaje.setId(1L);
        
        // Inicializar la lista de reservas
        nave.setDisposicionAsientos(new ArrayList<>());
    }

    @Test
    public void testObtenerTodasLasNaves() {
        List<Nave> mockNaves = Arrays.asList(new Nave(), new Nave());
        when(naveRepository.findAll()).thenReturn(mockNaves);

        List<Nave> result = naveService.obtenerTodasLasNaves();

        assertEquals(2, result.size());
        verify(naveRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerNavePorId_Existe() {
        Nave nave = new Nave();
        nave.setId(1L);
        when(naveRepository.findById(1L)).thenReturn(Optional.of(nave));

        Optional<Nave> result = naveService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(naveRepository, times(1)).findById(1L);
    }

    @Test
    public void testObtenerNavePorId_NoExiste() {
        when(naveRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Nave> result = naveService.findById(1L);

        assertFalse(result.isPresent());
        verify(naveRepository).findById(1L);
    }

    @Test
    public void testCrearNave() {
        Nave nave = new Nave();
        nave.setNombre("GalaxiaX");

        when(naveRepository.save(nave)).thenReturn(nave);

        Nave result = naveService.crearNave(nave);

        assertNotNull(result);
        assertEquals("GalaxiaX", result.getNombre());
        verify(naveRepository, times(1)).save(nave);
    }

    @Test
    public void testEliminarNave() {
        Long id = 1L;

        naveService.eliminarNave(id);

        verify(naveRepository, times(1)).deleteById(id);
    }

    @Test
    public void testObtenerNavePorNombre() {
        Nave nave = new Nave();
        nave.setNombre("EstrellaCruiser");

        when(naveRepository.findByNombre("EstrellaCruiser")).thenReturn(Optional.of(nave));

        Optional<Nave> result = naveService.findByNombre("EstrellaCruiser");

        assertTrue(result.isPresent());
        assertEquals("EstrellaCruiser", result.get().getNombre());
        verify(naveRepository).findByNombre("EstrellaCruiser");
    }

    @Test
    public void testGenerarDisposicionAsientos() {
        // Crear reservas para algunos asientos
        Reserva reserva1 = new Reserva(usuario, viaje, nave, LocalDateTime.now(), "PENDIENTE", 1, 2);
        Reserva reserva2 = new Reserva(usuario, viaje, nave, LocalDateTime.now(), "PENDIENTE", 2, 3);
        
        // Asignar las reservas a la nave
        nave.getDisposicionAsientos().add(reserva1);
        nave.getDisposicionAsientos().add(reserva2);
        
        // Verificar que las reservas se han agregado correctamente
        assertEquals(2, nave.getDisposicionAsientos().size(), "La cantidad de reservas debería ser 2.");
        
        // Verificar que las reservas tienen las coordenadas correctas
        assertEquals(1, nave.getDisposicionAsientos().get(0).getFila(), "La fila de la primera reserva debería ser 1.");
        assertEquals(2, nave.getDisposicionAsientos().get(0).getColumna(), "La columna de la primera reserva debería ser 2.");
        
        assertEquals(2, nave.getDisposicionAsientos().get(1).getFila(), "La fila de la segunda reserva debería ser 2.");
        assertEquals(3, nave.getDisposicionAsientos().get(1).getColumna(), "La columna de la segunda reserva debería ser 3.");
    }

    @Test
    public void testAsignarReservaAAsiento() {
        // Crear una reserva para el asiento (1, 1)
        Reserva reserva = new Reserva(usuario, viaje, nave, LocalDateTime.now(), "PENDIENTE", 1, 1);
        
        // Asignar la reserva a la nave
        nave.getDisposicionAsientos().add(reserva);

        // Verificar que el asiento (1, 1) tiene una reserva
        assertTrue(nave.getDisposicionAsientos().stream().anyMatch(r -> r.getFila() == 1 && r.getColumna() == 1), 
                   "El asiento (1, 1) debería estar reservado.");
    }

    @Test
    public void testEstadoDeAsiento() {
        // Crear una reserva
        Reserva reserva = new Reserva(usuario, viaje, nave, LocalDateTime.now(), "CONFIRMADA", 3, 4);
        
        // Asignar la reserva a la nave
        nave.getDisposicionAsientos().add(reserva);

        // Verificar el estado de la reserva
        assertEquals("CONFIRMADA", nave.getDisposicionAsientos().get(0).getEstado(), "El estado de la reserva debería ser 'CONFIRMADA'.");
    }

}
