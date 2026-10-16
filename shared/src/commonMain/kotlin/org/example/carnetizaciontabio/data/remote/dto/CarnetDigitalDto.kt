package org.example.carnetizaciontabio.data.remote.dto

import kotlinx.serialization.Serializable
import org.example.carnetizaciontabio.domain.model.EstadoCarnet

// Mismos campos que CarnetDigitalDto del backend.
// "codigoQr" es el valor que va dentro del QR y que se escanea para marcar.
@Serializable
data class CarnetDigitalDto(
    val idCarnet: Long? = null,
    val trabajadorId: String? = null,
    val codigoQr: String? = null,
    val fechaExpedicion: String? = null,
    val fechaVencimiento: String? = null,
    val estado: EstadoCarnet? = null
)
