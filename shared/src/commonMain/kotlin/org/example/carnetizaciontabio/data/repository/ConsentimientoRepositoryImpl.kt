package org.example.carnetizaciontabio.data.repository

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.core.map
import org.example.carnetizaciontabio.core.safeApiCall
import org.example.carnetizaciontabio.data.mapper.toDomain
import org.example.carnetizaciontabio.data.remote.api.ConsentimientoApi
import org.example.carnetizaciontabio.data.remote.dto.AceptarConsentimientoRequestDto
import org.example.carnetizaciontabio.domain.model.Consentimiento
import org.example.carnetizaciontabio.domain.repository.ConsentimientoRepository

// Acepta y consulta el consentimiento usando ConsentimientoApi.
class ConsentimientoRepositoryImpl(
    private val api: ConsentimientoApi
) : ConsentimientoRepository {

    override suspend fun aceptar(versionPolitica: String): ApiResult<Consentimiento> =
        safeApiCall { api.aceptar(AceptarConsentimientoRequestDto(versionPolitica)) }
            .map { it.toDomain() }

    override suspend fun obtenerMiConsentimiento(): ApiResult<Consentimiento> =
        safeApiCall { api.miConsentimiento() }.map { it.toDomain() }
}