# mapper

Funciones que convierten entre DTO (como viajan los datos por la red) y model (como los usa la app).

## Archivos
`TrabajadorMapper`, `CarnetDigitalMapper`, `MarcacionMapper`, `ConsentimientoMapper`.

## Como funcionan
- `toDomain()`: del backend hacia la app (DTO a model). Reemplaza los valores nulos por valores seguros para que las pantallas no tengan que revisarlos.
- `toDto()`: de la app hacia el backend. Hoy solo existe para registrar un trabajador (`NuevoTrabajador`), porque lleva el `pin`, que el model `Trabajador` no tiene.
- Son funciones de extension: se usan como `dto.toDomain()`, pero necesitan su propio `import`.

## Por que se llama asi
"Mapear" es convertir un tipo de dato en otro, campo por campo.

## Equivalente en el backend
La conversion entre entidades y DTO (en el backend la hace una clase mapper o el propio servicio, segun como este armado).

## Ojo
Los DTO no tienen logica: solo describen el JSON. Toda decision sobre que hacer con un nulo vive aqui.