package org.example.carnetizaciontabio.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.example.carnetizaciontabio.presentation.components.BarraSuperior
import org.example.carnetizaciontabio.presentation.theme.AmarilloCarnet
import org.example.carnetizaciontabio.presentation.theme.AzulMedio
import org.example.carnetizaciontabio.presentation.theme.AzulNoche
import org.example.carnetizaciontabio.presentation.theme.BordeAcento
import org.example.carnetizaciontabio.presentation.theme.CarnetizacionTheme
import org.example.carnetizaciontabio.presentation.theme.ImageUrls
import org.example.carnetizaciontabio.presentation.theme.VerdeAccion

/*
  Pantalla de inicio del trabajador: acceso a carnet, historial, marcacion y consentimiento.
  Las funciones onXxx avisan que se toco una opcion; la navegacion las conectara despues.
 */

private val MargenPantalla = 24.dp       // espacio entre el borde de la pantalla y el contenido
private val SeparacionVertical = 78.dp   // espacio entre bloques (titulo, tarjetas, botones)
private val SeparacionTarjetas = 20.dp   // espacio entre las dos tarjetas
private val SeparacionBotones = 12.dp  // espacio entre los dos botones de abajo
private val ProporcionTarjeta = 0.70f    // ancho / alto: 1f = cuadrada, menor = mas alta
private val TamanoIcono = 92.dp          // tamano del icono dentro de la tarjeta

@Composable
fun HomeScreen(
    onMiCarnet: () -> Unit = {},
    onHistorial: () -> Unit = {},
    onMarcar: () -> Unit = {},
    onConsentimiento: () -> Unit = {},
    onCerrarSesion: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        BarraSuperior(onCerrarSesion = onCerrarSesion)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(MargenPantalla),
            verticalArrangement = Arrangement.spacedBy(SeparacionVertical)  // espacio entre todos los bloques
        ) {
            // Titulo y subtitulo en su propia Column: asi quedan juntos aunque los bloques esten separados
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "EMSERTABIO E.S.P",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = AzulNoche
                )
                Text(
                    text = "Empresa de Servicios Públicos de Tabio",
                    style = MaterialTheme.typography.bodyLarge,
                    color = AzulNoche
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(SeparacionTarjetas)) {
                TarjetaMenu(
                    titulo = "Mi Carnet",
                    fondo = AmarilloCarnet,
                    colorTexto = AzulNoche,
                    icono = ImageUrls.USER,
                    textoArriba = true,
                    onClick = onMiCarnet,
                    modifier = Modifier.weight(1f)
                )
                TarjetaMenu(
                    titulo = "Historial",
                    fondo = AzulMedio,
                    colorTexto = Color.White,
                    icono = ImageUrls.CALENDAR,
                    textoArriba = false,
                    onClick = onHistorial,
                    modifier = Modifier.weight(1f)
                )
            }

            // Los dos botones en su propia Column: se separan poco entre si, aunque los bloques de arriba esten muy separados
            Column(verticalArrangement = Arrangement.spacedBy(SeparacionBotones)) {
                BotonAccion("Marcar Entrada/Salida", VerdeAccion, onMarcar, alto = 58.dp)
                BotonAccion("Consentimiento", BordeAcento, onConsentimiento, alto = 44.dp)
            }
        }
    }
}

/*
  Tarjeta vertical de color. El icono va siempre en el centro; el titulo arriba o abajo segun el mockup.
  weight(1f) (puesto por quien la usa) fija el ancho; aspectRatio calcula el alto a partir de ese ancho.
 */
@Composable
private fun TarjetaMenu(
    titulo: String,
    fondo: Color,
    colorTexto: Color,
    icono: String,
    textoArriba: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(ProporcionTarjeta)
            .clip(RoundedCornerShape(16.dp))  // se recorta primero para que el efecto del toque respete las esquinas
            .background(fondo)
            .clickable(onClick = onClick)
            .padding(16.dp)                   // distancia entre el borde de la tarjeta y el titulo
    ) {
        AsyncImage(
            model = icono,
            contentDescription = null,
            modifier = Modifier.size(TamanoIcono).align(Alignment.Center)
        )
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = colorTexto,
            modifier = Modifier.align(if (textoArriba) Alignment.TopCenter else Alignment.BottomCenter)
        )
    }
}

// Boton ancho de color solido, como los de los mockups. El color y el alto se eligen en cada uso.
@Composable
private fun BotonAccion(texto: String, color: Color, onClick: () -> Unit, alto: Dp = 52.dp) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(alto),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color, contentColor = Color.White)
    ) {
        Text(texto, style = MaterialTheme.typography.titleMedium)
    }
}

@Preview
@Composable
private fun HomePreview() {
    CarnetizacionTheme { HomeScreen() }
}