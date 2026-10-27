package org.example.carnetizaciontabio.data.repository

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.core.map
import org.example.carnetizaciontabio.core.safeApiCall
import org.example.carnetizaciontabio.data.mapper.toDomain
import org.example.carnetizaciontabio.data.remote.api.CarnetDigitalApi
import org.example.carnetizaciontabio.domain.model.CarnetDigital
import org.example.carnetizaciontabio.domain.repository.CarnetRepository

/* Obtiene el carnet desde el backend y lo convierte en model. */
class CarnetRepositoryImpl(
    private val api: CarnetDigitalApi
) : CarnetRepository {

    override suspend fun obtenerMiCarnet(): ApiResult<CarnetDigital> =
        safeApiCall { api.miCarnet() }.map { it.toDomain() }
}