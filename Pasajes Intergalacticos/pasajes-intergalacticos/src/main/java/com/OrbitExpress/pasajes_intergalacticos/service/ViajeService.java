package com.OrbitExpress.pasajes_intergalacticos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.OrbitExpress.pasajes_intergalacticos.model.Viaje;

public interface ViajeService {

    List<Viaje> obtenerTodosLosViajes();
    Optional<Viaje> obtenerViajePorId(Long id);
    Viaje crearViaje(Viaje viaje);
    void eliminarViaje(Long id);
    List<Viaje> obtenerViajesPorDestino(String destino);
    List<Viaje> obtenerViajesPorFechaSalida(LocalDate fechaSalida);
    List<Viaje> buscarViajes(String origen, String destino, LocalDate fechaSalida, String ordenar);
}
