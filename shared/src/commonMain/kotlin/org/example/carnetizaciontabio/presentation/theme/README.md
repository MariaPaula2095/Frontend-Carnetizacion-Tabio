# theme

Colores, tipografia y tema general de la app. Es lo que hace que todas las pantallas se vean como parte de la misma familia: la identidad visual de EMSERTABIO.

## Archivos
- `Color.kt`: la paleta. Los azules, el verde de accion y los grises salen de la pagina oficial de EMSERTABIO; los colores de estado del trabajador (autorizado, pendiente, inactivo, rechazado) salen de los mockups de Figma.
- `Theme.kt`: `CarnetizacionTheme`, que asigna cada color a un rol de Material (`primary`, `background`, `outline`...). Se usa una sola vez, en `App()`.

## Por que se llama asi
"theme" (tema) es el nombre estandar en Compose para el estilo visual compartido.

## Ojo
- Las pantallas no escriben colores a mano: usan `MaterialTheme.colorScheme.*` o las constantes de `Color.kt`. Asi un cambio de paleta se hace en un solo lugar.
- Material no tiene un rol propio para "exito" ni "advertencia", por eso esos estados son constantes sueltas en `Color.kt` y no parte del esquema.