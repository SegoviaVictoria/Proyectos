package com.OrbitExpress.pasajes_intergalacticos.service;

import java.util.List;
import java.util.Optional;

import com.OrbitExpress.pasajes_intergalacticos.model.Nave;

public interface NaveService {

    List<Nave> obtenerTodasLasNaves();
    Optional<Nave> findById(Long id);
    Optional<Nave> findByNombre(String nombre);
    Nave crearNave(Nave nave);
    void eliminarNave(Long id);

}
