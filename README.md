# API de Franquicias

API REST construida con **Spring Boot 3** y **MongoDB** para administrar franquicias, sus sucursales y los productos de cada sucursal.

## Tecnologías

- Java 17
- Spring Boot 3.3 (Web, Data MongoDB, Validation)
- MongoDB 7
- Docker y Docker Compose
- JUnit 5 + Mockito

## Modelo de datos

Cada franquicia se guarda como un único documento en MongoDB, con sus sucursales y productos embebidos:

```json
{
  "id": "6702f1...",
  "nombre": "Burger House",
  "sucursales": [
    {
      "id": "a1b2-...",
      "nombre": "Sucursal Centro",
      "productos": [
        { "id": "c3d4-...", "nombre": "Hamburguesa", "stock": 50 }
      ]
    }
  ]
}
```

## Cómo ejecutar en local

### Opción 1: con Docker (recomendada)

Requisitos: Docker Desktop instalado.

```bash
docker compose up --build
```

Esto levanta MongoDB y la API. La API queda disponible en `http://localhost:8080`.

Para detener: `docker compose down` (agrega `-v` para borrar también los datos).

### Opción 2: sin Docker para la API

Requisitos: Java 17, Maven y un MongoDB corriendo en `localhost:27017`.
Puedes levantar solo Mongo con Docker:

```bash
docker compose up -d mongo
mvn spring-boot:run
```

### Persistencia de datos

La solución usa **MongoDB**. La base de datos de la solución está alojada en
**MongoDB Atlas** (plan gratuito, proveedor AWS, región XXXX).

La conexión se configura con la variable de entorno `MONGODB_URI`:

- **Sin configurar nada** (opción por defecto): la aplicación usa el MongoDB local
  que levanta Docker Compose. No se requiere ninguna configuración adicional
  para ejecutar el proyecto.
- **Para conectarse a una base propia en MongoDB Atlas**:
  - Con Docker: copiar `.env.example` como `.env` y colocar la cadena de conexión.
  - Desde el IDE o Maven: definir la variable de entorno `MONGODB_URI` con la
    cadena de conexión antes de ejecutar la aplicación.

> El archivo `.env` contiene credenciales y no se sube al repositorio.

Define la variable de entorno `MONGODB_URI` con la cadena de conexión de Atlas:

```bash
# Linux / Mac
export MONGODB_URI="mongodb+srv://usuario:clave@cluster.xxxxx.mongodb.net/franquicias"
# Windows PowerShell
$env:MONGODB_URI="mongodb+srv://usuario:clave@cluster.xxxxx.mongodb.net/franquicias"

mvn spring-boot:run
```

Si no se define, se usa `mongodb://localhost:27017/franquicias`.

## Ejecutar las pruebas

```bash
mvn test
```

## Endpoints

Base: `http://localhost:8080/api/franquicias`

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/franquicias` | Crear franquicia |
| GET | `/api/franquicias/{franquiciaId}` | Consultar una franquicia |
| POST | `/api/franquicias/{franquiciaId}/sucursales` | Agregar sucursal |
| PATCH | `/api/franquicias/{franquiciaId}/sucursales/{sucursalId}/nombre` | Actualizar nombre de sucursal |
| POST | `/api/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos` | Agregar producto |
| DELETE | `/api/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}` | Eliminar producto |
| PATCH | `/api/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/stock` | Modificar stock |
| GET | `/api/franquicias/{franquiciaId}/productos/mayor-stock` | Producto con más stock por sucursal |

### Ejemplos con curl

```bash
# 1. Crear franquicia
curl -X POST http://localhost:8080/api/franquicias \
  -H "Content-Type: application/json" -d '{"nombre":"Burger House"}'

# 2. Agregar sucursal (reemplaza {fId})
curl -X POST http://localhost:8080/api/franquicias/{fId}/sucursales \
  -H "Content-Type: application/json" -d '{"nombre":"Sucursal Centro"}'

# 3. Agregar producto
curl -X POST http://localhost:8080/api/franquicias/{fId}/sucursales/{sId}/productos \
  -H "Content-Type: application/json" -d '{"nombre":"Hamburguesa","stock":50}'

# 4. Modificar stock
curl -X PATCH http://localhost:8080/api/franquicias/{fId}/sucursales/{sId}/productos/{pId}/stock \
  -H "Content-Type: application/json" -d '{"stock":120}'

# 5. Eliminar producto
curl -X DELETE http://localhost:8080/api/franquicias/{fId}/sucursales/{sId}/productos/{pId}

# 6. Producto con más stock por sucursal
curl http://localhost:8080/api/franquicias/{fId}/productos/mayor-stock

# 7. Renombrar sucursal
curl -X PATCH http://localhost:8080/api/franquicias/{fId}/sucursales/{sId}/nombre \
  -H "Content-Type: application/json" -d '{"nombre":"Sucursal Norte"}'
```

Respuesta de ejemplo de `mayor-stock`:

```json
[
  { "sucursalId": "a1b2-...", "sucursalNombre": "Sucursal Centro",
    "productoId": "c3d4-...", "productoNombre": "Hamburguesa", "stock": 120 }
]
```

### Errores

- `404` si la franquicia, sucursal o producto no existe: `{"error": "No existe la sucursal con id ..."}`
- `400` si los datos son inválidos (nombre vacío o stock negativo).

## Estructura del proyecto

```
src/main/java/com/ejemplo/franquicias
├── FranquiciasApplication.java   # Arranque de la app
├── controller/                   # Endpoints REST
├── service/                      # Lógica de negocio
├── repository/                   # Acceso a MongoDB
├── model/                        # Franquicia, Sucursal, Producto
├── dto/                          # Objetos de entrada/salida de la API
└── exception/                    # Manejo de errores
```
