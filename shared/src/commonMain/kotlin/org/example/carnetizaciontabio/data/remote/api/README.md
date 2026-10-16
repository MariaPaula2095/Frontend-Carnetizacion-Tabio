# api

Un archivo por cada controller del backend que usa la app del Trabajador. Cada archivo es la "puerta" hacia un grupo de endpoints.

## Archivos esperados
- `TrabajadorApi`: registrar y login (`/api/trabajadores/registrar`, `/api/trabajadores/login`).
- `CarnetDigitalApi`: consultar el propio carnet (`/api/carnets/mi-carnet`).
- `MarcacionApi`: marcar por escaneo y ver historial (`/api/marcaciones/escanear`, `/api/marcaciones/mi-historial`).
- `ConsentimientoApi`: aceptar y consultar consentimiento (`/api/consentimientos/aceptar`, `/api/consentimientos/mi-consentimiento`).

Los endpoints de Administrador (`/api/administradores/...`, listar, autorizar, reportes) NO van aqui: pertenecen a la app web.

## Por que se llaman asi
El nombre sigue el del controller del backend con el sufijo `Api`, para ubicar rapido a que parte del backend corresponde cada archivo.

## Equivalente en el backend
Paquete `controller`.
