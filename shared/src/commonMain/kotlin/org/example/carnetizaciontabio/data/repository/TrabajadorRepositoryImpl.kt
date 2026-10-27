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
) : TrabajadorRepository {

    // Trabajador que inició sesión. Vive solo en memoria: se pierde al cerrar la app,
    // igual que el token (ver InMemoryTokenStorage).
    private var trabajadorActual: Trabajador? = null

    override suspend fun login(cedula: String, pin: String): ApiResult<Trabajador>{

        val result = safeApiCall { api.login(LoginRequestDto(cedula, pin))}

        return when (result){
            is ApiResult.Success -> {
                tokenStorage.saveToken(result.data.token)
                val trabajador = result.data.trabajador.toDomain()
                trabajadorActual = trabajador
                ApiResult.Success(trabajador)
            }
            // El mensaje ya viene listo desde safeApiCall: el del backend si lo mando
            // (ej. "Cedula o PIN incorrectos", "Tu registro aun no ha sido autorizado..."),
            // o uno generico si no.
            is ApiResult.Error -> result
        }
    }

    override suspend fun registrar(nuevo: NuevoTrabajador): ApiResult<Trabajador> =
        safeApiCall { api.registrar(nuevo.toDto()) }.map { it.toDomain() }

    override fun obtenerTrabajadorActual(): Trabajador? = trabajadorActual

    override fun cerrarSesion() {
        tokenStorage.clearToken()
        trabajadorActual = null
    }
}
