package com.OrbitExpress.pasajes_intergalacticos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OrbitExpress.pasajes_intergalacticos.model.Nave;
import com.OrbitExpress.pasajes_intergalacticos.model.Reserva;
import com.OrbitExpress.pasajes_intergalacticos.model.Usuario;
import com.OrbitExpress.pasajes_intergalacticos.model.Viaje;
import com.OrbitExpress.pasajes_intergalacticos.repository.NaveRepository;
import com.OrbitExpress.pasajes_intergalacticos.repository.ReservaRepository;
import com.OrbitExpress.pasajes_intergalacticos.repository.ViajeRepository;
import com.OrbitExpress.pasajes_intergalacticos.service.ReservaService;

/**
 * Implementación del servicio de gestión de reservas.
 * Proporciona operaciones CRUD, validación de asientos y gestión de relaciones con nave, viaje y usuario.
 */
@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private final ReservaRepository reservaRepository;

    @Autowired
    private NaveRepository naveRepository;

    @Autowired
    private ViajeRepository viajeRepository;

    /**
     * Obtiene la lista de todas las reservas.
     * @return lista de objetos Reserva
     */
    @Override
    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepository.findAll();
    }

    /**
     * Busca una reserva por su ID.
     * @param id identificador de la reserva
     * @return Optional con la reserva encontrada o vacío si no existe
     */
    @Override
    public Optional<Reserva> obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id);
    }

    /**
     * Obtiene el usuario asociado a una reserva.
     * @param reservaId identificador de la reserva
     * @return usuario asociado o null si no existe la reserva
     */
    @Override
    public Usuario obtenerUsuarioPorReservaId(Long reservaId) {
        return reservaRepository.findById(reservaId)
                .map(Reserva::getUsuario)
                .orElse(null);
    }

    /**
     * Obtiene todas las reservas asociadas a un viaje.
     * @param viajeId identificador del viaje
     * @return lista de reservas del viaje
     */
    @Override
    public List<Reserva> obtenerReservasPorViajeId(Long viajeId) {
        return reservaRepository.findByViajeId(viajeId);
    }

    /**
     * Verifica si un asiento está ocupado en un viaje.
     * @param fila fila del asiento
     * @param columna columna del asiento
     * @param viajeId identificador del viaje
     * @return true si el asiento está ocupado, false en caso contrario
     */
    private boolean isAsientoOcupado(int fila, int columna, Long viajeId) {
        return reservaRepository.existsByViajeIdAndFilaAndColumna(viajeId, fila, columna);
    }

    /**
     * Crea una nueva reserva de asiento, validando la existencia de nave, viaje y disponibilidad del asiento.
     * @param reservaAsiento objeto Reserva a crear
     * @return reserva creada
     */
    @Override
    public Reserva crearReserva(Reserva reservaAsiento) {

        System.out.println("Reserva recibida: " + reservaAsiento);

        if (reservaAsiento.getNave().getId() == null) {
            throw new IllegalArgumentException("La nave no está especificada.");
        }

        Optional<Nave> naveOptional = naveRepository.findById(reservaAsiento.getNave().getId());
        if (!naveOptional.isPresent()) {
            throw new IllegalArgumentException("La nave con ID " + reservaAsiento.getNave().getId() + " no existe.");
        }
        reservaAsiento.setNave(naveOptional.get());

        if (reservaAsiento.getViaje() == null || reservaAsiento.getViaje().getId() == null) {
            throw new IllegalArgumentException("El viaje no está especificado.");
        }

        Optional<Viaje> viajeOptional = viajeRepository.findById(reservaAsiento.getViaje().getId());
        if (!viajeOptional.isPresent()) {
            throw new IllegalArgumentException("El viaje con ID " + reservaAsiento.getViaje().getId() + " no existe.");
        }

        if (isAsientoOcupado(reservaAsiento.getFila(), reservaAsiento.getColumna(),
                reservaAsiento.getViaje().getId())) {
            throw new IllegalArgumentException("El asiento ya está ocupado.");
        }
        reservaAsiento.setNave(naveOptional.get());

        return reservaRepository.save(reservaAsiento);
    }

    /**
     * Guarda o actualiza una reserva.
     * @param reserva objeto Reserva a guardar
     * @return reserva guardada
     */
    @Override
    public Reserva guardarReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    /**
     * Elimina una reserva por su ID.
     * @param id identificador de la reserva a eliminar
     */
    @Override
    public void eliminarReserva(Long id) {
        reservaRepository.deleteById(id);
    }

    /**
     * Constructor para inyección de dependencias.
     * @param reservaRepository repositorio de reservas
     */
    public ReservaServiceImpl(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    /**
     * Obtiene todas las reservas asociadas a una nave.
     * @param naveId identificador de la nave
     * @return lista de reservas de la nave
     */
    @Override
    public List<Reserva> obtenerReservasPorNave(Long naveId) {
        return reservaRepository.findReservasByNaveId(naveId);
    }

    /**
     * Actualiza una reserva existente.
     * @param id identificador de la reserva a actualizar
     * @param reserva datos actualizados de la reserva
     * @return reserva actualizada
     */
    @Override
    public Reserva actualizarReserva(Long id, Reserva reserva) {
        
        Optional<Reserva> reservaExistente = reservaRepository.findById(id);
        if (!reservaExistente.isPresent()) {
            throw new IllegalArgumentException("La reserva con ID " + id + " no existe.");
        }

        Reserva reservaActualizada = reservaExistente.get();

        if (reserva.getUsuario() != null && reserva.getUsuario().getId() != null) {
            reservaActualizada.setUsuario(reserva.getUsuario());
        } else {
            throw new IllegalArgumentException("El usuario asociado a la reserva no es válido.");
        }

        if (reserva.getViaje() != null && reserva.getViaje().getId() != null) {
            reservaActualizada.setViaje(reserva.getViaje());
        } else {
            throw new IllegalArgumentException("El viaje asociado a la reserva no es válido.");
        }

        reservaActualizada.setFila(reserva.getFila());
        reservaActualizada.setColumna(reserva.getColumna());
        reservaActualizada.setEstado(reserva.getEstado());

        return reservaRepository.save(reservaActualizada);
    }

}
