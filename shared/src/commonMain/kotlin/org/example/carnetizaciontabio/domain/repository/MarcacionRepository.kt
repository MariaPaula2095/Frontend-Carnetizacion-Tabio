package org.example.carnetizaciontabio.domain.repository

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.model.Marcacion

/* Contrato de acceso a las marcaciones del trabajador. */
interface MarcacionRepository {
    /* Envía el QR escaneado. El backend decide si es entrada, salida o almuerzo. */
    suspend fun registrar(codigoQr: String): ApiResult<Marcacion>

    /* Marcaciones del trabajador, de la más reciente a la más antigua. */
    suspend fun obtenerHistorial(): ApiResult<List<Marcacion>>
}