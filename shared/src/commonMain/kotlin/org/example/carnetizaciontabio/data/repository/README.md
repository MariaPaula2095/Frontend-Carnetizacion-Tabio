# repository (data)

Implementaciones de los contratos definidos en `domain/repository`.
Aqui se usan los clientes de `data/remote/api` y se convierten los DTO en objetos de `domain/model`.

## Archivos esperados
`TrabajadorRepositoryImpl`, `CarnetDigitalRepositoryImpl`, `MarcacionRepositoryImpl`, `ConsentimientoRepositoryImpl`.

## Por que se llaman asi
El sufijo `Impl` (implementacion) es el mismo que usa el backend en sus servicios (`TrabajadorServiceImpl`): identifica la clase que cumple el contrato de una interfaz.

## Ojo
En el backend un `repository` habla con la base de datos. Aqui habla con la API del backend. El nombre es igual, pero la fuente de datos es distinta.
