# core

Piezas de apoyo que usa toda la app y que no pertenecen a una pantalla ni a una entidad en particular.

## Que va aqui
- Configuracion del cliente HTTP: direccion base del backend y como se adjunta el token en cada peticion.
- Guardado del token de sesion (JWT) que devuelve el login.
- Manejo comun de errores: `ApiResult` (exito o error) y `safeApiCall`, que convierte cualquier fallo (sin conexion, 
  sesion vencida, etc.) en un `ApiResult.Error`. `ApiResultExtensions.kt` agrega `map`, para transformar el contenido de un resultado exitoso.
- `CoreModule`: el modulo de Koin con las piezas de esta carpeta (cliente HTTP y almacenamiento del token).

## Por que se llama asi
"core" significa nucleo: lo que todo lo demas necesita para poder funcionar.

## Equivalente en el backend
El paquete `configuration` (seguridad, JWT, CORS).

## Ojo
- Guardar el token de forma segura es distinto en Android y en iOS. Se declara aqui con `expect` y cada plataforma lo implementa con `actual` (carpetas `androidMain` e `iosMain`).
- Quien junta todos los modulos de Koin y arranca la app es la carpeta `di`.