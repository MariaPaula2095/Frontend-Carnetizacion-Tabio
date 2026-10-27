package org.example.carnetizaciontabio.data.mapper

import org.example.carnetizaciontabio.data.remote.dto.AreaDto
import org.example.carnetizaciontabio.domain.model.Area

/* Convierte el DTO del backend en el model Area. */
fun AreaDto.toDomain(): Area = Area(
    id = idArea ?: 0L,
    nombre = nombre.orEmpty()
)
