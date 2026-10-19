package org.example.carnetizaciontabio.data

import org.example.carnetizaciontabio.data.remote.api.CarnetDigitalApi
import org.example.carnetizaciontabio.data.remote.api.MarcacionApi
import org.example.carnetizaciontabio.data.remote.api.TrabajadorApi
import org.example.carnetizaciontabio.data.repository.CarnetRepositoryImpl
import org.example.carnetizaciontabio.data.repository.MarcacionRepositoryImpl
import org.example.carnetizaciontabio.domain.repository.CarnetRepository
import org.example.carnetizaciontabio.domain.repository.MarcacionRepository
import org.example.carnetizaciontabio.domain.repository.TrabajadorRepository
import org.example.carnetizaciontabio.domain.repository.TrabajadorRepositoryImpl
import org.example.carnetizaciontabio.domain.usecase.LoginTrabajador
import org.example.carnetizaciontabio.domain.usecase.ObtenerHistorial
import org.example.carnetizaciontabio.domain.usecase.ObtenerMiCarnet
import org.example.carnetizaciontabio.domain.usecase.RegistrarMarcacion
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

    single { CarnetDigitalApi(get()) }
    single<CarnetRepository> { CarnetRepositoryImpl(get()) }
    factory { ObtenerMiCarnet(get()) }

    single { MarcacionApi(get()) }
    single<MarcacionRepository> { MarcacionRepositoryImpl(get()) }
    factory { RegistrarMarcacion(get()) }
    factory { ObtenerHistorial(get()) }
}