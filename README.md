# Reserva Hoteles

Proyecto de gestión de reservas de hoteles.  
Stack: **Java 17 + Spring Boot 3 + PostgreSQL + Vue 3 + Pinia + Vite**.

---

## Tecnologías principales

- **Backend:** Java 17, Spring Boot 3, Spring Data JPA, Spring Security (solo roles, sin JWT), Flyway, PostgreSQL, H2 (tests), JUnit 5, Mockito
- **Frontend:** Vue 3, Vite, Pinia, Vue Router, Axios
- **Infraestructura:** Docker, Docker Compose

---

## Estructura del proyecto

```
.
├── src/
│   ├── main/
│   │   ├── java/com/hotel/         # Código fuente Java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/       # Migraciones Flyway y seeds
│   └── test/
│       ├── java/com/hotel/         # Tests unitarios e integración
│       └── resources/
│           └── application.properties
├── vue-app/
│   ├── src/
│   │   ├── views/                  # Vistas Vue
│   │   ├── components/             # Componentes Vue
│   │   ├── store/                  # Pinia stores (sin tokens)
│   │   ├── services/               # Lógica de API (axios, sin tokens)
│   │   └── assets/
│   ├── public/
│   ├── package.json
│   └── vite.config.js
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

### 2. Backend: compila y prueba la app localmente

```sh
mvn clean package
```
Esto ejecuta todos los tests (usando H2) y genera el JAR en `target/`.

### 3. Backend: levanta la base de datos y el backend con Docker Compose

```sh
docker compose up --build
```
Esto construye la imagen del backend y levanta PostgreSQL y la app.

### 4. Frontend: instala dependencias y ejecuta

```sh
cd vue-app
npm install
npm run dev
```
El frontend quedará disponible en [http://localhost:5173](http://localhost:5173) (o el puerto que indique Vite).

---

## Acceso a la aplicación

- **Frontend:**  
  [http://localhost:5173](http://localhost:5173)
- **API y documentación Swagger:**  
  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Base de datos PostgreSQL:**  
  Host: `localhost`  
  Base: `hotel`  
  Usuario: `hoteluser`  
  Contraseña: `hotelpass`

---

## Seguridad

- **NO se usa JWT ni refresh tokens.**
- **NO se usa bcrypt ni hash de contraseñas.**
- Las contraseñas se almacenan y validan en texto plano (solo para fines de demo/pruebas).
- Los roles (`ADMIN`, `USER`) se gestionan en la entidad `Usuario`.
- El backend permite autenticación simple por usuario/contraseña.
- Los tests y seeds usan usuarios en texto plano.
- El frontend no maneja tokens ni lógica de refresco.

### Usuarios por defecto (seeds y tests)

- **Usuario:** `usuario` / `1234`
- **Admin:** `admin` / `admin`

---

## Migraciones y Seeds

- Las migraciones Flyway (`src/main/resources/db/migration/`) crean el esquema y cargan datos iniciales (usuarios, hoteles, etc).
- Los tests usan H2 en memoria y datos de prueba.

---

## Pruebas

Para ejecutar los tests (usa H2 en memoria):

```sh
mvn clean test
```

---

## Endpoints principales

- `/api/auth/login` — Login simple (usuario/contraseña en texto plano)
- `/api/hoteles` — CRUD de hoteles
- `/api/habitaciones` — CRUD de habitaciones
- `/api/clientes` — CRUD de clientes
- `/api/reservas` — CRUD de reservas

Consulta la documentación Swagger en [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Frontend

- Vue 3 + Vite + Pinia + Vue Router + Axios
- No hay lógica de tokens ni refresh en el store ni en los servicios.
- UX: loaders, mensajes claros, diseño responsive.

### Scripts útiles

```sh
npm run dev      # Levanta el frontend en modo desarrollo
npm run build    # Compila para producción
npm run lint     # Lint con ESLint
npm run format   # Formatea el código con Prettier
```

---

## Docker

- Levanta backend y base de datos con:
  ```sh
  docker compose up --build
  ```
- El backend queda en [http://localhost:8080](http://localhost:8080)
- La base de datos PostgreSQL queda en el puerto 5432

---