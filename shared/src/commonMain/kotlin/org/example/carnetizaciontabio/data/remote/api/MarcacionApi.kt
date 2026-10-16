package org.example.carnetizaciontabio.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.example.carnetizaciontabio.data.remote.dto.EscanearRequestDto
import org.example.carnetizaciontabio.data.remote.dto.MarcacionDto

// Llama a los endpoints de MarcacionController que usa la app movil.
class MarcacionApi(private val client: HttpClient) {

    // POST /api/marcaciones/escanear: se envia el codigo QR escaneado y el backend
    // decide si es entrada, salida almuerzo, entrada almuerzo o salida.
    suspend fun escanear(request: EscanearRequestDto): MarcacionDto =
        client.post("/api/marcaciones/escanear") { setBody(request) }.body()

    // GET /api/marcaciones/mi-historial: marcaciones del trabajador, de la mas reciente a la mas antigua.
    suspend fun miHistorial(): List<MarcacionDto> =
        client.get("/api/marcaciones/mi-historial").body()
}
