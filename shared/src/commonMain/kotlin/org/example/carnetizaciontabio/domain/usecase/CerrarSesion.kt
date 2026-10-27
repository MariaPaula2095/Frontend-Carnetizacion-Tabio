package org.example.carnetizaciontabio.domain.usecase

import org.example.carnetizaciontabio.domain.repository.TrabajadorRepository

/* Caso de uso: cerrar la sesion del trabajador (borra el token y sus datos en memoria). */
class CerrarSesion(private val repository: TrabajadorRepository) {
    operator fun invoke() = repository.cerrarSesion()
}
