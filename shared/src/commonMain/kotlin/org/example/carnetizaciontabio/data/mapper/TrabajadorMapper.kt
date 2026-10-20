package org.example.carnetizaciontabio.data.mapper

import org.example.carnetizaciontabio.data.remote.dto.TrabajadorDto
import org.example.carnetizaciontabio.domain.model.EstadoTrabajador
import org.example.carnetizaciontabio.domain.model.NuevoTrabajador
import org.example.carnetizaciontabio.domain.model.Trabajador

/*
 Convierte el DTO (lo que manda el backend) en el model que usa la app.
 Los nulos se reemplazan por valores seguros para que las pantallas no tengan que revisarlos.
 */
fun TrabajadorDto.toDomain(): Trabajador = Trabajador(
    id = idTrabajador.orEmpty(),
    cedula = cedula.orEmpty(),
    nombres = nombres.orEmpty(),
    apellidos = apellidos.orEmpty(),
    fotoUrl = fotoUrl?.takeIf { it.isNotBlank() },
    cargo = cargo.orEmpty(),
    areaId = areaId,
    tipoVinculacion = tipoVinculacion,
    estado = estado ?: EstadoTrabajador.PENDIENTE,
    fechaIngreso = fechaIngreso
)

//Convierte los datos del formulario en el DTO que se envía al backend (dirección inversa a toDomain).
fun NuevoTrabajador.toDto(): TrabajadorDto = TrabajadorDto(
    cedula = cedula,
    nombres = nombres,
    apellidos = apellidos,
    fotoUrl = fotoUrl,
    cargo = cargo,
    areaId = areaId,
    tipoVinculacion = tipoVinculacion,
    pin = pin
)