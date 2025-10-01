# 🛍️ Tienda Online - Gestión de Productos
---
## 📝 Descripción

Sistema full-stack para gestión de tienda de libros online con:
- **Backend**: API REST con Spring Boot
- **Frontend**: Interfaz React
- **Base de datos**: MySQL

---

## ⚗️ Características

✔️ CRUD completo de productos  
✔️ Asociación productos-categorías  
✔️ Validación en frontend/backend  
✔️ Diseño responsive con Bootstrap  
✔️ Autenticación JWT (próximamente)

---

## 🛠️ Tecnologías

### Backend
| Tecnología       | Versión  |
|------------------|----------|
| Java             | 17       |
| Spring Boot      | 3.x      |
| Spring Data JPA  | 3.x      |
| MySQL            | 8+       |

### Frontend
| Tecnología       | Versión  |
|------------------|----------|
| React            | 18       |
| React Router     | 6        |
| Axios            | 1.x      |
| Bootstrap        | 5        |

---

## 🧱 Estructura del proyecto

### Diagrama de Clases
Claro, las tablas con arte ASCII muchas veces quedan desalineadas en Markdown por la fuente o espacios. Para que se vea mejor, recomiendo usar **tablas Markdown estándar**.

Aquí te dejo la misma info en tabla Markdown, mucho más limpia y legible:

```markdown
| Customer   |    | Order         |    | OrderDetail   |    | Product      |    | Category  |
|------------|----|---------------|----|---------------|----|--------------|----|-----------|
| id         |    | id            |    | id            |    | id           |    | id        |
| firstName  |    | date          |    | quantity      |    | name         |    | name      |
| lastName   |    | total         |    | price         |    | price        |    |           |
| email      |    | shipped       |    | order_id (FK) |    | stock        |    |           |
| phone      |    | cust_id (FK)  |    | prod_id (FK)  |    | update       |    |           |
| regDate    |    |               |    |               |    | cat_id (FK)  |    |           |
| isActive   |    |               |    |               |    |              |    |           |
```

**Relaciones**:

- Customer (1) --- (*) Order
- Order (1) --- (*) OrderDetail
- OrderDetail (*) --- (1) Product
- Product (*) --- (1) Category



### 🛝 Estructura de las carpetas

```markdown
tienda-online/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/tienda/
│   │   │   │   ├── controller/        # Controladores REST
│   │   │   │   ├── dto/               # Clases DTO (ProductoDTO, CategoriaDTO)
│   │   │   │   ├── model/             # Entidades JPA (Producto, Categoria)
│   │   │   │   ├── repository/        # Interfaces de acceso a datos
│   │   │   │   ├── service/           # Lógica de negocio
│   │   │   │   └── TiendaApplication.java
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── data.sql           # (opcional) Datos iniciales
│   └── pom.xml                        # Configuración Maven
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── components/                # Componentes reutilizables
│   │   ├── pages/                     # Páginas como Productos, Categorías
│   │   ├── services/                  # Axios, API helpers
│   │   ├── App.js
│   │   ├── index.js
│   └── package.json                   # Configuración npm
└── README.md
```

---
---

# 🛍️ Instrucciones para Iniciar el Proyecto Tienda Online en Otro Ordenador


## Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

- **Java JDK 17**  
- **Maven 3.6+**  
- **Node.js 16+ y npm**  
- **MySQL 8+**  

---

## 1. Configurar la Base de Datos (MySQL)

1. Asegúrate de que MySQL esté instalado y corriendo.

2. Abre la consola o cliente de MySQL y crea la base de datos ejecutando:

```sql
CREATE DATABASE tienda_db;
```

3. Configura las credenciales y conexión para el backend editando el archivo:

```
backend/src/main/resources/application.properties
```

Modifica estas líneas para que coincidan con tu configuración local de MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tienda_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

---

## 2. Iniciar el Backend (Spring Boot)

1. Abre una terminal y navega a la carpeta del backend:

```bash
cd backend
```

2. Instala dependencias y compila el proyecto:

```bash
mvn clean install
```

3. Ejecuta la aplicación Spring Boot:

```bash
mvn spring-boot:run
```

- Por defecto el backend correrá en: `http://localhost:8080`

---

## 3. Iniciar el Frontend (React)

1. Abre otra terminal y navega a la carpeta del frontend:

```bash
cd frontend
```

2. Instala las dependencias de Node.js:

```bash
npm install
```

3. Inicia el servidor de desarrollo de React:

```bash
npm start
```

- Esto abrirá la app React en: `http://localhost:3000`
- El frontend consume automáticamente la API del backend configurada para `http://localhost:8080`.

---

## Notas importantes

- El backend debe estar corriendo antes de iniciar el frontend para que la API esté disponible.
- Asegúrate que los puertos `8080` y `3000` estén libres en tu máquina.
- En el archivo `frontend/package.json` debería estar definido el proxy para conectar con backend:

```json
"proxy": "http://localhost:8080"
```

---

## Resumen rápido

```bash
# Configura MySQL (solo una vez)
CREATE DATABASE tiendaOnline;
CREATE USER 'user'@'%' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON tiendaOnline.* TO 'user'@'%';
FLUSH PRIVILEGES;

# Backend
cd backend
mvn clean install # necesitas tener instalado maven, sino necesitarás instalarlos en tu sistema
mvn spring-boot:run

# Frontend
cd frontend
npm install
npm start
```

🎉¡Listo! Con estos pasos podrás levantar el proyecto completo en tu ordenador.

