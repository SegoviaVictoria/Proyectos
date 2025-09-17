package com.OrbitExpress.pasajes_intergalacticos.Service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.OrbitExpress.pasajes_intergalacticos.model.Nave;
import com.OrbitExpress.pasajes_intergalacticos.model.Reserva;
import com.OrbitExpress.pasajes_intergalacticos.model.Usuario;
import com.OrbitExpress.pasajes_intergalacticos.model.Viaje;
import com.OrbitExpress.pasajes_intergalacticos.repository.NaveRepository;
import com.OrbitExpress.pasajes_intergalacticos.repository.ReservaRepository;
import com.OrbitExpress.pasajes_intergalacticos.repository.ViajeRepository;
import com.OrbitExpress.pasajes_intergalacticos.service.impl.ReservaServiceImpl;

public class ReservaServiceImplTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private NaveRepository naveRepository;

    @InjectMocks
    private ReservaServiceImpl reservaService;

    @Mock
    private ViajeRepository viajeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerTodasLasReservas() {
        List<Reserva> mockReservas = Arrays.asList(new Reserva(), new Reserva());
        when(reservaRepository.findAll()).thenReturn(mockReservas);

        List<Reserva> result = reservaService.obtenerTodasLasReservas();

        assertEquals(2, result.size());
        verify(reservaRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerReservaPorId_Existe() {
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        Optional<Reserva> result = reservaService.obtenerReservaPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(reservaRepository, times(1)).findById(1L);
    }

    @Test
    public void testObtenerReservaPorId_NoExiste() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Reserva> result = reservaService.obtenerReservaPorId(1L);

        assertFalse(result.isPresent());
        verify(reservaRepository).findById(1L);
    }

    @Test
    void testCrearReserva() {
        // Datos de prueba
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Nave nave = new Nave();
        nave.setId(1L);

        Viaje viaje = new Viaje();
        viaje.setId(1L);

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setNave(nave);
        reserva.setViaje(viaje);
        reserva.setFechaReserva(LocalDateTime.now());
        reserva.setEstado("PENDIENTE");
        reserva.setFila(3);
        reserva.setColumna(4);

        // Configurar mocks
        when(naveRepository.findById(1L)).thenReturn(Optional.of(nave));
        when(viajeRepository.findById(1L)).thenReturn(Optional.of(viaje));
        when(reservaRepository.existsByViajeIdAndFilaAndColumna(1L, 3, 4)).thenReturn(false);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        // Ejecutar el método
        Reserva resultado = reservaService.crearReserva(reserva);

        // Verificar resultados
        assertNotNull(resultado);
        assertEquals(1L, resultado.getNave().getId());
        assertEquals(1L, resultado.getViaje().getId());
        assertEquals(3, resultado.getFila());
        assertEquals(4, resultado.getColumna());
        assertEquals("PENDIENTE", resultado.getEstado());

        // Verificar interacciones con los mocks
        verify(naveRepository).findById(1L);
        verify(viajeRepository).findById(1L);
        verify(reservaRepository).existsByViajeIdAndFilaAndColumna(1L, 3, 4);
        verify(reservaRepository).save(reserva);
    }

    @Test
    public void testCrearReserva_AsientoOcupado() {
        Reserva reserva = new Reserva();
        reserva.setFila(1);
        reserva.setColumna(1);
        reserva.setUsuario(new Usuario());

        Viaje viaje = new Viaje();
        viaje.setId(20L);
        reserva.setViaje(viaje);

        when(reservaRepository.existsByViajeIdAndFilaAndColumna(20L, 1, 1)).thenReturn(true);

        Reserva result = reservaService.crearReserva(reserva);

        assertNull(result); // Debe ser null si el asiento está ocupado
        verify(reservaRepository, never()).save(any());
    }

    @Test
    public void testEliminarReserva() {
        Long id = 2L;
        reservaService.eliminarReserva(id);
        verify(reservaRepository, times(1)).deleteById(id);
    }
}
