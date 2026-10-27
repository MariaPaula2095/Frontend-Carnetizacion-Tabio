package org.example.carnetizaciontabio.presentation.marcacion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import org.example.carnetizaciontabio.presentation.components.BarraSuperior
import org.example.carnetizaciontabio.presentation.theme.AzulNoche
import org.example.carnetizaciontabio.presentation.theme.BordeGris
import org.example.carnetizaciontabio.presentation.theme.CarnetizacionTheme
import org.example.carnetizaciontabio.presentation.theme.DoradoCarnet

// ---- Ajustes de espaciado y tamano ----
private val MargenPantalla = 24.dp
private val SeparacionVertical = 24.dp
private const val AnchoMarco = 0.8f   // fraccion del ancho de la pantalla que ocupa el marco del QR

/*
  Pantalla donde el trabajador muestra su QR para marcar entrada o salida.
  codigoQr es null mientras se carga; ultimaMarcacion es null si todavia no hay ninguna.
 */
@Composable
fun MarcarScreen(
    codigoQr: String?,
    ultimaMarcacion: String?,
    onBack: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        BarraSuperior(onBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(MargenPantalla),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(SeparacionVertical)
        ) {
            Text(
                text = "Marcar Entrada/Salida",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = AzulNoche
            )
            Text(
                text = "Escanea tu código:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            MarcoQr(codigoQr)

            Text(
                text = if (ultimaMarcacion != null) "Última marcación: $ultimaMarcacion" else "Aún no tienes marcaciones hoy",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/*
  Cuadro con borde dorado que contiene el QR. El QR va sobre fondo BLANCO con margen:
  sin ese contraste y espacio alrededor, muchos lectores no lo reconocen.
 */
@Composable
private fun MarcoQr(codigoQr: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth(AnchoMarco)
            .aspectRatio(1f)
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(6.dp, DoradoCarnet, RoundedCornerShape(12.dp))
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        if (codigoQr != null) {
            Image(
                painter = rememberQrCodePainter(codigoQr),
                contentDescription = "Código QR para marcar",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Mientras llega el codigo del backend
            Box(modifier = Modifier.fillMaxSize().background(BordeGris, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
private fun MarcarPreview() {
    CarnetizacionTheme {
        MarcarScreen(codigoQr = "EJEMPLO-123456", ultimaMarcacion = "Entrada - 07:45 AM")
    }
}

