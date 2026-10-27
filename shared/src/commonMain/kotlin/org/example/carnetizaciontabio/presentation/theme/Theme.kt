package org.example.carnetizaciontabio.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Asigna cada color de la paleta a un "rol" de Material (primary, background...).
// Asi los componentes toman los colores solos, sin repetir hex en cada pantalla.
private val LightColors = lightColorScheme(
    primary = AzulHeader,
    onPrimary = Color.White,
    primaryContainer = AzulAccion,
    onPrimaryContainer = Color.White,
    secondary = VerdeAccion,
    onSecondary = Color.White,
    tertiary = CelesteAccion,
    background = Blanco,
    onBackground = TextoPrincipal,
    surface = Blanco,
    onSurface = TextoPrincipal,
    surfaceVariant = GrisNavbar,
    onSurfaceVariant = TextoSecundario,
    outline = BordeGris,
    error = EstadoRechazado,
    onError = Color.White
)

/** Tema de toda la app. Se envuelve una vez en App() y todas las pantallas lo heredan. */
@Composable
fun CarnetizacionTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}