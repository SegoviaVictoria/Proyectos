package com.OrbitExpress.pasajes_intergalacticos.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.OrbitExpress.pasajes_intergalacticos.model.Viaje;
import com.OrbitExpress.pasajes_intergalacticos.repository.ReservaRepository;
import com.OrbitExpress.pasajes_intergalacticos.service.ViajeService;

/**
 * Controlador REST para la gestión de viajes.
 * Proporciona endpoints para CRUD, búsqueda avanzada y consulta de reservas asociadas.
 */
@RestController
@RequestMapping("/api/viajes")
public class ViajeController {

    @Autowired
    private ViajeService viajeService;

    /**
     * Obtiene la lista de todos los viajes.
     * @return lista de objetos Viaje
     */
    @GetMapping
    public List<Viaje> obtenerTodosLosViajes() {
        return viajeService.obtenerTodosLosViajes();
    }

    /**
     * Obtiene un viaje por su ID.
     * @param id identificador del viaje
     * @return objeto Viaje o null si no existe
     */
    @GetMapping("/{id}")
    public Viaje obtenerViajePorId(@PathVariable Long id) {
        return viajeService.obtenerViajePorId(id).orElse(null);
    }

    /**
     * Crea un nuevo viaje.
     * @param viaje objeto Viaje a crear
     * @return viaje creado
     */
    @PostMapping
    public Viaje crearViaje(@RequestBody Viaje viaje) {
        return viajeService.crearViaje(viaje);
    }

    /**
     * Actualiza un viaje existente.
     * @param id identificador del viaje a actualizar
     * @param viaje datos actualizados del viaje
     * @return viaje actualizado
     */
    @PutMapping("/{id}")
    public Viaje actualizarViaje(@PathVariable Long id, @RequestBody Viaje viaje) {
        viaje.setId(id);
        return viajeService.crearViaje(viaje);
    }

    /**
     * Busca viajes por origen, destino y fecha de salida.
     * @param origen origen del viaje (opcional)
     * @param destino destino del viaje (opcional)
     * @param fechaSalida fecha de salida (opcional)
     * @param ordenar criterio de ordenación (opcional)
     * @return lista de viajes que cumplen los criterios de búsqueda
     */
    @GetMapping("/buscar")
    public List<Viaje> buscarViajes(
            @RequestParam(required = false) String origen,
            @RequestParam(required = false) String destino,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaSalida,
            @RequestParam(required = false) String ordenar
    ) {
        return viajeService.buscarViajes(origen, destino, fechaSalida, ordenar);
    }

    /**
     * Elimina un viaje por su ID.
     * @param id identificador del viaje a eliminar
     */
    @DeleteMapping("/{id}")
    public void eliminarViaje(@PathVariable Long id) {
        viajeService.eliminarViaje(id);
    }

    // Contar número de reservas asociadas a un viaje
    private final ReservaRepository reservaRepository;

    /**
     * Constructor para inyección de dependencias.
     * @param reservaRepository repositorio de reservas
     */
    public ViajeController(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    /**
     * Obtiene el número de reservas asociadas a un viaje.
     * @param viajeId identificador del viaje
     * @return número de reservas para el viaje dado
     */
    @GetMapping("/{viajeId}/reservas/count")
    public Long getNumeroDeReservas(@PathVariable Long viajeId) {
        return reservaRepository.countReservasByViajeId(viajeId);
    }

}
