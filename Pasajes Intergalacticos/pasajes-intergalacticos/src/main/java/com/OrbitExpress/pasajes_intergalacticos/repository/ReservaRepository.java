package com.OrbitExpress.pasajes_intergalacticos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.OrbitExpress.pasajes_intergalacticos.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuarioId(Long usuarioId);
    List<Reserva> findByViajeId(Long viajeId);
    boolean existsByViajeIdAndFilaAndColumna(Long viajeId, int fila, int columna);

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.viaje.id = :viajeId")
    Long countReservasByViajeId(@Param("viajeId") Long viajeId);

    List<Reserva> findByNaveId(Long naveId);
    List<Reserva>findReservasByNaveId(Long naveId);

}
