# domain

Capa del negocio: define QUE hace la app, sin importar como se muestra ni de donde vienen los datos.
No debe depender de Ktor, de Compose ni de ninguna plataforma. Es la capa mas pura y la que menos cambia.

## Subcarpetas
- `model`: las entidades tal como las entiende la app.
- `repository`: los contratos (interfaces) de acceso a datos.
- `usecase`: cada accion concreta que puede hacer el usuario.

## Por que se llama asi
"domain" (dominio) es el nombre estandar en Clean Architecture para la logica propia del problema que se resuelve: carnets, marcaciones, consentimiento.

## Equivalente en el backend
Une lo que en el backend esta en `model` y en `service`.
