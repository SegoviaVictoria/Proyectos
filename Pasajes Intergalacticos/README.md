# Pasajes Intergalácticos

Plataforma de gestión de pasajes intergalácticos para OrbitExpress.

---

## Requisitos del sistema

- **Java:** 17
- **Maven:** 3.6+
- **Spring Boot:** 3.2.5
- **Base de datos:** H2 embebida (por defecto)
- **Navegador web** moderno para acceder al frontend y Swagger UI

---

## Instalación y compilación

1. **Clona el repositorio:**
   ```sh
   git clone <https://github.com/STEMgranada-1DAW-2425/proyecto-3t-Victoria-STEM>
   cd pasajes-intergalacticos
   ```

2. **Compila el proyecto:**
   ```sh
   mvn clean install
   ```

3. **Ejecuta la aplicación:**
   ```sh
   mvn spring-boot:run
   ```
   o ejecuta el archivo:
   ```sh
   PasajesIntergalacticosApplicacion.java
   ```

---

## Acceso a la aplicación

- **Frontend:**  
  Abre tu navegador y accede a los archivos HTML en la carpeta `src/main/resources/static` (`login.html` o `admin.html`).

- **API REST:**  
  El backend estará disponible en:  
  ```
  http://localhost:8080/
  ```

---

## Rutas relevantes de la API

Algunos endpoints principales (pueden variar según tu implementación):

- `GET    /api/usuarios`           → Listar usuarios
- `POST   /api/usuarios`           → Crear usuario
- `POST   /api/usuarios/login`     → Login de usuario
- `GET    /api/viajes`             → Listar viajes
- `GET    /api/viajes/{id}`        → Obtener viaje por ID
- `POST   /api/viajes`             → Crear viaje
- `DELETE /api/viajes/{id}`        → Eliminar viaje
- `GET    /api/reservas`           → Listar reservas
- `POST   /api/reservas`           → Crear reserva
- `DELETE /api/reservas/{id}`      → Eliminar reserva

> **Nota:** Consulta la documentación Swagger para ver todos los endpoints y detalles.

---

## Documentación Swagger / OpenAPI

- Accede a la documentación interactiva de la API en:  
  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
  o  
  [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## Acceso a la consola H2

- **URL:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL:** `jdbc:h2:mem:db_orbitexpress`
- **Usuario:** `sa`
- **Contraseña:** *(dejar en blanco)*

---


## Datos de prueba

Existen tres usuarios demo:

email: cliente@email.com
contrasena: 1234

email: empresa@email.com
contrasena: 1234

email: admin@email.com
contrasena: 1234

---

## Notas adicionales

- El proyecto incluye dependencias para Swagger/OpenAPI, H2, Spring Security (puede estar deshabilitado), y Java Faker para generación de datos de prueba.
- Puedes modificar la configuración de la base de datos y otros parámetros en `src/main/resources/application.properties`.

---

**Desarrollado por Victoria Segovia**

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/nEmZ8A4d)
