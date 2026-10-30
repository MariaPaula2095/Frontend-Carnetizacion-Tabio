package org.example.carnetizaciontabio.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.example.carnetizaciontabio.domain.model.TipoMarcacion
import org.example.carnetizaciontabio.domain.usecase.CerrarSesion
import org.example.carnetizaciontabio.presentation.carnet.CarnetScreen
import org.example.carnetizaciontabio.presentation.carnet.DatosCarnet
import org.example.carnetizaciontabio.presentation.consentimiento.ConsentimientoScreen
import org.example.carnetizaciontabio.presentation.historial.HistorialScreen
import org.example.carnetizaciontabio.presentation.historial.ItemHistorial
import org.example.carnetizaciontabio.presentation.home.HomeScreen
import org.example.carnetizaciontabio.presentation.login.LoginScreen
import org.example.carnetizaciontabio.presentation.marcacion.MarcarScreen
import org.koin.compose.koinInject

private val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) { subclassesOfSealed<Ruta>() }
    }
}

// EJEMPLO: datos de prueba para la navegacion. Se borran cuando cada pantalla tenga su ViewModel.
private val CarnetEjemplo = DatosCarnet(
    nombreCompleto = "Juan Sebastián Silva Cárdenas",
    cargo = "Contratista",
    cedula = "1076622641",
    fotoUrl = null,
    area = "Oficina de ejemplo"
)
/*
private val HistorialEjemplo = listOf(
    ItemHistorial(TipoMarcacion.ENTRADA, "Hoy, 7:45 AM"),
    ItemHistorial(TipoMarcacion.SALIDA, "Ayer, 6:00 PM")
)
*/

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(config, Ruta.Login)
    val cerrarSesion: CerrarSesion = koinInject() // caso de uso que borra la sesion de la memoria

    NavDisplay(
        backStack = backStack,
        // Boton atras del celular: quita la pantalla de arriba.
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Ruta.Login> {
                LoginScreen(onIngresar = {
                    backStack.clear(); backStack.add(Ruta.Home)   // asi "atras" no vuelve al Login
                })
            }
            entry<Ruta.Home> {
                HomeScreen(
                    onMiCarnet = { backStack.add(Ruta.Carnet) },
                    onHistorial = { backStack.add(Ruta.Historial) },
                    onMarcar = { backStack.add(Ruta.Marcar) },
                    onConsentimiento = { backStack.add(Ruta.Consentimiento) },
                    onCerrarSesion = {
                        cerrarSesion()   // borra el token y al trabajador de la memoria
                        backStack.clear(); backStack.add(Ruta.Login)
                    }
                )
            }
            entry<Ruta.Carnet> {
                CarnetScreen(datos = CarnetEjemplo, onBack = { backStack.removeLastOrNull() })
            }
            entry<Ruta.Marcar> {
                MarcarScreen(
                    codigoQr = "EJEMPLO-123456",
                    ultimaMarcacion = "Entrada - 07:45 AM",
                    onBack = { backStack.removeLastOrNull() }
                )
            }
            entry<Ruta.Historial> {
                HistorialScreen(onBack = { backStack.removeLastOrNull() })
            }
            entry<Ruta.Consentimiento> {
                ConsentimientoScreen(
                    onAceptar = { backStack.removeLastOrNull() },
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}

