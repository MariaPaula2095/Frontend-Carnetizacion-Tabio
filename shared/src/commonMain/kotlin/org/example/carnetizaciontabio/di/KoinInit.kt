package org.example.carnetizaciontabio.di

import org.example.carnetizaciontabio.core.coreModule
import org.example.carnetizaciontabio.data.dataModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

// Arranca Koin una sola vez con todos los modulos. Cada plataforma lo llama al iniciar la app.
fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(coreModule, dataModule)
    }
}

