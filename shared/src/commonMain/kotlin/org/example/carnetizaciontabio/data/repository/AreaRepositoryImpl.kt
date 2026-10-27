package org.example.carnetizaciontabio.data.repository

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.core.map
import org.example.carnetizaciontabio.core.safeApiCall
import org.example.carnetizaciontabio.data.mapper.toDomain
import org.example.carnetizaciontabio.data.remote.api.AreaApi
import org.example.carnetizaciontabio.domain.model.Area
import org.example.carnetizaciontabio.domain.repository.AreaRepository

// Obtiene las areas desde el backend usando AreaApi.
class AreaRepositoryImpl(
    private val api: AreaApi
) : AreaRepository {

    override suspend fun obtenerAreas(): ApiResult<List<Area>> =
        safeApiCall { api.listar() }.map { lista -> lista.map { it.toDomain() } }
}
