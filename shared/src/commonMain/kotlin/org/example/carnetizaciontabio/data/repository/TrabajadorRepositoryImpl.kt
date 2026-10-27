package org.example.carnetizaciontabio.data.repository

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.core.TokenStorage
import org.example.carnetizaciontabio.core.map
import org.example.carnetizaciontabio.core.safeApiCall
import org.example.carnetizaciontabio.data.mapper.toDomain
import org.example.carnetizaciontabio.data.mapper.toDto
import org.example.carnetizaciontabio.data.remote.api.TrabajadorApi
import org.example.carnetizaciontabio.data.remote.dto.LoginRequestDto
import org.example.carnetizaciontabio.domain.model.NuevoTrabajador
import org.example.carnetizaciontabio.domain.model.Trabajador
import org.example.carnetizaciontabio.domain.repository.TrabajadorRepository

/*
 Implementación real del repositorio: llama al backend con TrabajadorApi,
 guarda el token de sesión y convierte los DTO en models.
*/
class TrabajadorRepositoryImpl (
    private val api: TrabajadorApi,
    private val tokenStorage: TokenStorage
) : TrabajadorRepository{
    override suspend fun login(cedula: String, pin: String): ApiResult<Trabajador>{

        val result = safeApiCall { api.login(LoginRequestDto(cedula, pin))}

        return when (result){
            is ApiResult.Success -> {
                tokenStorage.saveToken(result.data.token)
                ApiResult.Success(result.data.trabajador.toDomain())
            }
            is ApiResult.Error -> {
                if (result.code == 401 || result.code == 403){
                    ApiResult.Error("Datos incorrectos.", result.code)
                }else {
                    result
                }
            }
        }
    }

    override suspend fun registrar(nuevo: NuevoTrabajador): ApiResult<Trabajador> =
        safeApiCall { api.registrar(nuevo.toDto()) }.map { it.toDomain() }
}