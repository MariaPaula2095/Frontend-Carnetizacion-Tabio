package org.example.carnetizaciontabio.domain.usecase

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.model.Marcacion
import org.example.carnetizaciontabio.domain.repository.MarcacionRepository

// Caso de uso: registrar una marcación a partir del QR escaneado.
class RegistrarMarcacion(private val repository: MarcacionRepository) {
    suspend operator fun invoke(codigoQr: String): ApiResult<Marcacion> {
        if (codigoQr.isBlank()) {
            return ApiResult.Error("No se pudo leer el código QR. Intenta de nuevo.")
        }
        return repository.registrar(codigoQr.trim())
    }
}