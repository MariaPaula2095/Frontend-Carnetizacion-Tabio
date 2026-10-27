package org.example.carnetizaciontabio.core

import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
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
        // Si el backend mando su propio mensaje (ej. "Cedula o PIN incorrectos"), se usa ese.
        // Si no mando ninguno, se usa un texto generico segun el codigo.
        val message = leerMensajeDelServidor(e) ?: when (code) {
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

// Forma de los errores que manda el backend, ej.: { "status": 401, "message": "Cedula o PIN incorrectos", ... }
// Solo interesa el campo "message".
@Serializable
private data class ErrorDelServidor(val message: String? = null)

private val jsonDeErrores = Json { ignoreUnknownKeys = true }

// Intenta leer el mensaje que el backend dejo en el cuerpo de la respuesta de error.
// Devuelve null si el cuerpo esta vacio o no tiene ese formato (por ejemplo, una sesion vencida).
private suspend fun leerMensajeDelServidor(e: ResponseException): String? =
    try {
        jsonDeErrores
            .decodeFromString<ErrorDelServidor>(e.response.bodyAsText())
            .message
            ?.takeIf { it.isNotBlank() }
    } catch (ex: CancellationException) {
        throw ex
    } catch (ex: Exception) {
        null
    }
