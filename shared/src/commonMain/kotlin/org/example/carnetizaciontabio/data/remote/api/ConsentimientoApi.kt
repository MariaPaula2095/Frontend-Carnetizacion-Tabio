package org.example.carnetizaciontabio.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.example.carnetizaciontabio.data.remote.dto.AceptarConsentimientoRequestDto
import org.example.carnetizaciontabio.data.remote.dto.ConsentimientoDto

// Llama a los endpoints de ConsentimientoController que usa la app movil.
class ConsentimientoApi(private val client: HttpClient) {

    // POST /api/consentimientos/aceptar: el trabajador acepta la politica de datos.
    suspend fun aceptar(request: AceptarConsentimientoRequestDto): ConsentimientoDto =
        client.post("/api/consentimientos/aceptar") { setBody(request) }.body()

    // GET /api/consentimientos/mi-consentimiento: consulta si ya acepto.
    suspend fun miConsentimiento(): ConsentimientoDto =
        client.get("/api/consentimientos/mi-consentimiento").body()
}
