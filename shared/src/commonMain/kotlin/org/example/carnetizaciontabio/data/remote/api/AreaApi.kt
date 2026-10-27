package org.example.carnetizaciontabio.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.carnetizaciontabio.data.remote.dto.AreaDto

// Llama a los endpoints de AreaController que usa la app movil.
class AreaApi(private val client: HttpClient) {

    // GET /api/areas/listar (publico): las areas de la empresa, para elegir la propia al registrarse.
    suspend fun listar(): List<AreaDto> =
        client.get("/api/areas/listar").body()
}
