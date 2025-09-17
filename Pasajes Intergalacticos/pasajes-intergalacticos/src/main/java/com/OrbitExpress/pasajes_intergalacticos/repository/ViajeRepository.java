package com.OrbitExpress.pasajes_intergalacticos.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.OrbitExpress.pasajes_intergalacticos.model.Viaje;

public interface ViajeRepository extends JpaRepository<Viaje, Long>, JpaSpecificationExecutor<Viaje> {
    List<Viaje> findByDestino(String destino); 
    List<Viaje> findByFechaSalida(LocalDate fechaSalida); 
}
