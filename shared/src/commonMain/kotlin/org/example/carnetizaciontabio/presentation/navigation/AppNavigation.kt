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
import org.example.carnetizaciontabio.presentation.carnet.CarnetScreen
import org.example.carnetizaciontabio.presentation.carnet.DatosCarnet
import org.example.carnetizaciontabio.presentation.consentimiento.ConsentimientoScreen
import org.example.carnetizaciontabio.presentation.historial.HistorialScreen
import org.example.carnetizaciontabio.presentation.historial.ItemHistorial
import org.example.carnetizaciontabio.presentation.home.HomeScreen
import org.example.carnetizaciontabio.presentation.login.LoginScreen
import org.example.carnetizaciontabio.presentation.marcacion.MarcarScreen

// Los demas imports (pantallas, DatosCarnet, ItemHistorial, TipoMarcacion, subclassesOfSealed): Alt+Enter

// Necesario para que la pila se pueda guardar en iOS; en Android no estorba. Es la receta de la documentacion.
private val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) { subclassesOfSealed<Ruta>() }
    }
}

// EJEMPLO: datos de prueba la navegacion. Se borran cuando cada pantalla tenga su ViewModel.
// Ajusta los campos de DatosCarnet a como quedo tu version final.
private val CarnetEjemplo = DatosCarnet(
    nombreCompleto = "Juan Sebastián Silva Cárdenas",
    cargo = "Contratista",
    cedula = "1076622641",
    fotoUrl = null,
    oficina = "Oficina de ejemplo"
)
private val HistorialEjemplo = listOf(
    ItemHistorial(TipoMarcacion.ENTRADA, "Hoy, 7:45 AM"),
    ItemHistorial(TipoMarcacion.SALIDA, "Ayer, 6:00 PM")
)

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(config, Ruta.Login)

    NavDisplay(
        backStack = backStack,
        // Boton atras del celular: quita la pantalla de arriba. Si la firma de onBack marca error, Ctrl+P sobre NavDisplay.
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Ruta.Login> {
                LoginScreen(onIngresar = {
                    // pendiente: entra sin validar; el ViewModel llamara esto solo si el login sale bien
                    backStack.clear(); backStack.add(Ruta.Home)   // asi "atras" no vuelve al Login
                })
            }
            entry<Ruta.Home> {
                HomeScreen(
                    onMiCarnet = { backStack.add(Ruta.Carnet) },
                    onHistorial = { backStack.add(Ruta.Historial) },
                    onMarcar = { backStack.add(Ruta.Marcar) },
                    onConsentimiento = { backStack.add(Ruta.Consentimiento) },
                    onCerrarSesion = { backStack.clear(); backStack.add(Ruta.Login) }
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
                HistorialScreen(items = HistorialEjemplo, onBack = { backStack.removeLastOrNull() })
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

