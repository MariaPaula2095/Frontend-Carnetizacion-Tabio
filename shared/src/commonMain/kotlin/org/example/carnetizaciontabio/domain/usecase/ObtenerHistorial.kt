package org.example.carnetizaciontabio.domain.usecase

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.model.Marcacion
import org.example.carnetizaciontabio.domain.repository.MarcacionRepository

// Caso de uso: consultar el historial de marcaciones del trabajador.
class ObtenerHistorial(private val repository: MarcacionRepository) {
    suspend operator fun invoke(): ApiResult<List<Marcacion>> = repository.obtenerHistorial()
}