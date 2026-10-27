package org.example.carnetizaciontabio.domain.repository

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.model.Area

/* Contrato de acceso a las areas de la empresa. */
interface AreaRepository {
    /* Lista de areas, para que el trabajador elija la suya al registrarse (no requiere sesion). */
    suspend fun obtenerAreas(): ApiResult<List<Area>>
}
