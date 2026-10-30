package org.example.carnetizaciontabio.di

import org.example.carnetizaciontabio.presentation.historial.HistorialViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel


// ViewModels de la capa de presentacion.
// Van aparte de dataModule porque dependen de Compose y de lifecycle-viewmodel,
// y la capa de datos no debe conocer nada de eso.
val presentationModule = module {
    viewModel { HistorialViewModel(get()) }
}