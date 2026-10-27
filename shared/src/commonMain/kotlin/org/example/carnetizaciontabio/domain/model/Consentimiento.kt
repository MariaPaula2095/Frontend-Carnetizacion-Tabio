package org.example.carnetizaciontabio.domain.model

// Aceptación de la política de tratamiento de datos por parte del trabajador.
data class Consentimiento(
    val id: Long,
    val trabajadorId: String,
    val fechaAceptacion: String?,
    val versionPolitica: String,
    val aceptado: Boolean
)