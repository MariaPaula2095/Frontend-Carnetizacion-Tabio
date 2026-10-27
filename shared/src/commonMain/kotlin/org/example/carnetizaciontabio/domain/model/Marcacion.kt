package org.example.carnetizaciontabio.domain.model

/* Un registro de entrada, salida o almuerzo del trabajador. */
data class Marcacion(
    val id: Long,
    val trabajadorId: String,
    /* Se deja nullable a propósito: si no llega, inventar un tipo sería mostrar un dato falso. */
    val tipo: TipoMarcacion?,
    /* Fecha y hora en texto ISO, ej. "2026-10-16T07:45:00". */
    val fechaHora: String,
    val metodoVerificacion: MetodoVerificacion,
    val origen: String?
)