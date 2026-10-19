package org.example.carnetizaciontabio.domain.model


/*
Trabajador tal como lo usan las pantallas y los ViewModels.
Es la versión "limpia" de TrabajadorDto: sin PIN, sin JSON y con menos nulos.
*/
data class Trabajador(
    val id: String,
    val cedula: String,
    val nombres: String,
    val apellidos: String,
    val fotoUrl: String?,
    val cargo: String,
    val areaId: Long?,
    val tipoVinculacion: TipoVinculacion?,
    val estado: EstadoTrabajador,
    val fechaIngreso: String?
) {
    // Nombre listo para mostrar en pantalla.
    val nombreCompleto: String get() = "$nombres $apellidos"
}