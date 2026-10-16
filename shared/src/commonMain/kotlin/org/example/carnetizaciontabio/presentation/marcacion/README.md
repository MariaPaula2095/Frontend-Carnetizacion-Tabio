# marcacion

Pantalla "Marcar Entrada/Salida": el trabajador escanea el codigo QR de su propio carnet con la camara del celular.

- Usa: `RegistrarMarcacionUseCase`.
- Backend: `POST /api/marcaciones/escanear` enviando solo el `codigoQr`. El backend decide solo si la marcacion es ENTRADA, SALIDA_ALMUERZO, ENTRADA_ALMUERZO o SALIDA segun cuantas lleva hoy; la app no elige el tipo.
- La camara y la lectura del QR son distintas en Android y en iOS: se declaran con `expect` y se implementan con `actual` en `androidMain` e `iosMain`.
- Debajo del escaner se muestra la ultima marcacion, como en el mockup.
