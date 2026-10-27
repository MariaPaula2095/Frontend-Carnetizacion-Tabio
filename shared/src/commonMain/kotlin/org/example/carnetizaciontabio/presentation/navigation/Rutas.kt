package org.example.carnetizaciontabio.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

// Las pantallas de la app. Cada una es un "destino" al que se puede navegar.
@Serializable
sealed interface Ruta : NavKey {
    @Serializable data object Login : Ruta
    @Serializable data object Home : Ruta
    @Serializable data object Carnet : Ruta
    @Serializable data object Marcar : Ruta
    @Serializable data object Historial : Ruta
    @Serializable data object Consentimiento : Ruta
}

