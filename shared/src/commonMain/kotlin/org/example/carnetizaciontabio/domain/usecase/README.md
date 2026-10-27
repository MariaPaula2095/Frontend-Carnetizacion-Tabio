# usecase

Una clase por cada accion concreta que puede hacer el usuario en la app. Cada una hace una sola cosa.

## Archivos esperados
- `LoginTrabajadorUseCase`
- `RegistrarTrabajadorUseCase`
- `ObtenerMiCarnetUseCase`
- `RegistrarMarcacionUseCase` (recibe el codigo QR escaneado)
- `ObtenerHistorialUseCase`
- `AceptarConsentimientoUseCase`
- `ObtenerMiConsentimiento`
- `ObtenerAreas` (las areas para el formulario de registro; no requiere sesion)
- `ObtenerTrabajadorActual` (el trabajador que inicio sesion, para el carnet; es null si no hay sesion)
- `CerrarSesion` (borra el token y los datos del trabajador en memoria)

## Por que se llaman asi
El nombre es un verbo + lo que hace, y termina en `UseCase` (caso de uso). Al leer la lista de archivos se entiende todo lo que la app es capaz de hacer.

## Quien los usa
Los `ViewModel` de `presentation`: la pantalla pide la accion, el caso de uso la ejecuta usando los repositorios.

## Equivalente en el backend
Los metodos de las clases del paquete `service` (por ejemplo `login` y `registrar` de `TrabajadorService`).
