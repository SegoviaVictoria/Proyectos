package com.OrbitExpress.pasajes_intergalacticos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.OrbitExpress.pasajes_intergalacticos.model.LoginRequest;
import com.OrbitExpress.pasajes_intergalacticos.model.Reserva;
import com.OrbitExpress.pasajes_intergalacticos.model.Usuario;
import com.OrbitExpress.pasajes_intergalacticos.model.Viaje;
import com.OrbitExpress.pasajes_intergalacticos.service.UsuarioService;

/**
 * Controlador REST para la gestión de usuarios.
 * Proporciona endpoints para CRUD, gestión de reservas, favoritos y autenticación.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private final UsuarioService usuarioService;

    /**
     * Obtiene la lista de todos los usuarios.
     * @return lista de objetos Usuario
     */
    @GetMapping
    public List<Usuario> obtenerTodasLosUsuarios() {
        return usuarioService.obtenerTodasLosUsuarios();
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id identificador del usuario
     * @return objeto Usuario o null si no existe
     */
    @GetMapping("/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuariosPorId(id).orElse(null);
    }

    /**
     * Busca un usuario por su nombre.
     * @param nombre nombre del usuario
     * @return objeto Usuario o null si no existe
     */
    @GetMapping("/nombre/{nombre}")
    public Usuario obtenerUsuarioPorNombre(@PathVariable String nombre) {
        return usuarioService.findByNombre(nombre).orElse(null);
    }

    /**
     * Obtiene todas las reservas asociadas a un usuario.
     * @param usuarioId identificador del usuario
     * @return lista de reservas del usuario
     */
    @GetMapping("/{usuarioId}/reservas")
    public ResponseEntity<List<Reserva>> obtenerReservasPorUsuarioId(@PathVariable Long usuarioId) {
        List<Reserva> reservas = usuarioService.obtenerReservasPorUsuarioId(usuarioId);
        return ResponseEntity.ok(reservas);
    }

    /**
     * Crea un nuevo usuario.
     * @param usuario objeto Usuario a crear
     * @return usuario creado
     */
    @PostMapping(consumes = "application/json;charset=UTF-8")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    /**
     * Actualiza un usuario existente.
     * @param id identificador del usuario a actualizar
     * @param usuario datos actualizados del usuario
     * @return usuario actualizado
     */
    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        return usuarioService.crearUsuario(usuario);
    }

    /**
     * Elimina un usuario por su ID.
     * @param id identificador del usuario a eliminar
     */
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }

    // -------------------- FAVORITOS -------------------- //

    /**
     * Obtiene la lista de viajes favoritos de un usuario.
     * @param usuarioId identificador del usuario
     * @return lista de viajes favoritos
     */
    @GetMapping("/{usuarioId}/favoritos")
    public ResponseEntity<List<Viaje>> obtenerFavoritos(@PathVariable Long usuarioId) {
        List<Viaje> favoritos = usuarioService.obtenerFavoritos(usuarioId);
        return ResponseEntity.ok(favoritos);
    }

    /**
     * Agrega un viaje a la lista de favoritos de un usuario.
     * @param usuarioId identificador del usuario
     * @param viajeId identificador del viaje a agregar
     * @return respuesta vacía con estado 200 OK
     */
    @PostMapping("/{usuarioId}/favoritos/{viajeId}")
    public ResponseEntity<Void> agregarViajeAFavoritos(@PathVariable Long usuarioId, @PathVariable Long viajeId) {
        usuarioService.agregarViajeAFavoritos(usuarioId, viajeId);
        return ResponseEntity.ok().build();
    }

    /**
     * Elimina un viaje de la lista de favoritos de un usuario.
     * @param usuarioId identificador del usuario
     * @param viajeId identificador del viaje a eliminar
     * @return respuesta vacía con estado 200 OK
     */
    @DeleteMapping("/{usuarioId}/favoritos/{viajeId}")
    public ResponseEntity<Void> eliminarViajeDeFavoritos(@PathVariable Long usuarioId, @PathVariable Long viajeId) {
        usuarioService.eliminarViajeDeFavoritos(usuarioId, viajeId);
        return ResponseEntity.ok().build();
    }

    // -------------------- LOGIN -------------------- //

    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor para inyección de dependencias.
     * @param usuarioService servicio de usuario
     * @param passwordEncoder codificador de contraseñas
     */
    public UsuarioController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Autentica a un usuario con email y contraseña.
     * @param loginRequest objeto con email y contraseña
     * @return usuario autenticado o error si las credenciales no son válidas
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Usuario usuario = usuarioService.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        return ResponseEntity.ok(usuario);
    }

}
