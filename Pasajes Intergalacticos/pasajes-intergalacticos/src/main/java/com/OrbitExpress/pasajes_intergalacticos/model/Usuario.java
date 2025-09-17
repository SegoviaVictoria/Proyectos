package com.OrbitExpress.pasajes_intergalacticos.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidad que representa un usuario del sistema.
 * Puede ser CLIENTE, EMPRESA o ADMIN y tiene relaciones con reservas, viajes favoritos y viajes ofrecidos.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del usuario.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Email único del usuario.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Contraseña del usuario (encriptada).
     */
    @Column(nullable = false)
    private String password;

    /**
     * Rol del usuario: CLIENTE, EMPRESA o ADMIN.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    /**
     * Reservas realizadas por el usuario.
     */
    @JsonManagedReference(value = "usuario-reservas")
    @OneToMany(mappedBy = "usuario")
    private List<Reserva> reservas = new ArrayList<>();

    /**
     * Viajes favoritos del usuario.
     */
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "usuario_viaje_favorito",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "viaje_id")
    )
    private List<Viaje> viajesFavoritos = new ArrayList<>();

    /**
     * Viajes ofrecidos por la empresa (solo para usuarios con rol EMPRESA).
     */
    @OneToMany(mappedBy = "empresa")
    @JsonManagedReference
    private List<Viaje> viajes = new ArrayList<>();

    // ------------------ CONSTRUCTORES ------------------ //

    /**
     * Constructor vacío requerido por JPA.
     */
    public Usuario() {
    }

    /**
     * Constructor principal para crear un usuario.
     * @param nombre nombre del usuario
     * @param email email del usuario
     * @param password contraseña del usuario
     * @param rol rol del usuario
     */
    public Usuario(String nombre, String email, String password, Rol rol) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    /**
     * Constructor completo para crear un usuario con relaciones.
     * @param nombre nombre del usuario
     * @param email email del usuario
     * @param password contraseña del usuario
     * @param rol rol del usuario
     * @param reservas reservas realizadas
     * @param viajesFavoritos viajes favoritos
     * @param viajes viajes ofrecidos (EMPRESA)
     */
    public Usuario(String nombre, String email, String password, Rol rol, List<Reserva> reservas, List<Viaje> viajesFavoritos, List<Viaje> viajes) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.reservas = reservas;
        this.viajesFavoritos = viajesFavoritos;
        this.viajes = viajes;
    }

    // ------------------- GETTERS & SETTERS ------------------ //

    /**
     * Obtiene el ID del usuario.
     * @return id del usuario
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del usuario.
     * @param id id del usuario
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     * @param nombre nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el email del usuario.
     * @return email del usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario.
     * @param email email del usuario
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return contraseña del usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     * @param password contraseña del usuario
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el rol del usuario.
     * @return rol del usuario
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     * @param rol rol del usuario
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Obtiene la lista de reservas del usuario.
     * @return lista de reservas
     */
    public List<Reserva> getReservas() {
        return reservas;
    }

    /**
     * Establece la lista de reservas del usuario.
     * @param reservas lista de reservas
     */
    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    /**
     * Obtiene la lista de viajes favoritos del usuario.
     * @return lista de viajes favoritos
     */
    public List<Viaje> getViajesFavoritos() {
        return viajesFavoritos;
    }

    /**
     * Establece la lista de viajes favoritos del usuario.
     * @param viajesFavoritos lista de viajes favoritos
     */
    public void setViajesFavoritos(List<Viaje> viajesFavoritos) {
        this.viajesFavoritos = viajesFavoritos;
    }

    /**
     * Obtiene la lista de viajes ofrecidos por la empresa.
     * @return lista de viajes ofrecidos
     */
    public List<Viaje> getViajes() {
        return viajes;
    }

    /**
     * Establece la lista de viajes ofrecidos por la empresa.
     * @param viajes lista de viajes ofrecidos
     */
    public void setViajes(List<Viaje> viajes) {
        this.viajes = viajes;
    }

    // ---------------- METODOS ---------------- //

    /**
     * Devuelve una representación en texto del usuario.
     * @return string con los datos del usuario
     */
    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", password=" + password + ", rol="
                + rol + ", reservas=" + reservas + ", favoritos=" + viajesFavoritos + ", viajes=" + viajes + "]";
    }

}
