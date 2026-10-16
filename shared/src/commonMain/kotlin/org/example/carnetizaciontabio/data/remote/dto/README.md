# dto

Clases que describen la forma exacta de los datos que viajan por la API (marcadas como `@Serializable` para convertirse automaticamente a/desde JSON).

## Archivos esperados
`TrabajadorDto`, `CarnetDigitalDto`, `MarcacionDto`, `ConsentimientoDto`.

## Regla importante
Deben tener el MISMO nombre y los MISMOS campos que los DTO del backend. Asi el JSON se convierte solo, sin escribir conversiones a mano. Si un DTO cambia en el backend, hay que cambiarlo aqui tambien.

El PIN del trabajador solo se envia (login y registro), el backend nunca lo devuelve.

## Por que se llaman asi
DTO significa "Data Transfer Object" (objeto de transferencia de datos): solo sirve para transportar datos, no tiene logica.

## Equivalente en el backend
Paquete `dto`.
