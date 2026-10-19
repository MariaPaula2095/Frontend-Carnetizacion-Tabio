package org.example.carnetizaciontabio.domain.usecase

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.model.Consentimiento
import org.example.carnetizaciontabio.domain.repository.ConsentimientoRepository

// Caso de uso: consultar si el trabajador ya aceptó la política. */
class ObtenerMiConsentimiento(private val repository: ConsentimientoRepository) {
    suspend operator fun invoke(): ApiResult<Consentimiento> = repository.obtenerMiConsentimiento()
}