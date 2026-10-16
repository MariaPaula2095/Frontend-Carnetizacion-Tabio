package org.example.carnetizaciontabio.core

import io.ktor.client.plugins.ResponseException
import kotlin.coroutines.cancellation.CancellationException

// Toda llamada al backend termina en uno de estos dos resultados:
// salio bien (con los datos) o fallo (con un mensaje que se le puede mostrar al usuario).
// Asi las pantallas no tienen que manejar errores tecnicos.
sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>
}

// Ejecuta una llamada al backend y convierte cualquier problema
// (sin internet, sesion vencida, error del servidor) en un ApiResult.Error.
suspend fun <T> safeApiCall(block: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(block())
    } catch (e: CancellationException) {
        // Si la pantalla se cierra y la llamada se cancela, no es un error: se deja seguir.
        throw e
    } catch (e: ResponseException) {
        val code = e.response.status.value
        val message = when (code) {
            401, 403 -> "No tienes permiso o tu sesion vencio. Inicia sesion de nuevo."
            404 -> "No se encontro lo que buscas."
            in 500..599 -> "El servidor tuvo un problema. Intenta de nuevo."
            else -> "No se pudo completar la solicitud."
        }
        ApiResult.Error(message, code)
    } catch (e: Exception) {
        ApiResult.Error("No hay conexion con el servidor. Revisa tu internet.")
    }
}