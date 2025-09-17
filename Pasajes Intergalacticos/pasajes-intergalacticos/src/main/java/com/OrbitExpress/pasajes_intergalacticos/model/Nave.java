package com.OrbitExpress.pasajes_intergalacticos.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidad que representa una nave espacial en el sistema.
 * Incluye información sobre nombre, modelo, capacidad, disposición de asientos y dimensiones.
 */
@Entity
@Table(name = "naves")
public class Nave {

    /**
     * Identificador único de la nave.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de la nave.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Modelo de la nave.
     */
    @Column(nullable = false)
    private String modelo;

    /**
     * Capacidad de la nave (número de pasajeros).
     */
    @Column(nullable = false)
    private int capacidad; // Capacidad de la nave (número de pasajeros)

    /**
     * Número de filas de asientos en la nave.
     */
    @Column(nullable = false)
    private Integer filas;

    /**
     * Número de columnas de asientos en la nave.
     */
    @Column(nullable = false)
    private Integer columnas;

    /**
     * Lista de reservas asociadas a la nave (disposición de asientos).
     * Se utiliza para gestionar la ocupación de los asientos.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "nave", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> disposicionAsientos = new ArrayList<>();

    // ------------------ CONSTRUCTORES ------------------ //

    /**
     * Constructor vacío requerido por JPA.
     */
    public Nave() {
    }

    /**
     * Constructor para crear una nave con los datos principales.
     * @param nombre nombre de la nave
     * @param modelo modelo de la nave
     * @param capacidad capacidad de la nave
     * @param filas número de filas de asientos
     * @param columnas número de columnas de asientos
     */
    public Nave(String nombre, String modelo, int capacidad, Integer filas, Integer columnas) {
        this.nombre = nombre;
        this.modelo = modelo;
        this.capacidad = capacidad;
        this.filas = filas;
        this.columnas = columnas;
    }

    /**
     * Constructor para crear una nave con disposición de asientos.
     * @param nombre nombre de la nave
     * @param modelo modelo de la nave
     * @param capacidad capacidad de la nave
     * @param filas número de filas de asientos
     * @param columnas número de columnas de asientos
     * @param disposicionAsientos lista de reservas/asientos
     */
    public Nave(String nombre, String modelo, int capacidad, Integer filas, Integer columnas,
            List<Reserva> disposicionAsientos) {
        this.nombre = nombre;
        this.modelo = modelo;
        this.capacidad = capacidad;
        this.filas = filas;
        this.columnas = columnas;
        this.disposicionAsientos = new ArrayList<>();
    }

    // --------------------- GETTERS & SETTERS --------------------- //

    /**
     * Obtiene el ID de la nave.
     * @return id de la nave
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID de la nave.
     * @param id id de la nave
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la nave.
     * @return nombre de la nave
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la nave.
     * @param nombre nombre de la nave
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el modelo de la nave.
     * @return modelo de la nave
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Establece el modelo de la nave.
     * @param modelo modelo de la nave
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtiene la capacidad de la nave.
     * @return capacidad de la nave
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Establece la capacidad de la nave.
     * @param capacidad capacidad de la nave
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * Obtiene el número de filas de asientos.
     * @return número de filas
     */
    public Integer getFilas() {
        return filas;
    }

    /**
     * Establece el número de filas de asientos.
     * @param filas número de filas
     */
    public void setFilas(Integer filas) {
        this.filas = filas;
    }

    /**
     * Obtiene el número de columnas de asientos.
     * @return número de columnas
     */
    public Integer getColumnas() {
        return columnas;
    }

    /**
     * Establece el número de columnas de asientos.
     * @param columnas número de columnas
     */
    public void setColumnas(Integer columnas) {
        this.columnas = columnas;
    }

    /**
     * Obtiene la disposición de asientos (reservas) de la nave.
     * @return lista de reservas/asientos
     */
    public List<Reserva> getDisposicionAsientos() {
        return disposicionAsientos;
    }

    /**
     * Establece la disposición de asientos (reservas) de la nave.
     * @param disposicionAsientos lista de reservas/asientos
     */
    public void setDisposicionAsientos(List<Reserva> disposicionAsientos) {
        this.disposicionAsientos = disposicionAsientos;
    }

    // --------------------- METODOS --------------------- //

    /**
     * Devuelve una representación en texto de la nave.
     * @return string con los datos de la nave
     */
    @Override
    public String toString() {
        return "Nave [id=" + id + ", nombre=" + nombre + ", modelo=" + modelo + ", capacidad=" + capacidad + ", filas="
                + filas + ", columnas=" + columnas + ", disposicionAsientos="
                + disposicionAsientos + "]";
    }
    
}
