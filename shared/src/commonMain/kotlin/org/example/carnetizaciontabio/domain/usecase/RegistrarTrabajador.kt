package org.example.carnetizaciontabio.domain.usecase

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.model.NuevoTrabajador
import org.example.carnetizaciontabio.domain.model.Trabajador
import org.example.carnetizaciontabio.domain.repository.TrabajadorRepository

// Caso de uso: registrar un trabajador nuevo. Valida los campos obligatorios antes de llamar al servidor.
class RegistrarTrabajador(private val repository: TrabajadorRepository) {
    suspend operator fun invoke(nuevo: NuevoTrabajador): ApiResult<Trabajador> {
        val obligatorios = listOf(nuevo.cedula, nuevo.nombres, nuevo.apellidos, nuevo.cargo, nuevo.pin)
        if (obligatorios.any { it.isBlank() }) {
            return ApiResult.Error("Completa todos los campos obligatorios.")
        }
        // Se limpian espacios de los textos de identidad; el PIN se deja intacto (es texto libre).
        return repository.registrar(
            nuevo.copy(
                cedula = nuevo.cedula.trim(),
                nombres = nuevo.nombres.trim(),
                apellidos = nuevo.apellidos.trim(),
                cargo = nuevo.cargo.trim()
            )
        )
    }
}