package org.example.carnetizaciontabio.domain.repository

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.model.CarnetDigital

/* Contrato de acceso al carnet digital. */
interface CarnetRepository {
    /* Carnet del trabajador con sesión iniciada (el backend lo identifica por el token). */
    suspend fun obtenerMiCarnet(): ApiResult<CarnetDigital>
}