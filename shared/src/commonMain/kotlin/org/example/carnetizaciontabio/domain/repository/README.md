# repository (domain)

Interfaces (contratos) que dicen QUE operaciones existen para obtener y guardar datos, sin decir COMO se hacen.

## Archivos esperados
`TrabajadorRepository`, `CarnetDigitalRepository`, `MarcacionRepository`, `ConsentimientoRepository`.

Ejemplo: `TrabajadorRepository` declara `login(cedula, pin)` pero no sabe si eso se hace con internet, con una base local o con datos de prueba.

## Por que son interfaces
Asi los casos de uso no dependen de la API: se puede cambiar la implementacion o simularla en pruebas sin tocar el resto de la app. La implementacion real esta en `data/repository`.

## Equivalente en el backend
Las interfaces del paquete `service` (por ejemplo `TrabajadorService`).
