package org.example.carnetizaciontabio.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.carnetizaciontabio.data.remote.dto.CarnetDigitalDto

// Llama a los endpoints de CarnetDigitalController que usa la app movil.
class CarnetDigitalApi(private val client: HttpClient) {

    // GET /api/carnets/mi-carnet: el carnet del trabajador que inicio sesion (se identifica por el token).
    suspend fun miCarnet(): CarnetDigitalDto =
        client.get("/api/carnets/mi-carnet").body()
}
