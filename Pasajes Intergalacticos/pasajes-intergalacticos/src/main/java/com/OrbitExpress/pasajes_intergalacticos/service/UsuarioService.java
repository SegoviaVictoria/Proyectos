package com.OrbitExpress.pasajes_intergalacticos.service;

import java.util.List;
import java.util.Optional;

import com.OrbitExpress.pasajes_intergalacticos.model.Reserva;
import com.OrbitExpress.pasajes_intergalacticos.model.Usuario;
import com.OrbitExpress.pasajes_intergalacticos.model.Viaje;

public interface UsuarioService {

    List<Usuario> obtenerTodasLosUsuarios();
    Optional<Usuario> obtenerUsuariosPorId(Long id);
    Optional<Usuario> findByNombre(String nombre);
    Usuario crearUsuario(Usuario usuario);
    void eliminarUsuario(Long id);
    Usuario login(Usuario loginUsuario);

    void agregarViajeAFavoritos(Long usuarioId, Long viajeId);
    void eliminarViajeDeFavoritos(Long usuarioId, Long viajeId);
    List<Viaje> obtenerFavoritos(Long usuarioId);
    Optional<Usuario> findByEmail(String email);
    List<Reserva> obtenerReservasPorUsuarioId(Long usuarioId);
    
}
