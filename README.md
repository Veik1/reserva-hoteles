# Reserva Hoteles

Proyecto Java Spring Boot + Vue 3 para la gestión de reservas de hoteles.

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
- Spring Security (JWT y roles, refresh tokens)
- BCrypt (hash de contraseñas)
- Vue 3 + Vite (frontend)
- Axios, Pinia (frontend)

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
├── vue-app/
│   ├── src/
│   │   ├── views/                  # Vistas Vue
│   │   ├── components/             # Componentes Vue (Spinner, AlertMessage, etc)
│   │   ├── store/                  # Pinia stores
│   │   ├── services/               # Lógica de API (axios)
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

---

### 2. Backend: compila y prueba la app localmente

```sh
mvn clean package
```

Esto ejecuta todos los tests (usando H2) y genera el JAR en `target/`.

---

### 3. Backend: levanta la base de datos y el backend con Docker Compose

```sh
docker compose up --build
```

Esto construye la imagen del backend y levanta PostgreSQL y la app.

---

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
  Puerto: `5432`  
  Usuario: `hoteluser`  
  Contraseña: `hotelpass`  
  Base: `hotel`

---

## Seguridad

- **JWT + Refresh Token**
- **Usuario:** `usuario`
- **Contraseña:** `1234`
- **Usuario admin:** `admin` / `admin`
- Los roles y accesos están definidos en [`SecurityConfig.java`](src/main/java/com/hotel/config/SecurityConfig.java)
- El frontend maneja automáticamente la expiración del JWT y solicita uno nuevo usando el refresh token.

---

## UX y Accesibilidad

- **Loaders (spinners)** y **mensajes claros** en todas las vistas.
- **Mensajes de error y éxito** visibles y accesibles.
- **Diseño responsive** y colores fríos.
- **Navegación rápida** y accesible desde la Home.

---

## Ejemplos de uso de la API (con JWT)

### 1. Obtener token JWT

```sh
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"usuario","password":"1234"}'
```

La respuesta será:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
  "username": "usuario",
  "role": "USER"
}
```

### 2. Usar el token JWT en los endpoints

Guarda el valor de `token` y úsalo así:

```sh
curl -X GET http://localhost:8080/api/clientes \
  -H "Authorization: Bearer TU_TOKEN_AQUI"
```

### 3. Refrescar el token JWT

```sh
curl -X POST http://localhost:8080/api/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{"refreshToken":"xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"}'
```

### 4. Probar la API en Swagger

- Ve a [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Haz clic en "Authorize" e ingresa:  
  `Bearer TU_TOKEN_AQUI`

---

## Pruebas

Para ejecutar los tests (usa H2 en memoria):

```sh
mvn clean test
```

---

## Migraciones y Seeds

- Las migraciones Flyway (`src/main/resources/db/migration/`) crean el esquema y cargan datos iniciales (seeds).
- Los tests usan datos aleatorios y nunca chocan con los seeds.

---

## Notas

- El backend usa PostgreSQL en producción y H2 en memoria para pruebas.
- Las migraciones Flyway crean y llenan la base de datos automáticamente.
- Seguridad JWT (usuario: `usuario`, contraseña: `1234`).
- Puedes explorar y probar la API desde Swagger UI.
- Los tests de integración usan datos aleatorios y nunca chocan con los seeds.
- El frontend maneja automáticamente la expiración de sesión y muestra mensajes claros al usuario.

---

## Paso a paso para preparar y probar todo

### 1. Clonar el repositorio

```sh
git clone https://github.com/Veik1/reserva-hoteles.git
cd reserva-hoteles
```

### 2. Compilar y probar el backend

```sh
mvn clean package
```

### 3. Levantar backend y base de datos con Docker

```sh
docker compose up --build
```

- Esto deja el backend en [http://localhost:8080](http://localhost:8080)
- La base de datos PostgreSQL queda en el puerto 5432

### 4. Instalar y ejecutar el frontend

```sh
cd vue-app
npm install
npm run dev
```

- El frontend queda en [http://localhost:5173](http://localhost:5173)

### 5. Probar la aplicación

- Ingresa a [http://localhost:5173](http://localhost:5173)
- Inicia sesión con `usuario` / `1234` o `admin` / `admin`
- Prueba las vistas, la administración y la experiencia de usuario
- Si quieres probar la API directamente, usa Swagger en [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---