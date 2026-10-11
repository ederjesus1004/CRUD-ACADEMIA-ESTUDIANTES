# Academia: registro de estudiantes

Aplicación web para registrar a los estudiantes de una academia. El backend es una API REST en Spring Boot y el frontend es una pantalla en Angular que la consume.

Desde la pantalla se puede listar, registrar, editar y eliminar estudiantes. Los errores de validación de la API se muestran debajo de cada campo del formulario.

## Tecnologías

| Parte | Tecnologías |
|---|---|
| Backend | Java 21, Spring Boot 4 (Web MVC, Data JPA, Validation), Lombok, Maven |
| Base de datos | PostgreSQL |
| Frontend | Angular (componentes standalone y signals), TypeScript, HttpClient, CSS |

## Cómo se conectan

```
Navegador (localhost:4200)              Spring Boot (localhost:8080)
┌───────────────────────┐   HTTP + JSON  ┌──────────────────────────┐
│ Angular               │ ─────────────▶ │ Controller → Service     │
│  formulario + tabla   │                │   → Repository           │
│  estudiante.service   │ ◀───────────── │   → PostgreSQL           │
└───────────────────────┘                └──────────────────────────┘
```

El backend tiene configurado CORS para aceptar peticiones solo desde `http://localhost:4200`.

## Estructura del proyecto

```
academia/
├── api-estudiantes/                 backend (Spring Boot)
│   └── src/main/java/dev/eder/estudiantes/
│       ├── entity/                  Estudiante (la tabla)
│       ├── repository/              consultas a la base de datos
│       ├── dto/                     EstudianteRequest y EstudianteResponse
│       ├── exception/               excepciones, ErrorResponse y GlobalExceptionHandler
│       ├── mapper/                  convierte entre DTO y entidad
│       ├── service/                 interface + implementación con las reglas
│       ├── controller/              endpoints REST
│       └── config/                  CORS
│
└── estudiantes-web/                 frontend (Angular)
    └── src/app/
        ├── estudiante.model.ts      forma del JSON (Request, Response, ErrorResponse)
        ├── estudiante.service.ts    llamadas HTTP a la API
        ├── app.config.ts            activa HttpClient
        ├── app.ts                   lógica de la pantalla
        ├── app.html                 formulario y tabla
        └── app.css                  estilos
```

## Endpoints

| Método | Ruta | Descripción | Respuesta |
|---|---|---|---|
| GET | `/api/estudiantes` | Lista todos los estudiantes | 200 |
| GET | `/api/estudiantes/{id}` | Busca un estudiante por id | 200 / 404 |
| POST | `/api/estudiantes` | Registra un estudiante | 201 / 400 / 409 |
| PUT | `/api/estudiantes/{id}` | Actualiza un estudiante | 200 / 400 / 404 / 409 |
| DELETE | `/api/estudiantes/{id}` | Elimina un estudiante | 204 / 404 |

## Reglas

- El código tiene la forma `U` + 8 dígitos (por ejemplo `U20241234`) y no se puede repetir.
- El email no se puede repetir. Se guarda sin espacios y en minúsculas.
- Nombres, apellidos y carrera son obligatorios.
- El ciclo va del 1 al 10.
- Si un dato no cumple, la API responde **400** con el error de cada campo, y la pantalla lo muestra debajo del input.
- Si el código o el email ya los tiene otro estudiante, la API responde **409** y la pantalla muestra un aviso.

## Cómo correrlo

### Requisitos

- Java 21
- PostgreSQL
- Node 22 o superior
- Angular CLI: `npm install -g @angular/cli`

### 1. Base de datos

En pgAdmin o en psql:

```sql
CREATE DATABASE estudiantes_db;
```

### 2. Backend

Configura tu contraseña de PostgreSQL como variable de entorno. En IntelliJ: *Run → Edit Configurations → Environment variables*:

```
DB_PASSWORD=tu_contraseña
```

Si no la defines, se usa `postgres`. Después ejecuta:

```bash
cd api-estudiantes
./mvnw spring-boot:run
```

La API queda en `http://localhost:8080` y la tabla `estudiantes` se crea sola.

### 3. Frontend

En otra terminal:

```bash
cd estudiantes-web
npm install
ng serve
```

Abre `http://localhost:4200`. Si la pantalla dice "No hay conexión con la API", revisa que el backend esté corriendo.

## Ejemplo de la API

**POST** `/api/estudiantes`

```json
{
  "codigo": "U20241234",
  "nombres": "Ana Lucía",
  "apellidos": "Torres Ramos",
  "email": "Ana.Torres@mail.com",
  "carrera": "Ingeniería de Sistemas",
  "ciclo": 5
}
```

Respuesta **201 Created**:

```json
{
  "id": 1,
  "codigo": "U20241234",
  "nombres": "Ana Lucía",
  "apellidos": "Torres Ramos",
  "descripcion": "Ana Lucía Torres Ramos - Ingeniería de Sistemas",
  "email": "ana.torres@mail.com",
  "carrera": "Ingeniería de Sistemas",
  "ciclo": 5,
  "fechaRegistro": "2026-10-10T20:30:00"
}
```

Respuesta **400 Bad Request** si el código no tiene el formato correcto:

```json
{
  "fecha": "2026-10-10T20:31:00",
  "estado": 400,
  "mensaje": "Datos inválidos",
  "errores": {
    "codigo": "El código debe ser U seguido de 8 dígitos"
  }
}
```

## Autor

Eder Cuaresma · [GitHub](https://github.com/ederjesus1004)
