# carnet

Pantalla "Mi Carnet": muestra el carnet digital del trabajador (frente y reverso) con los botones "Descargar Carnet" y "Ver Codigo QR".

- Usa: `ObtenerMiCarnetUseCase`.
- Backend: `GET /api/carnets/mi-carnet`.
- El codigo QR que se muestra es el valor `codigoQr` del carnet; es el mismo que se escanea en la pantalla de marcacion.
- "Descargar Carnet" (guardar como imagen) depende de la plataforma: se declara con `expect` y se implementa con `actual` en Android e iOS.
