package org.example.carnetizaciontabio.data.mapper

import org.example.carnetizaciontabio.data.remote.dto.CarnetDigitalDto
import org.example.carnetizaciontabio.domain.model.CarnetDigital
import org.example.carnetizaciontabio.domain.model.EstadoCarnet

/* Convierte el DTO del backend en el model CarnetDigital. */
fun CarnetDigitalDto.toDomain(): CarnetDigital = CarnetDigital(
    id = idCarnet ?: 0L,
    trabajadorId = trabajadorId.orEmpty(),
    codigoQr = codigoQr.orEmpty(),
    fechaExpedicion = fechaExpedicion,
    fechaVencimiento = fechaVencimiento,
    // Si el estado no llega se asume INACTIVO: es lo más seguro,
    // nunca mostrar un carnet como válido sin saberlo.
    estado = estado ?: EstadoCarnet.INACTIVO
)