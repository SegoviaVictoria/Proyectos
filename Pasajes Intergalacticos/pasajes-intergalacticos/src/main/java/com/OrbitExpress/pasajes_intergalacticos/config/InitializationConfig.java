package com.OrbitExpress.pasajes_intergalacticos.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.OrbitExpress.pasajes_intergalacticos.model.Nave;
import com.OrbitExpress.pasajes_intergalacticos.model.Reserva;
import com.OrbitExpress.pasajes_intergalacticos.model.Rol;
import com.OrbitExpress.pasajes_intergalacticos.model.Usuario;
import com.OrbitExpress.pasajes_intergalacticos.model.Viaje;
import com.OrbitExpress.pasajes_intergalacticos.repository.NaveRepository;
import com.OrbitExpress.pasajes_intergalacticos.repository.ReservaRepository;
import com.OrbitExpress.pasajes_intergalacticos.repository.UsuarioRepository;
import com.OrbitExpress.pasajes_intergalacticos.repository.ViajeRepository;
import com.github.javafaker.Faker;

// Esta clase se utiliza para inicializar la aplicación con valores predeterminados
// y configuraciones. Puede usarse para configurar cualquier dato o
// configuración necesaria para que la aplicación funcione correctamente.
@Configuration
public class InitializationConfig {

    @Bean
    public CommandLineRunner initData(UsuarioRepository usuarioRepository,
            ViajeRepository viajeRepository,
            ReservaRepository reservaRepository,
            NaveRepository naveRepository) {
        return args -> {
            try {
                Faker faker = new Faker();

                // Configuración de cantidades
                int cantidadUsuarios = 5;
                int cantidadNaves = 3;
                int cantidadViajes = 5;
                int cantidadReservas = 5;

                // Crear usuarios
                List<Usuario> usuarios = new ArrayList<>();
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String contrasena = "1234"; // ----- ELIMINAR DESPUES DE LA PRUEBA !!!!!!
                for (int i = 0; i < cantidadUsuarios; i++) {
                    usuarios.add(new Usuario(
                            faker.name().fullName(),
                            faker.internet().emailAddress(),
                            passwordEncoder.encode(contrasena), // Contraseña cifrada
                            i < 3 ? Rol.CLIENTE : Rol.EMPRESA // Los primeros 3 son CLIENTE, los demás EMPRESA
                    ));
                }
                usuarioRepository.saveAll(usuarios);

                Usuario empresa1 = usuarios.get(3);
                Usuario empresa2 = usuarios.get(4);

                // Crear naves
                List<Nave> naves = new ArrayList<>();
                for (int i = 0; i < cantidadNaves; i++) {
                    naves.add(new Nave(
                            faker.space().nasaSpaceCraft(),
                            faker.space().agency(),
                            faker.number().numberBetween(100, 300),
                            faker.number().numberBetween(10, 20),
                            faker.number().numberBetween(10, 20)));
                }
                naveRepository.saveAll(naves);

                // Crear viajes
                List<Viaje> viajes = new ArrayList<>();
                for (int i = 0; i < cantidadViajes; i++) {
                    viajes.add(new Viaje(
                            i % 2 == 0 ? empresa1 : empresa2,
                            faker.space().planet(),
                            faker.space().planet(),
                            LocalDateTime.now().plusDays(faker.number().numberBetween(10, 100)),
                            faker.number().numberBetween(1000, 5000),
                            BigDecimal.valueOf(faker.number().randomDouble(2, 1000, 10000)),
                            naves.get(faker.number().numberBetween(0, cantidadNaves - 1)))); // Asignar una nave
                                                                                             // aleatoria;

                }
                viajeRepository.saveAll(viajes);

                // Crear reservas
                List<Reserva> reservas = new ArrayList<>();
                for (int i = 0; i < cantidadReservas; i++) {
                    Usuario usuario = usuarios.get(faker.number().numberBetween(0, cantidadUsuarios - 1));
                    Viaje viaje = viajes.get(faker.number().numberBetween(0, cantidadViajes - 1));
                    reservas.add(new Reserva(
                            usuario,
                            viaje,
                            viaje.getNave(),
                            LocalDateTime.now(),
                            faker.number().numberBetween(1, 5), // Número de asientos
                            faker.number().numberBetween(1, 10) // Número de filas
                    ));
                }
                reservaRepository.saveAll(reservas);

                // Crear usuario administrador
                PasswordEncoder contrasenaAdmin = new BCryptPasswordEncoder();
                Usuario admin = new Usuario(
                        "root", // Nombre
                        "admin@email.com", // Email
                        contrasenaAdmin.encode("1234"), // Contraseña
                        Rol.ADMIN // Rol
                );
                usuarioRepository.save(admin);

                PasswordEncoder contrasenaCliente = new BCryptPasswordEncoder();
                // Crear usuario cliente con contraseña cifrada
                Usuario cliente = new Usuario(
                        "cliente", // Nombre
                        "cliente@email.com", // Email
                        contrasenaCliente.encode("1234"), // Contraseña cifrada
                        Rol.CLIENTE // Rol
                );
                usuarioRepository.save(cliente);

                // Crear usuario empresa
                PasswordEncoder contrasenaEmpresa = new BCryptPasswordEncoder();
                Usuario empresa = new Usuario(
                        "empresa", // Nombre
                        "empresa@email.com", // Email
                        contrasenaEmpresa.encode("1234"), // Contraseña
                        Rol.EMPRESA // Rol
                );
                usuarioRepository.save(empresa);

                // Crear viajes para el usuario empresa
                List<Viaje> viajesEmpresa = new ArrayList<>();
                for (int i = 0; i < 2; i++) { // Crear exactamente 2 viajes
                    viajesEmpresa.add(new Viaje(
                            empresa, // Asignar al usuario empresa
                            faker.space().planet(),
                            faker.space().planet(),
                            LocalDateTime.now().plusDays(faker.number().numberBetween(10, 100)),
                            faker.number().numberBetween(1000, 5000),
                            BigDecimal.valueOf(faker.number().randomDouble(2, 1000, 10000)),
                            naves.get(faker.number().numberBetween(0, cantidadNaves - 1)) // Asignar una nave aleatoria
                    ));
                }
                viajeRepository.saveAll(viajesEmpresa);

                // Crear 3 reservas para cada viaje
                List<Reserva> reservasEmpresa = new ArrayList<>();
                for (Viaje viaje : viajesEmpresa) {
                    for (int i = 0; i < 3; i++) { // Crear exactamente 3 reservas por viaje
                        Usuario clienteEmpresa = usuarios.get(faker.number().numberBetween(0, cantidadUsuarios - 1)); // Seleccionar
                        reservasEmpresa.add(new Reserva(
                                clienteEmpresa, // Cliente que realiza la reserva
                                viaje, // Viaje asociado
                                viaje.getNave(), // Nave asociada al viaje
                                LocalDateTime.now(), // Fecha de la reserva
                                faker.number().numberBetween(1, 5), // Número de asientos
                                faker.number().numberBetween(1, 10) // Número de filas
                        ));
                    }
                }
                reservaRepository.saveAll(reservasEmpresa);

                System.out.println("Datos iniciales cargados correctamente.");

            } catch (Exception e) {
                // Imprimir el error si ocurre una excepción
                e.printStackTrace();
                System.out.println("Ocurrió un error al cargar los datos o al imprimir las relaciones.");
            }
        };
    }

}
