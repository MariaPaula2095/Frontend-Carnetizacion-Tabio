package org.example.carnetizaciontabio.core

import org.koin.dsl.module

// Koin es quien "arma" la app: aqui se declara que piezas existen y como se crean,
// y luego cada clase recibe las que necesita sin crearlas ella misma.
val coreModule = module {
single<TokenStorage> { InMemoryTokenStorage() }
    single { createHttpClient(get(), defaultBaseUrl()) }
}