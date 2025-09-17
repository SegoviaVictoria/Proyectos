package com.OrbitExpress.pasajes_intergalacticos.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.OrbitExpress.pasajes_intergalacticos.model.Reserva;
import com.OrbitExpress.pasajes_intergalacticos.model.Usuario;
import com.OrbitExpress.pasajes_intergalacticos.service.ReservaService;

/**
 * Controlador REST para la gestión de reservas.
 * Proporciona endpoints para CRUD, consulta por viaje, nave y usuario, y actualización parcial del estado.
 */
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    /**
     * Obtiene la lista de todas las reservas.
     * @return lista de objetos Reserva
     */
    @GetMapping
    public List<Reserva> obtenerTodasLasReservas() {
        return reservaService.obtenerTodasLasReservas();
    }

    /**
     * Obtiene una reserva por su ID.
     * @param id identificador de la reserva
     * @return objeto Reserva o null si no existe
     */
    @GetMapping("/{id}")
    public Reserva obtenerReservaPorId(@PathVariable Long id) {
        return reservaService.obtenerReservaPorId(id).orElse(null);
    }

    /**
     * Obtiene el usuario asociado a una reserva.
     * @param reservaId identificador de la reserva
     * @return objeto Usuario o 404 si no existe
     */
    @GetMapping("/{reservaId}/usuario")
    public ResponseEntity<Usuario> obtenerUsuarioPorReservaId(@PathVariable Long reservaId) {
        Usuario usuario = reservaService.obtenerUsuarioPorReservaId(reservaId);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    /**
     * Crea una nueva reserva.
     * @param reserva objeto Reserva a crear
     * @return reserva creada
     */
    @PostMapping
    public Reserva crearReserva(@RequestBody Reserva reserva) {
        return reservaService.crearReserva(reserva);
    }

    /**
     * Actualiza completamente una reserva existente.
     * @param id identificador de la reserva a actualizar
     * @param reserva datos actualizados de la reserva
     * @return reserva actualizada
     */
    @PutMapping("/{id}")
    public Reserva actualizarReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        Optional<Reserva> reservaExistente = reservaService.obtenerReservaPorId(id);
        if (!reservaExistente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada");
        }
        reserva.setId(id);
        return reservaService.actualizarReserva(id, reserva);
    }

    /**
     * Actualiza parcialmente el estado de una reserva.
     * @param id identificador de la reserva
     * @param updates mapa con los campos a actualizar (por ejemplo, "estado")
     * @return reserva actualizada o error si el campo es inválido
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarEstadoReserva(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        ResponseEntity<?> respuesta;
        Optional<Reserva> reservaOpt = reservaService.obtenerReservaPorId(id);

        if (reservaOpt.isEmpty()) {
            respuesta = ResponseEntity.notFound().build();
        } else {
            Reserva reserva = reservaOpt.get();
            String estado = (String) updates.get("estado");

            if (estado != null) {
                reserva.setEstado(estado);
                reservaService.guardarReserva(reserva);
                respuesta = ResponseEntity.ok(reserva);
            } else {
                respuesta = ResponseEntity.badRequest().body("Campo 'estado' inválido o no proporcionado.");
            }
        }
        return respuesta;
    }

    /**
     * Elimina una reserva por su ID.
     * @param id identificador de la reserva a eliminar
     */
    @DeleteMapping("/{id}")
    public void eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
    }

    /**
     * Obtiene todas las reservas asociadas a un viaje específico.
     * @param viajeId identificador del viaje
     * @return lista de reservas del viaje
     */
    @GetMapping("/viajes/{viajeId}")
    public ResponseEntity<List<Reserva>> obtenerReservasPorViajeId(@PathVariable Long viajeId) {
        List<Reserva> reservas = reservaService.obtenerReservasPorViajeId(viajeId);
        return ResponseEntity.ok(reservas);
    }

    /**
     * Obtiene todas las reservas asociadas a una nave específica.
     * @param naveId identificador de la nave
     * @return lista de reservas de la nave o 404 si no hay reservas
     */
    @GetMapping("/nave/{naveId}")
    public ResponseEntity<List<Reserva>> obtenerReservasPorNave(@PathVariable Long naveId) {
        List<Reserva> reservas = reservaService.obtenerReservasPorNave(naveId);
        if (reservas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservas);
    }

}
