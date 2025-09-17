package com.OrbitExpress.pasajes_intergalacticos.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa una reserva de asiento en un viaje espacial.
 * Incluye información sobre el usuario, viaje, nave, fecha, estado y ubicación del asiento.
 */
@Entity
@Table(name = "reservas")
public class Reserva {

    /**
     * Identificador único de la reserva.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Usuario que realiza la reserva.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference(value = "usuario-reservas")
    private Usuario usuario;

    /**
     * Viaje asociado a la reserva.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "viaje_id", nullable = false)
    private Viaje viaje;

    /**
     * Nave en la que se realiza la reserva.
     */
    @ManyToOne
    @JoinColumn(name = "nave_id", nullable = false)
    private Nave nave;

    /**
     * Fecha y hora en que se realizó la reserva.
     */
    @Column(nullable = false)
    private LocalDateTime fechaReserva;

    /**
     * Estado de la reserva: "PENDIENTE", "CONFIRMADA" o "CANCELADA".
     */
    @Column(nullable = false)
    private String estado = "PENDIENTE";

    /**
     * Fila del asiento reservado.
     */
    @Column(nullable = false)
    private Integer fila;

    /**
     * Columna del asiento reservado.
     */
    @Column(nullable = false)
    private Integer columna;

    // ------------------ CONSTRUCTORES ------------------ //

    /**
     * Constructor vacío requerido por JPA.
     */
    public Reserva() {
    }

    /**
     * Constructor para crear una reserva básica.
     * @param usuario usuario que reserva
     * @param viaje viaje asociado
     * @param nave nave asociada
     * @param fechaReserva fecha y hora de la reserva
     * @param fila fila del asiento
     * @param columna columna del asiento
     */
    public Reserva(Usuario usuario, Viaje viaje, Nave nave, LocalDateTime fechaReserva, Integer fila, Integer columna) {
        this.usuario = usuario;
        this.viaje = viaje;
        this.nave = nave;
        this.fechaReserva = fechaReserva;
        this.fila = fila;
        this.columna = columna;
    }

    /**
     * Constructor para crear una reserva con estado.
     * @param usuario usuario que reserva
     * @param viaje viaje asociado
     * @param nave nave asociada
     * @param fechaReserva fecha y hora de la reserva
     * @param estado estado de la reserva
     * @param fila fila del asiento
     * @param columna columna del asiento
     */
    public Reserva(Usuario usuario, Viaje viaje, Nave nave, LocalDateTime fechaReserva, String estado, Integer fila, Integer columna) {
        this.usuario = usuario;
        this.viaje = viaje;
        this.nave = nave;
        this.fechaReserva = fechaReserva;
        this.estado = estado;
        this.fila = fila;
        this.columna = columna;
    }

    // ------------------ GETTERS & SETTERS ------------------ //

    /**
     * Obtiene el ID de la reserva.
     * @return id de la reserva
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID de la reserva.
     * @param id id de la reserva
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el usuario que realizó la reserva.
     * @return usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario que realizó la reserva.
     * @param usuario usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el viaje asociado a la reserva.
     * @return viaje
     */
    public Viaje getViaje() {
        return viaje;
    }

    /**
     * Establece el viaje asociado a la reserva.
     * @param viaje viaje
     */
    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }

    /**
     * Obtiene la fecha y hora de la reserva.
     * @return fecha de la reserva
     */
    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    /**
     * Establece la fecha y hora de la reserva.
     * @param fechaReserva fecha de la reserva
     */
    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    /**
     * Obtiene el estado de la reserva.
     * @return estado de la reserva
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la reserva.
     * @param estado estado de la reserva
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la fila del asiento reservado.
     * @return fila del asiento
     */
    public Integer getFila() {
        return fila;
    }

    /**
     * Establece la fila del asiento reservado.
     * @param fila fila del asiento
     */
    public void setFila(Integer fila) {
        this.fila = fila;
    }

    /**
     * Obtiene la columna del asiento reservado.
     * @return columna del asiento
     */
    public Integer getColumna() {
        return columna;
    }

    /**
     * Establece la columna del asiento reservado.
     * @param columna columna del asiento
     */
    public void setColumna(Integer columna) {
        this.columna = columna;
    }

    /**
     * Obtiene la nave asociada a la reserva.
     * @return nave
     */
    public Nave getNave() {
        return nave;
    }

    /**
     * Establece la nave asociada a la reserva.
     * @param nave nave
     */
    public void setNave(Nave nave) {
        this.nave = nave;
    }

    // ------------------ MÉTODOS ------------------ //

    /**
     * Devuelve una representación en texto de la reserva.
     * @return string con los datos de la reserva
     */
    @Override
    public String toString() {
        return "Reserva{"
                + "id=" + id
                + ", fila=" + fila
                + ", columna=" + columna
                + ", fechaReserva=" + fechaReserva
                + ", estado='" + estado + '\''
                + '}';
    }

}
