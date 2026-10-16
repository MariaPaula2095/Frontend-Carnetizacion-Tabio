package org.example.carnetizaciontabio.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.example.carnetizaciontabio.data.remote.dto.LoginRequestDto
import org.example.carnetizaciontabio.data.remote.dto.LoginResponseDto
import org.example.carnetizaciontabio.data.remote.dto.TrabajadorDto

// Llama a los endpoints de TrabajadorController del backend que usa la app movil.
// Si el backend responde con error, estas funciones lanzan excepcion;
// el repositorio es quien la convierte en un ApiResult.
class TrabajadorApi(private val client: HttpClient) {

    // POST /api/trabajadores/registrar (publico). Queda PENDIENTE hasta que un administrador lo autorice.
    suspend fun registrar(trabajador: TrabajadorDto): TrabajadorDto =
        client.post("/api/trabajadores/registrar") { setBody(trabajador) }.body()

    // POST /api/trabajadores/login (publico). Devuelve el token y los datos del trabajador.
    suspend fun login(request: LoginRequestDto): LoginResponseDto =
        client.post("/api/trabajadores/login") { setBody(request) }.body()
}
