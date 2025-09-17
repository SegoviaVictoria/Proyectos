package com.OrbitExpress.pasajes_intergalacticos.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.OrbitExpress.pasajes_intergalacticos.model.Viaje;
import com.OrbitExpress.pasajes_intergalacticos.repository.ViajeRepository;
import com.OrbitExpress.pasajes_intergalacticos.service.ViajeService;

/**
 * Implementación del servicio de gestión de viajes espaciales.
 * Proporciona operaciones CRUD, búsqueda avanzada y filtrado por destino o fecha.
 */
@Service
public class ViajeServiceImpl implements ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    /**
     * Obtiene la lista de todos los viajes.
     * @return lista de objetos Viaje
     */
    @Override
    public List<Viaje> obtenerTodosLosViajes() {
        List<Viaje> viajes = viajeRepository.findAll();
        return viajes;
    }

    /**
     * Busca un viaje por su ID.
     * @param id identificador del viaje
     * @return Optional con el viaje encontrado o vacío si no existe
     */
    @Override
    public Optional<Viaje> obtenerViajePorId(Long id) {
        return viajeRepository.findById(id);
    }

    /**
     * Crea un nuevo viaje.
     * @param viaje objeto Viaje a crear
     * @return viaje creado
     */
    @Override
    public Viaje crearViaje(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    /**
     * Elimina un viaje por su ID.
     * @param id identificador del viaje a eliminar
     */
    @Override
    public void eliminarViaje(Long id) {
        viajeRepository.deleteById(id);
    }

    /**
     * Obtiene la lista de viajes por destino.
     * @param destino destino del viaje
     * @return lista de viajes con ese destino
     */
    @Override
    public List<Viaje> obtenerViajesPorDestino(String destino) {
        return viajeRepository.findByDestino(destino);
    }

    /**
     * Obtiene la lista de viajes por fecha de salida.
     * @param fechaSalida fecha de salida
     * @return lista de viajes en esa fecha
     */
    @Override
    public List<Viaje> obtenerViajesPorFechaSalida(LocalDate fechaSalida) {
        return viajeRepository.findByFechaSalida(fechaSalida);
    }

    /**
     * Busca viajes según origen, destino, fecha de salida y criterio de ordenación.
     * @param origen origen del viaje (opcional)
     * @param destino destino del viaje (opcional)
     * @param fechaSalida fecha de salida (opcional)
     * @param ordenar criterio de ordenación ("precio", "duracion", etc.)
     * @return lista de viajes que cumplen los criterios de búsqueda
     */
    @Override
    public List<Viaje> buscarViajes(String origen, String destino, LocalDate fechaSalida, String ordenar) {
        Specification<Viaje> spec = Specification.where(null);

        if (origen != null && !origen.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("origen")), origen.toLowerCase()));
        }

        if (destino != null && !destino.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("destino")), destino.toLowerCase()));
        }

        if (fechaSalida != null) {
            spec = spec.and((root, query, cb) -> cb.equal(cb.function("DATE", LocalDate.class, root.get("fechaSalida")), fechaSalida));
        }

        Sort sort = Sort.unsorted();
        if ("precio".equalsIgnoreCase(ordenar)) {
            sort = Sort.by("precio").ascending();
        } else if ("duracion".equalsIgnoreCase(ordenar)) {
            sort = Sort.by("duracionMinutos").ascending();
        }

        return viajeRepository.findAll(spec, sort);
    }

}
