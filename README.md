# Reserva Hoteles

Proyecto Java Spring Boot para la gestión de reservas de hoteles.

---

## Tecnologías

- Java 17
- Spring Boot 3
- PostgreSQL (producción)
- H2 (tests)
- Docker & Docker Compose
- Flyway (migraciones)
- Swagger/OpenAPI (documentación)
- JUnit, Mockito (tests)
- Spring Security (HTTP Basic Auth)

---

## Estructura del proyecto

```
.
├── src/
│   ├── main/
│   │   ├── java/com/hotel/         # Código fuente Java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/       # Migraciones Flyway
│   └── test/
│       ├── java/com/hotel/         # Tests unitarios e integración
│       └── resources/
│           └── application.properties
├── Dockerfile                      # Imagen backend
├── docker-compose.yml              # Orquestación de contenedores
├── pom.xml                         # Dependencias Maven
├── README.md                       # Este archivo
└── logs/                           # Logs de la aplicación
```

---

## Configuración rápida

### 1. Clona el repositorio

```sh
git clone https://github.com/Veik1/reserva-hoteles.git
cd reserva-hoteles
```

### 2. Compila y prueba la app localmente

```sh
mvn clean package
```

Esto ejecuta todos los tests (usando H2) y genera el JAR en `target/`.

### 3. Levanta la base de datos y el backend con Docker Compose

```sh
docker compose up --build
```

Esto construye la imagen del backend y levanta PostgreSQL y la app.

---

## Acceso a la aplicación

- **API y documentación Swagger:**  
  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Base de datos PostgreSQL:**  
  Host: `localhost`  
  Puerto: `5432`  
  Usuario: `hoteluser`  
  Contraseña: `hotelpass`  
  Base: `hotel`

---

## Seguridad

- **HTTP Basic Auth**
- **Usuario:** `usuario`
- **Contraseña:** `1234`
- **Usuario admin:** `admin` / `admin`
- Los roles y accesos están definidos en [`SecurityConfig.java`](src/main/java/com/hotel/config/SecurityConfig.java)

---

## Pruebas

Para ejecutar los tests (usa H2 en memoria):

```sh
mvn clean test
```

---

## Migraciones y Seeds

- Las migraciones Flyway (`src/main/resources/db/migration/`) crean el esquema y cargan datos iniciales (seeds) con IDs altos para evitar conflictos en tests.
- Los tests usan datos aleatorios y nunca chocan con los seeds.

---

## Ejemplos de uso de la API

> Todos los endpoints requieren autenticación básica.

### Crear un cliente

```sh
curl -X POST http://localhost:8080/api/clientes \
  -u usuario:1234 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan Perez","email":"juan@mail.com","dni":"12345678"}'
```

### Listar clientes

```sh
curl -X GET http://localhost:8080/api/clientes -u usuario:1234
```

### Crear una habitación (requiere rol ADMIN)

```sh
curl -X POST http://localhost:8080/api/habitaciones \
  -u admin:admin \
  -H "Content-Type: application/json" \
  -d '{"numero":101,"tipo":"Suite","disponible":true,"precio":2000.0,"hotel":{"id":1001}}'
```

### Listar habitaciones

```sh
curl -X GET http://localhost:8080/api/habitaciones -u usuario:1234
```

### Crear una reserva

```sh
curl -X POST http://localhost:8080/api/reservas \
  -u usuario:1234 \
  -H "Content-Type: application/json" \
  -d '{"cliente":{"id":3001},"habitacion":{"id":2001},"hotel":{"id":1001},"fechaInicio":"2025-06-20","fechaFin":"2025-06-22"}'
```

### Listar reservas

```sh
curl -X GET http://localhost:8080/api/reservas -u usuario:1234
```

---

## Notas

- El backend usa PostgreSQL en producción y H2 en memoria para pruebas.
- Las migraciones Flyway crean y llenan la base de datos automáticamente.
- Seguridad básica HTTP Basic (usuario: `usuario`, contraseña: `1234`).
- Puedes explorar y probar la API desde Swagger UI.
- Los tests de integración usan datos aleatorios y nunca chocan con los seeds.

---
