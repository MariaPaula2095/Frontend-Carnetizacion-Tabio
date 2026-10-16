# data

Capa de datos: todo lo que habla con el exterior, en nuestro caso con el backend.
Aqui se sabe COMO se piden y se envian los datos, pero no que significan para el negocio.

## Subcarpetas
- `remote`: la comunicacion con la API del backend (clientes y DTO).
- `repository`: las implementaciones que usan esa comunicacion para cumplir lo que pide `domain`.

## Por que se llama asi
"data" es el nombre estandar de esta capa en Clean Architecture.

## Equivalente en el backend
Es el "lado cliente" de las capas `controller`, `dto` y `repository` del backend.
