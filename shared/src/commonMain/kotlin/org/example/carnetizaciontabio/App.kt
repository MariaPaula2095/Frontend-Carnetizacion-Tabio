package org.example.carnetizaciontabio

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.example.carnetizaciontabio.presentation.login.LoginScreen
import org.example.carnetizaciontabio.presentation.navigation.AppNavigation
import org.example.carnetizaciontabio.presentation.theme.CarnetizacionTheme


// Punto de entrada de la UI compartida. Mas adelante aqui vivirán el tema y la navegacion.

@Composable
@Preview
fun App() {
    CarnetizacionTheme {
        AppNavigation()
    }
}