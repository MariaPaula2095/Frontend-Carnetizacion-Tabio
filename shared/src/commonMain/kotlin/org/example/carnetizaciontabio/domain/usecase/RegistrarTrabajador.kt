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
        // El backend usa los ultimos 4 digitos de la cedula como id del trabajador,
        // por eso debe tener solo numeros y minimo 4 digitos.
        val cedula = nuevo.cedula.trim()
        if (cedula.length < 4 || cedula.any { !it.isDigit() }) {
            return ApiResult.Error("La cédula debe tener solo números y mínimo 4 dígitos.")
        }
        // Se limpian espacios de los textos de identidad; el PIN se deja intacto (es texto libre).
        return repository.registrar(
            nuevo.copy(
                cedula = cedula,
                nombres = nuevo.nombres.trim(),
                apellidos = nuevo.apellidos.trim(),
                cargo = nuevo.cargo.trim()
            )
        )
    }
}