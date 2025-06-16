# Reserva Hoteles

Proyecto Java Spring Boot para la gestión de reservas de hoteles.

## Tecnologías

- Java 17
- Spring Boot 3
- PostgreSQL (producción)
- H2 (tests)
- Docker Compose
- Flyway (migraciones)
- Swagger/OpenAPI (documentación)
- JUnit, Mockito (tests)

## Uso rápido

1. **Clona el repositorio:**
   ```sh
   git clone https://github.com/Veik1/reserva-hoteles.git
   cd reserva-hoteles
   ```

2. **Levanta los servicios con Docker Compose:**
   ```sh
   docker-compose up --build
   ```
<<<<<<< HEAD
4. Accede a la API en [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
=======

3. **Accede a la API y documentación:**
   - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Estructura del proyecto

- `src/main/java/com/hotel/` - Código fuente Java
- `src/main/resources/` - Configuración, migraciones y recursos
- `src/test/java/com/hotel/` - Pruebas unitarias e integración
- `src/test/resources/` - Configuración para tests (H2)
- `docker-compose.yml` - Orquestación de contenedores
- `Dockerfile` - Imagen del backend
- `README.md` - Documentación

## Pruebas

Para ejecutar los tests (usa H2 en memoria):

```sh
./mvnw clean test
```

## Notas

- El backend usa PostgreSQL en producción y H2 en memoria para pruebas.
- Las migraciones Flyway crean y llenan la base de datos automáticamente.
- Seguridad básica HTTP Basic (usuario: `usuario`, contraseña: `1234`).
>>>>>>> 1e113fe (backend major update)
