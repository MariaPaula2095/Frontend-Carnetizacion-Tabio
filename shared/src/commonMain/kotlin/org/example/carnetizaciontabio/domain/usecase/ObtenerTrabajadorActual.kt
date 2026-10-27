package org.example.carnetizaciontabio.domain.usecase

import org.example.carnetizaciontabio.domain.model.Trabajador
import org.example.carnetizaciontabio.domain.repository.TrabajadorRepository

/* Caso de uso: consultar al trabajador que inicio sesion (devuelve null si no hay sesion). */
class ObtenerTrabajadorActual(private val repository: TrabajadorRepository) {
    operator fun invoke(): Trabajador? = repository.obtenerTrabajadorActual()
}
