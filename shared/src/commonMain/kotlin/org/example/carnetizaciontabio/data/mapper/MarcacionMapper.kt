package org.example.carnetizaciontabio.data.mapper

import org.example.carnetizaciontabio.data.remote.dto.MarcacionDto
import org.example.carnetizaciontabio.domain.model.Marcacion
import org.example.carnetizaciontabio.domain.model.MetodoVerificacion

/* Convierte el DTO del backend en el model Marcacion. */
fun MarcacionDto.toDomain(): Marcacion = Marcacion(
    id = idMarcacion ?: 0L,
    trabajadorId = trabajadorId.orEmpty(),
    tipo = tipo,
    fechaHora = fechaHora.orEmpty(),
    // CARNET_QR es el único método que existe en el backend, así que es un valor por defecto seguro.
    metodoVerificacion = metodoVerificacion ?: MetodoVerificacion.CARNET_QR,
    origen = origen?.takeIf { it.isNotBlank() }
)