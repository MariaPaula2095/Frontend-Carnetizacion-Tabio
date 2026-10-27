package org.example.carnetizaciontabio.domain.repository

import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.model.NuevoTrabajador
import org.example.carnetizaciontabio.domain.model.Trabajador

/*
 Contrato de acceso a datos del trabajador.
 Dice qué necesita la app; el como (HTTP, token) lo resuelve TrabajadorRepositoryImpl.
*/
interface TrabajadorRepository {
    //Inicia sesión con cédula y PIN. Devuelve el trabajador o un error con mensaje legible.
    suspend fun login(cedula: String, pin: String): ApiResult<Trabajador>

    // Registra un trabajador nuevo (endpoint público). Queda PENDIENTE hasta que un administrador lo autorice.
    suspend fun registrar(nuevo: NuevoTrabajador): ApiResult<Trabajador>
}