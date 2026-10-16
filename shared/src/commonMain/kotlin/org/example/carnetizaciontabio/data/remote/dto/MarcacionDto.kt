package org.example.carnetizaciontabio.data.remote.dto

import kotlinx.serialization.Serializable
import org.example.carnetizaciontabio.domain.model.MetodoVerificacion
import org.example.carnetizaciontabio.domain.model.TipoMarcacion

// Mismos campos que MarcacionDto del backend.
// "fechaHora" llega como texto ISO (ej. "2026-10-16T07:45:00").
@Serializable
data class MarcacionDto(
    val idMarcacion: Long? = null,
    val trabajadorId: String? = null,
    val tipo: TipoMarcacion? = null,
    val fechaHora: String? = null,
    val metodoVerificacion: MetodoVerificacion? = null,
    val origen: String? = null
)

// Lo que se envia a POST /api/marcaciones/escanear: solo el codigo del QR escaneado.
@Serializable
data class EscanearRequestDto(
    val codigoQr: String
)
