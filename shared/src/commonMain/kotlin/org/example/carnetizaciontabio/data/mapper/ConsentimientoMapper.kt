package org.example.carnetizaciontabio.data.mapper

import org.example.carnetizaciontabio.data.remote.dto.ConsentimientoDto
import org.example.carnetizaciontabio.domain.model.Consentimiento

// Convierte el DTO del backend en el model Consentimiento.
fun ConsentimientoDto.toDomain(): Consentimiento = Consentimiento(
    id = idConsentimiento ?: 0L,
    trabajadorId = trabajadorId.orEmpty(),
    fechaAceptacion = fechaAceptacion,
    versionPolitica = versionPolitica.orEmpty(),
    // Si no llega, se asume que NO aceptó: nunca dar por aceptado algo sin confirmarlo.
    aceptado = aceptado ?: false
)