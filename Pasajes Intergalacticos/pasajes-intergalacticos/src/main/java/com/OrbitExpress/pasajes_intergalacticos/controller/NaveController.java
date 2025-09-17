package com.OrbitExpress.pasajes_intergalacticos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OrbitExpress.pasajes_intergalacticos.model.Nave;
import com.OrbitExpress.pasajes_intergalacticos.model.Reserva;
import com.OrbitExpress.pasajes_intergalacticos.service.NaveService;

/**
 * Controlador REST para la gestión de naves espaciales.
 * Proporciona endpoints para CRUD y consulta de asientos.
 */
@RestController
@RequestMapping("/api/naves")
public class NaveController {

    @Autowired
    private NaveService naveService;

    /**
     * Obtiene la lista de todas las naves.
     * @return lista de objetos Nave
     */
    @GetMapping
    public List<Nave> obtenerTodasLasNaves() {
        return naveService.obtenerTodasLasNaves();
    }

    /**
     * Obtiene una nave por su ID.
     * @param id identificador de la nave
     * @return objeto Nave o null si no existe
     */
    @GetMapping("/{id}")
    public Nave obtenerNavePorId(@PathVariable Long id) {
        return naveService.findById(id).orElse(null);
    }

    /**
     * Busca una nave por su nombre.
     * @param nombre nombre de la nave
     * @return objeto Nave o null si no existe
     */
    @GetMapping("/nombre/{nombre}")
    public Nave findByNombre(@PathVariable String nombre) {
        return naveService.findByNombre(nombre).orElse(null);
    }

    /**
     * Crea una nueva nave.
     * @param nave objeto Nave a crear
     * @return nave creada
     */
    @PostMapping
    public Nave crearNave(@RequestBody Nave nave) {
        return naveService.crearNave(nave);
    }

    /**
     * Actualiza una nave existente.
     * @param id identificador de la nave a actualizar
     * @param nave datos actualizados de la nave
     * @return nave actualizada
     */
    @PutMapping("/{id}")
    public Nave actualizarNave(@PathVariable Long id, @RequestBody Nave nave) {
        nave.setId(id);
        return naveService.crearNave(nave);
    }

    /**
     * Elimina una nave por su ID.
     * @param id identificador de la nave a eliminar
     */
    @DeleteMapping("/{id}")
    public void eliminarNave(@PathVariable Long id) {
        naveService.eliminarNave(id);
    }

    /**
     * Obtiene la disposición de asientos (reservas) de una nave por su ID.
     * @param id identificador de la nave
     * @return lista de reservas (asientos) de la nave
     */
    @GetMapping("/{id}/asientos")
    public List<Reserva> obtenerAsientosPorNave(@PathVariable Long id) {
        return naveService.findById(id).get().getDisposicionAsientos();
    }
    
}
