package org.example.carnetizaciontabio.core

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Crea el "navegador" interno de la app: el que hace las llamadas al backend.
// Se crea una sola vez y lo usan todos los clientes de data/remote/api.
fun createHttpClient(tokenStorage: TokenStorage, baseUrl: String): HttpClient = HttpClient {

    // Hace que las respuestas con error (401, 404, 500...) lancen excepcion,
    // para que safeApiCall las convierta en ApiResult.Error.
    expectSuccess = true

    // Convierte automaticamente entre JSON y las clases de dto.
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true // si el backend agrega un campo nuevo, la app no se rompe
            isLenient = true
        })
    }

    // Muestra en la consola de Android Studio cada llamada, util para depurar.
    install(Logging) { level = LogLevel.INFO }

    // Se aplica a todas las peticiones: direccion base, formato JSON,
    // y el token de sesion si ya se inicio sesion.
    defaultRequest {
        url(baseUrl)
        contentType(ContentType.Application.Json)
        tokenStorage.getToken()?.let { header(HttpHeaders.Authorization, "Bearer $it") }
    }
}