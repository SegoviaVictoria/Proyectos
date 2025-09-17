package com.OrbitExpress.pasajes_intergalacticos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OrbitExpress.pasajes_intergalacticos.model.Nave;

public interface NaveRepository extends JpaRepository<Nave, Long> {
    Optional<Nave> findByNombre(String nombre);
}
