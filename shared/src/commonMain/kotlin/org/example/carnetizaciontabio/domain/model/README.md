# model

Las entidades como las usa la app internamente, y los valores fijos (enums).

## Archivos esperados
- Entidades: `Trabajador`, `CarnetDigital`, `Marcacion`, `Consentimiento`.
- Enums: `EstadoTrabajador` (PENDIENTE, AUTORIZADO, INACTIVO, RECHAZADO), `TipoMarcacion` (ENTRADA, SALIDA_ALMUERZO, ENTRADA_ALMUERZO, SALIDA), `EstadoCarnet`, `TipoVinculacion`.

## Diferencia con los DTO
El DTO es como viajan los datos por la red; el model es como los usa la app. Pueden parecerse, pero estan separados para que un cambio en la API no obligue a reescribir toda la app.

## Equivalente en el backend
Paquete `model` (entidades y enums).
