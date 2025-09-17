package com.OrbitExpress.pasajes_intergalacticos.model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class NaveTest {

    @Test
    public void testConstructorYToString() {

        // Datos de prueba
        Long id = 1L;
        String nombre = "GalaxiaX";
        String modelo = "GX-9000";
        int capacidad = 6;
        Integer filas = 2;
        Integer columnas = 3;

        // Crear reservas (simulando asientos ocupados por reservas)
        List<Reserva> disposicionAsientos = new ArrayList<>();

        disposicionAsientos.add(new Reserva());  // Asiento 1
        disposicionAsientos.add(new Reserva());  // Asiento 2
        disposicionAsientos.add(new Reserva());  // Asiento 3
        disposicionAsientos.add(new Reserva());  // Asiento 4
        disposicionAsientos.add(new Reserva());  // Asiento 5
        disposicionAsientos.add(new Reserva());  // Asiento 6

        // Crear nave con el constructor
        Nave nave = new Nave(nombre, modelo, capacidad, filas, columnas, disposicionAsientos);

        // Verificaciones
        assertEquals(id, nave.getId());
        assertEquals(nombre, nave.getNombre());
        assertEquals(modelo, nave.getModelo());
        assertEquals(capacidad, nave.getCapacidad());
        assertEquals(filas, nave.getFilas());
        assertEquals(columnas, nave.getColumnas());

        // Probar toString
        String result = nave.toString();
        assertTrue(result.contains("nombre=GalaxiaX"));
        assertTrue(result.contains("modelo=GX-9000"));
        
    }
    
}
