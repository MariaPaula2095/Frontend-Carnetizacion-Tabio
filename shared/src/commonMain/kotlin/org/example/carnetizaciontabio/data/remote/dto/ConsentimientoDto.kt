package org.example.carnetizaciontabio.data.remote.dto

import kotlinx.serialization.Serializable

// Mismos campos que ConsentimientoDto del backend.
@Serializable
data class ConsentimientoDto(
    val idConsentimiento: Long? = null,
    val trabajadorId: String? = null,
    val fechaAceptacion: String? = null,
    val versionPolitica: String? = null,
    val aceptado: Boolean? = null
)

// Lo que se envia a POST /api/consentimientos/aceptar.
// Si no se manda version, el backend usa "1.0".
@Serializable
data class AceptarConsentimientoRequestDto(
    val versionPolitica: String = "1.0"
)
