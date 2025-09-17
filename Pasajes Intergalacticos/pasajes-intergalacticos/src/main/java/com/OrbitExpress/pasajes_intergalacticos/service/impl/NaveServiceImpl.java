package com.OrbitExpress.pasajes_intergalacticos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.OrbitExpress.pasajes_intergalacticos.model.Nave;
import com.OrbitExpress.pasajes_intergalacticos.repository.NaveRepository;
import com.OrbitExpress.pasajes_intergalacticos.service.NaveService;

/**
 * Implementación del servicio de gestión de naves espaciales.
 * Proporciona operaciones CRUD y búsqueda por nombre o ID.
 */
@Service
public class NaveServiceImpl implements NaveService {

    @Autowired
    private NaveRepository naveRepository;

    /**
     * Obtiene la lista de todas las naves.
     * @return lista de objetos Nave
     */
    @Override
    public List<Nave> obtenerTodasLasNaves() {
        return naveRepository.findAll();
    }

    /**
     * Busca una nave por su ID.
     * @param id identificador de la nave
     * @return Optional con la nave encontrada o vacío si no existe
     */
    @Override
    public Optional<Nave> findById(Long id) {
        return naveRepository.findById(id);
    }

    /**
     * Crea o actualiza una nave.
     * @param nave objeto Nave a guardar
     * @return nave guardada
     */
    @Override
    public Nave crearNave(Nave nave) {
        return naveRepository.save(nave);
    }

    /**
     * Elimina una nave por su ID.
     * @param id identificador de la nave a eliminar
     */
    @Override
    public void eliminarNave(Long id) {
        naveRepository.deleteById(id);
    }

    /**
     * Busca una nave por su nombre.
     * @param nombre nombre de la nave
     * @return Optional con la nave encontrada o vacío si no existe
     */
    @Override
    public Optional<Nave> findByNombre(String nombre) {
        return naveRepository.findByNombre(nombre);
    }

}
