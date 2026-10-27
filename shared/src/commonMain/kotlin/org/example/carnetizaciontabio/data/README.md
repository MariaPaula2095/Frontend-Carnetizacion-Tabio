# data

Capa de datos: todo lo que habla con el exterior, en nuestro caso con el backend.
Aqui se sabe COMO se piden y se envian los datos, pero no que significan para el negocio.

## Subcarpetas
- `remote`: la comunicacion con la API del backend (clientes y DTO).
- `mapper`: funciones que convierten entre DTO (lo que viaja por la red) y model (lo que usa la app). `toDomain()` va del backend a la app y `toDto()` hace el camino inverso.
- `repository`: las implementaciones que usan esa comunicacion para cumplir lo que pide `domain`.

## Archivos sueltos
- `DataModule.kt`: le dice a Koin como construir las `Api`, los repositorios y los casos de uso.

## Por que se llama asi
"data" es el nombre estandar de esta capa en Clean Architecture.

## Equivalente en el backend
Es el "lado cliente" de las capas `controller`, `dto` y `repository` del backend.
