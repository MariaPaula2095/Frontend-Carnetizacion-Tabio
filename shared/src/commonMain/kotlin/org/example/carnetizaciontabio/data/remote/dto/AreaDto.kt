package org.example.carnetizaciontabio.data.remote.dto

import kotlinx.serialization.Serializable

// Mismos campos que AreaDto del backend (una dependencia de la empresa: Aseo, Acueducto, etc.).
@Serializable
data class AreaDto(
    val idArea: Long? = null,
    val nombre: String? = null
)
