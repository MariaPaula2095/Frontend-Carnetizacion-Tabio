# presentation

Todo lo que el usuario ve y toca: las pantallas (Compose Multiplatform) y la logica de cada pantalla.

## Estructura
Una carpeta por pantalla, tomada de los mockups. Dentro de cada una:
- `XxxScreen`: lo que se dibuja.
- `XxxViewModel`: el estado de la pantalla y lo que pasa cuando el usuario toca algo.

## Regla
Una pantalla nunca llama a la API directamente. La pantalla le avisa al ViewModel, el ViewModel ejecuta un caso de uso de `domain/usecase`, y el resultado vuelve como estado para dibujar.

## Por que se llama asi
"presentation" (presentacion) es el nombre estandar de la capa que muestra la informacion al usuario.

## Equivalente en el backend
No tiene. El backend no tiene interfaz; esta capa es exclusiva del front.
