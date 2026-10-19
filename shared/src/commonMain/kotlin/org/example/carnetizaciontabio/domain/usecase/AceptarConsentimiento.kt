package org.example.carnetizaciontabio.domain.usecase

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.model.Consentimiento
import org.example.carnetizaciontabio.domain.repository.ConsentimientoRepository

// Caso de uso: el trabajador acepta la política de tratamiento de datos.
class AceptarConsentimiento(private val repository: ConsentimientoRepository) {
    // "1.0" coincide con la versión que el backend usa por defecto.
    suspend operator fun invoke(versionPolitica: String = "1.0"): ApiResult<Consentimiento> =
        repository.aceptar(versionPolitica)
}