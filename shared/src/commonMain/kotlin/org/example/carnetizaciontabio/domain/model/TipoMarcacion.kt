package org.example.carnetizaciontabio.domain.model

// Las 4 marcaciones que se hacen en el dia, en este orden (igual que en el backend).
// El backend decide solo cual toca; la app no la escoge.
enum class TipoMarcacion {
    ENTRADA,
    SALIDA_ALMUERZO,
    ENTRADA_ALMUERZO,
    SALIDA
}
