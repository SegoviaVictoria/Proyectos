package com.OrbitExpress.pasajes_intergalacticos.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.OrbitExpress.pasajes_intergalacticos.model.Reserva;
import com.OrbitExpress.pasajes_intergalacticos.model.Usuario;
import com.OrbitExpress.pasajes_intergalacticos.model.Viaje;
import com.OrbitExpress.pasajes_intergalacticos.repository.ReservaRepository;
import com.OrbitExpress.pasajes_intergalacticos.repository.UsuarioRepository;
import com.OrbitExpress.pasajes_intergalacticos.repository.ViajeRepository;
import com.OrbitExpress.pasajes_intergalacticos.service.UsuarioService;

/**
 * Implementación del servicio de gestión de usuarios. Proporciona operaciones
 * CRUD, gestión de reservas, favoritos y autenticación.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    /**
     * Obtiene la lista de todos los usuarios.
     *
     * @return lista de objetos Usuario
     */
    @Override
    public List<Usuario> obtenerTodasLosUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id identificador del usuario
     * @return Optional con el usuario encontrado o vacío si no existe
     */
    @Override
    public Optional<Usuario> obtenerUsuariosPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Busca un usuario por su email.
     *
     * @param email email del usuario
     * @return Optional con el usuario encontrado o vacío si no existe
     */
    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    /**
     * Obtiene todas las reservas asociadas a un usuario.
     *
     * @param usuarioId identificador del usuario
     * @return lista de reservas del usuario
     */
    @Override
    public List<Reserva> obtenerReservasPorUsuarioId(Long usuarioId) {
        return Optional.ofNullable(reservaRepository.findByUsuarioId(usuarioId))
                .orElse(Collections.emptyList());
    }

    /**
     * Crea un nuevo usuario, encriptando la contraseña antes de guardar.
     *
     * @param usuario objeto Usuario a crear
     * @return usuario creado
     */
    @Override
    public Usuario crearUsuario(Usuario usuario) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id identificador del usuario a eliminar
     */
    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    /**
     * Busca un usuario por su nombre.
     *
     * @param nombre nombre del usuario
     * @return Optional con el usuario encontrado o vacío si no existe
     */
    @Override
    public Optional<Usuario> findByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    /**
     * Obtiene la lista de viajes favoritos de un usuario.
     *
     * @param usuarioId identificador del usuario
     * @return lista de viajes favoritos
     */
    @Override
    public List<Viaje> obtenerFavoritos(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return usuario.getViajesFavoritos();
    }

    /**
     * Agrega un viaje a la lista de favoritos de un usuario.
     *
     * @param usuarioId identificador del usuario
     * @param viajeId identificador del viaje a agregar
     */
    @Override
    public void agregarViajeAFavoritos(Long usuarioId, Long viajeId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));

        if (!usuario.getViajesFavoritos().contains(viaje)) {
            usuario.getViajesFavoritos().add(viaje);
            viaje.getUsuariosQueLoLikearon().add(usuario); // Sincroniza la relación inversa
            usuarioRepository.save(usuario);
        }
    }

    /**
     * Elimina un viaje de la lista de favoritos de un usuario.
     *
     * @param usuarioId identificador del usuario
     * @param viajeId identificador del viaje a eliminar
     */
    @Override
    public void eliminarViajeDeFavoritos(Long usuarioId, Long viajeId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Viaje viaje = viajeRepository.findById(viajeId)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado"));

        if (usuario.getViajesFavoritos().contains(viaje)) {
            usuario.getViajesFavoritos().remove(viaje);
            viaje.getUsuariosQueLoLikearon().remove(usuario); // Sincroniza la relación inversa
            usuarioRepository.save(usuario);
        }
    }

    /**
     * Autentica a un usuario por email y contraseña.
     *
     * @param loginUsuario usuario con email y contraseña a validar
     * @return usuario autenticado si las credenciales son correctas
     * @throws IllegalArgumentException si el usuario no existe o la contraseña
     * es incorrecta
     */
    @Override
    public Usuario login(Usuario loginUsuario) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return usuarioRepository.findByEmail(loginUsuario.getEmail())
                .filter(usuario -> passwordEncoder.matches(loginUsuario.getPassword(), usuario.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado o contraseña incorrecta"));
    }

}
