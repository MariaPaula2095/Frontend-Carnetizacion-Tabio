package org.example.carnetizaciontabio.data.remote.dto

import kotlinx.serialization.Serializable
import org.example.carnetizaciontabio.domain.model.EstadoTrabajador
import org.example.carnetizaciontabio.domain.model.TipoVinculacion

// Mismos campos que TrabajadorDto del backend, para que el JSON se convierta solo.
// Todos son opcionales porque la misma clase sirve para enviar datos (registro)
// y para recibirlos (respuestas). Las fechas llegan como texto ISO (ej. "2026-10-16").
@Serializable
data class TrabajadorDto(
    val idTrabajador: String? = null,
    val cedula: String? = null,
    val nombres: String? = null,
    val apellidos: String? = null,
    val fotoUrl: String? = null,
    val cargo: String? = null,
    val areaId: Long? = null,
    val tipoVinculacion: TipoVinculacion? = null,
    val estado: EstadoTrabajador? = null,
    val fechaIngreso: String? = null,
    val autorizadoPor: Long? = null,
    val fechaAutorizacion: String? = null,
    // El PIN solo se envia (registro y login). El backend nunca lo devuelve.
    val pin: String? = null
)

// Lo que se envia a POST /api/trabajadores/login
@Serializable
data class LoginRequestDto(
    val cedula: String,
    val pin: String
)

// Lo que responde POST /api/trabajadores/login: el token de sesion y los datos del trabajador.
@Serializable
data class LoginResponseDto(
    val token: String,
    val trabajador: TrabajadorDto
)
