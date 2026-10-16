# core

Piezas de apoyo que usa toda la app y que no pertenecen a una pantalla ni a una entidad en particular.

## Que va aqui
- Configuracion del cliente HTTP: direccion base del backend y como se adjunta el token en cada peticion.
- Guardado del token de sesion (JWT) que devuelve el login.
- Inyeccion de dependencias (Koin): el lugar donde se "arma" la app conectando cada pieza con la que necesita.
- Manejo comun de errores (sin conexion, sesion vencida, etc.).

## Por que se llama asi
"core" significa nucleo: lo que todo lo demas necesita para poder funcionar.

## Equivalente en el backend
El paquete `configuration` (seguridad, JWT, CORS).

## Ojo
Guardar el token de forma segura es distinto en Android y en iOS. Se declara aqui con `expect` y cada plataforma lo implementa con `actual` (carpetas `androidMain` e `iosMain`).
