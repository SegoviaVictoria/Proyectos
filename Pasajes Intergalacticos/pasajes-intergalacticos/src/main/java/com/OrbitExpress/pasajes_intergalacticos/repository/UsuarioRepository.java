package com.OrbitExpress.pasajes_intergalacticos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.OrbitExpress.pasajes_intergalacticos.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombre(String nombre);
    Optional<Usuario> findByEmail(String email);
}
