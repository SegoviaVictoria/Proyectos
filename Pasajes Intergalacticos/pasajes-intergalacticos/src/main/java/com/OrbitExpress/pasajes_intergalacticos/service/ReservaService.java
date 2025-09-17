package com.OrbitExpress.pasajes_intergalacticos.service;

import java.util.List;
import java.util.Optional;

import com.OrbitExpress.pasajes_intergalacticos.model.Reserva;
import com.OrbitExpress.pasajes_intergalacticos.model.Usuario;

public interface ReservaService {
    
    List<Reserva> obtenerTodasLasReservas();
    Optional<Reserva> obtenerReservaPorId(Long id);
    Usuario obtenerUsuarioPorReservaId(Long reservaId);
    List<Reserva> obtenerReservasPorViajeId(Long viajeId);
    Reserva crearReserva(Reserva reserva);
    Reserva actualizarReserva(Long id, Reserva reserva);
    void eliminarReserva(Long id);
    Reserva guardarReserva(Reserva reserva);
    List<Reserva> obtenerReservasPorNave(Long naveId);

}
