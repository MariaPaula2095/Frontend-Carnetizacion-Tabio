package org.example.carnetizaciontabio.domain.repository

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.model.Consentimiento

// Contrato de acceso al consentimiento de datos del trabajador.
interface ConsentimientoRepository {
    // Registra que el trabajador aceptó la política en la versión indicada.
    suspend fun aceptar(versionPolitica: String): ApiResult<Consentimiento>

    // Consulta el consentimiento del trabajador con sesión iniciada.
    suspend fun obtenerMiConsentimiento(): ApiResult<Consentimiento>
}
