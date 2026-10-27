package org.example.carnetizaciontabio.data.repository

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.core.map
import org.example.carnetizaciontabio.core.safeApiCall
import org.example.carnetizaciontabio.data.mapper.toDomain
import org.example.carnetizaciontabio.data.remote.api.MarcacionApi
import org.example.carnetizaciontabio.data.remote.dto.EscanearRequestDto
import org.example.carnetizaciontabio.domain.model.Marcacion
import org.example.carnetizaciontabio.domain.repository.MarcacionRepository

// Registra y consulta marcaciones usando MarcacionApi.
class MarcacionRepositoryImpl(
    private val api: MarcacionApi
) : MarcacionRepository {

    override suspend fun registrar(codigoQr: String): ApiResult<Marcacion> =
        safeApiCall { api.escanear(EscanearRequestDto(codigoQr)) }.map { it.toDomain() }

    override suspend fun obtenerHistorial(): ApiResult<List<Marcacion>> =
        safeApiCall { api.miHistorial() }.map { lista -> lista.map { it.toDomain() } }
}