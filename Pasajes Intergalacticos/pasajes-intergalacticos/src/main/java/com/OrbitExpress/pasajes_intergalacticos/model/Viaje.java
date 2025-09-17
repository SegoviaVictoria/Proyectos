package com.OrbitExpress.pasajes_intergalacticos.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa un viaje espacial.
 * Incluye información sobre origen, destino, fecha, duración, precio, empresa, nave y usuarios que lo han marcado como favorito.
 */
@Entity
@Table(name = "viajes")
public class Viaje {

    /**
     * Identificador único del viaje.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Origen del viaje.
     */
    @Column(nullable = false)
    private String origen;

    /**
     * Destino del viaje.
     */
    @Column(nullable = false)
    private String destino;

    /**
     * Fecha y hora de salida del viaje.
     */
    @Column(nullable = false)
    private LocalDateTime fechaSalida;

    /**
     * Duración del viaje en minutos.
     */
    @Column(nullable = false)
    private Integer duracionMinutos;

    /**
     * Precio del viaje.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    /**
     * Empresa (usuario con rol EMPRESA) que ofrece el viaje.
     */
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    @JsonBackReference
    private Usuario empresa;

    /**
     * Nave asignada al viaje.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nave_id", nullable = false)
    private Nave nave;

    /**
     * Usuarios que han marcado este viaje como favorito.
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "viajesFavoritos")
    private List<Usuario> usuariosQueLoLikearon = new ArrayList<>();

    // ------------------ CONSTRUCTORES ------------------ //

    /**
     * Constructor vacío requerido por JPA.
     */
    public Viaje() {
    }

    /**
     * Constructor para crear un viaje con los datos principales.
     * @param empresa empresa que ofrece el viaje
     * @param origen origen del viaje
     * @param destino destino del viaje
     * @param fechaSalida fecha y hora de salida
     * @param duracionMinutos duración en minutos
     * @param precio precio del viaje
     * @param nave nave asignada
     */
    public Viaje(Usuario empresa, String origen, String destino, LocalDateTime fechaSalida, Integer duracionMinutos,
            BigDecimal precio, Nave nave) {
        this.empresa = empresa;
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.duracionMinutos = duracionMinutos;
        this.precio = precio;
        this.nave = nave;
    }

    /**
     * Constructor completo para crear un viaje con todos los datos y usuarios que lo han marcado como favorito.
     * @param origen origen del viaje
     * @param destino destino del viaje
     * @param fechaSalida fecha y hora de salida
     * @param duracionMinutos duración en minutos
     * @param precio precio del viaje
     * @param empresa empresa que ofrece el viaje
     * @param nave nave asignada
     * @param usuariosQueLoLikearon lista de usuarios que lo han marcado como favorito
     */
    public Viaje(String origen, String destino, LocalDateTime fechaSalida, Integer duracionMinutos,
            BigDecimal precio, Usuario empresa, Nave nave, List<Usuario> usuariosQueLoLikearon) {
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.duracionMinutos = duracionMinutos;
        this.precio = precio;
        this.empresa = empresa;
        this.nave = nave;
        this.usuariosQueLoLikearon = usuariosQueLoLikearon;
    }

    // ---------------- Getters y setters ---------------- //

    /**
     * Obtiene el ID del viaje.
     * @return id del viaje
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del viaje.
     * @param id id del viaje
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el origen del viaje.
     * @return origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * Establece el origen del viaje.
     * @param origen origen
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * Obtiene el destino del viaje.
     * @return destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Establece el destino del viaje.
     * @param destino destino
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * Obtiene la fecha y hora de salida del viaje.
     * @return fecha de salida
     */
    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    /**
     * Establece la fecha y hora de salida del viaje.
     * @param fechaSalida fecha de salida
     */
    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    /**
     * Obtiene la duración del viaje en minutos.
     * @return duración en minutos
     */
    public Integer getDuracionMinutos() {
        return duracionMinutos;
    }

    /**
     * Establece la duración del viaje en minutos.
     * @param duracionMinutos duración en minutos
     */
    public void setDuracionMinutos(Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    /**
     * Obtiene el precio del viaje.
     * @return precio
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del viaje.
     * @param precio precio
     */
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la empresa que ofrece el viaje.
     * @return empresa
     */
    public Usuario getEmpresa() {
        return empresa;
    }

    /**
     * Establece la empresa que ofrece el viaje.
     * @param empresa empresa
     */
    public void setEmpresa(Usuario empresa) {
        this.empresa = empresa;
    }

    /**
     * Obtiene la nave asignada al viaje.
     * @return nave
     */
    public Nave getNave() {
        return nave;
    }

    /**
     * Establece la nave asignada al viaje.
     * @param nave nave
     */
    public void setNave(Nave nave) {
        this.nave = nave;
    }

    /**
     * Obtiene la lista de usuarios que han marcado este viaje como favorito.
     * @return lista de usuarios
     */
    public List<Usuario> getUsuariosQueLoLikearon() {
        return usuariosQueLoLikearon;
    }

    /**
     * Establece la lista de usuarios que han marcado este viaje como favorito.
     * @param usuariosQueLoLikearon lista de usuarios
     */
    public void setUsuariosQueLoLikearon(List<Usuario> usuariosQueLoLikearon) {
        this.usuariosQueLoLikearon = usuariosQueLoLikearon;
    }

    // ---------------- METODOS ---------------- //

    /**
     * Devuelve una representación en texto del viaje.
     * @return string con los datos del viaje
     */
    @Override
    public String toString() {
        return "Viaje [id=" + id + ", origen=" + origen + ", destino=" + destino + ", fechaSalida=" + fechaSalida
                + ", duracionMinutos=" + duracionMinutos + ", precio=" + precio + ", empresa=" + empresa + ", nave="
                + nave + ", usuariosQueLoLikearon=" + usuariosQueLoLikearon + "]";
    }

}
