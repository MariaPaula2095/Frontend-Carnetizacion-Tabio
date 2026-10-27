package org.example.carnetizaciontabio.domain.usecase

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.model.Area
import org.example.carnetizaciontabio.domain.repository.AreaRepository

/* Caso de uso: obtener las areas de la empresa para el formulario de registro. */
class ObtenerAreas(private val repository: AreaRepository) {
    suspend operator fun invoke(): ApiResult<List<Area>> = repository.obtenerAreas()
}
