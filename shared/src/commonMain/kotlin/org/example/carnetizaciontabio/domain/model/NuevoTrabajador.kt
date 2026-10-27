package org.example.carnetizaciontabio.domain.model

// Datos que llena el trabajador para registrarse. Queda PENDIENTE hasta que un administrador lo autorice.
data class NuevoTrabajador(
    val cedula: String,
    val nombres: String,
    val apellidos: String,
    val cargo: String,
    val areaId: Long,
    val tipoVinculacion: TipoVinculacion,
    // Solo se envía al backend, que nunca lo devuelve; por eso no existe en Trabajador.
    val pin: String,
    val fotoUrl: String? = null
)