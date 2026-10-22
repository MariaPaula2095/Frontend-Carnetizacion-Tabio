# di

Inyeccion de dependencias: el punto donde se arma la app, es decir, donde se conectan las piezas de todas las capas.
Aqui no hay logica de negocio ni pantallas, solo el arranque de Koin con todos los modulos.

## Contenido
- `KoinInit.kt`: la funcion `initKoin()`, que inicia Koin una sola vez con `coreModule` y `dataModule`. Cada plataforma la llama al abrir la app (Android desde `CarnetizacionApp`, iOS desde Swift).

## Como funciona
Cada clase recibe lo que necesita por el constructor en vez de crearlo ella misma. Los modulos de Koin le dicen como construir cada pieza:
- `coreModule` (en `core`): cliente HTTP y almacenamiento del token.
- `dataModule` (en `data`): las `Api`, los repositorios y los casos de uso.
  Cada modulo vive en la capa que arma. Esta carpeta solo los junta.

## Por que esta separada de core
`core` no debe conocer a `data`. Esta carpeta es el unico lugar que ve todas las capas a la vez, asi las dependencias siguen yendo en una sola direccion.

## Por que se llama asi
"di" viene de Dependency Injection, el nombre estandar de esta parte en Clean Architecture.

## Equivalente en el backend
Es el contenedor de Spring Boot (`@Configuration`, `@Bean`, `@Autowired`), que arma las piezas del backend. En Spring casi todo se detecta solo con anotaciones. Aqui se declara de forma explicita en los modulos de Koin.