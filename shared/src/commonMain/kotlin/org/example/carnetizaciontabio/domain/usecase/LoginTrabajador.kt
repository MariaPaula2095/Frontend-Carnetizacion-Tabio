package org.example.carnetizaciontabio.domain.usecase

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.model.Trabajador
import org.example.carnetizaciontabio.domain.repository.TrabajadorRepository

class LoginTrabajador(private val repository: TrabajadorRepository) {

    suspend operator fun invoke(cedula: String, pin: String): ApiResult<Trabajador> {
        if (cedula.isBlank() || pin.isBlank()) {
            return ApiResult.Error("Ingresa tu cédula y tu PIN.")
        }
        return repository.login(cedula.trim(), pin)
    }

}