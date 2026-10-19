package org.example.carnetizaciontabio.domain.usecase

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.model.CarnetDigital
import org.example.carnetizaciontabio.domain.repository.CarnetRepository

/* Caso de uso: consultar el carnet digital del trabajador. */
class ObtenerMiCarnet(private val repository: CarnetRepository) {
    suspend operator fun invoke(): ApiResult<CarnetDigital> = repository.obtenerMiCarnet()
}