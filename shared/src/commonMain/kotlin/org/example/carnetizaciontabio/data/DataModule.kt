package org.example.carnetizaciontabio.data

import org.example.carnetizaciontabio.data.remote.api.TrabajadorApi
import org.example.carnetizaciontabio.domain.repository.TrabajadorRepository
import org.example.carnetizaciontabio.domain.repository.TrabajadorRepositoryImpl
import org.example.carnetizaciontabio.domain.usecase.LoginTrabajador
import org.koin.dsl.module

/* Le dice a Koin cómo construir los repositorios y casos de uso
 Cada get() le pide a Koin una pieza que ya sabe construir (la Api, el TokenStorage).
 */
val dataModule = module {
    // single = una sola instancia para toda la app.
    // Se declara como TrabajadorRepository (la interfaz) para que el resto
    // del código dependa del contrato y no de la implementación.
    single { TrabajadorApi( get()) }
    single<TrabajadorRepository> { TrabajadorRepositoryImpl(get(), get()) }
    factory { LoginTrabajador( get()) }
}