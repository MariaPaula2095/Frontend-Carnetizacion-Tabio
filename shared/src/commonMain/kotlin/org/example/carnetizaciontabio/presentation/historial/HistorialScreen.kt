package org.example.carnetizaciontabio.presentation.historial

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.carnetizaciontabio.domain.model.TipoMarcacion
import org.example.carnetizaciontabio.presentation.components.BarraSuperior
import org.example.carnetizaciontabio.presentation.theme.AmarilloCarnet
import org.example.carnetizaciontabio.presentation.theme.AzulNoche
import org.example.carnetizaciontabio.presentation.theme.BordeAcento
import org.example.carnetizaciontabio.presentation.theme.BordeGris
import org.example.carnetizaciontabio.presentation.theme.CarnetizacionTheme
import org.example.carnetizaciontabio.presentation.theme.EstadoInactivo

// ---- Ajustes de espaciado y tamano ----
private val MargenPantalla = 24.dp
private val SeparacionFilas = 12.dp
private val TamanoPunto = 14.dp

// Una fila ya lista para mostrar. El ViewModel convertira la fecha ISO del backend en texto legible.
data class ItemHistorial(
    val tipo: TipoMarcacion?,
    val fecha: String   // ejemplo: "Hoy, 7:45 AM"
)

@Composable
fun HistorialScreen(
    items: List<ItemHistorial>,
    onBack: () -> Unit = {},
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        BarraSuperior(onBack = onBack)

        Text(
            text = "Historial",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = AzulNoche,
            modifier = Modifier.padding(start = MargenPantalla, end = MargenPantalla, top = MargenPantalla, bottom = 16.dp)
        )

        // Solo se dibuja uno de estos cuatro casos
        when {
            isLoading -> Box(modifier = Modifier.fillMaxWidth().padding(MargenPantalla), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            errorMessage != null -> Mensaje(errorMessage, MaterialTheme.colorScheme.error)
            items.isEmpty() -> Mensaje("Aún no tienes marcaciones.", MaterialTheme.colorScheme.onSurfaceVariant)
            else -> LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth().navigationBarsPadding(),
                contentPadding = PaddingValues(start = MargenPantalla, end = MargenPantalla, bottom = MargenPantalla),
                verticalArrangement = Arrangement.spacedBy(SeparacionFilas)
            ) {
                items(items) { FilaHistorial(it) }
            }
        }
    }
}

@Composable
private fun Mensaje(texto: String, color: Color) {
    Text(
        text = texto,
        color = color,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(MargenPantalla)
    )
}

// Punto de color a la izquierda y tarjeta con borde a la derecha, como en el mockup.
@Composable
private fun FilaHistorial(item: ItemHistorial) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(modifier = Modifier.size(TamanoPunto).background(item.tipo.color(), CircleShape))
        Text(
            text = "${item.tipo.etiqueta()} - ${item.fecha}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .weight(1f)
                .background(Color.White, RoundedCornerShape(8.dp))
                .border(1.dp, BordeGris, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 14.dp)
        )
    }
}

// El backend tiene cuatro tipos de marcacion.
private fun TipoMarcacion?.etiqueta(): String = when (this) {
    TipoMarcacion.ENTRADA -> "Entrada"
    TipoMarcacion.SALIDA -> "Salida"
    TipoMarcacion.ENTRADA_ALMUERZO -> "Entrada almuerzo"
    TipoMarcacion.SALIDA_ALMUERZO -> "Salida almuerzo"
    null -> "Marcación"
}

private fun TipoMarcacion?.color(): Color = when (this) {
    TipoMarcacion.ENTRADA, TipoMarcacion.ENTRADA_ALMUERZO -> BordeAcento
    TipoMarcacion.SALIDA, TipoMarcacion.SALIDA_ALMUERZO -> AmarilloCarnet
    null -> EstadoInactivo
}

@Preview
@Composable
private fun HistorialPreview() {
    CarnetizacionTheme {
        HistorialScreen(
            items = listOf(
                ItemHistorial(TipoMarcacion.ENTRADA, "Hoy, 7:45 AM"),
                ItemHistorial(TipoMarcacion.SALIDA_ALMUERZO, "Hoy, 12:00 PM"),
                ItemHistorial(TipoMarcacion.ENTRADA_ALMUERZO, "Hoy, 12:57 PM"),
                ItemHistorial(TipoMarcacion.SALIDA, "Ayer, 6:00 PM"),
                ItemHistorial(TipoMarcacion.ENTRADA, "Ayer, 7:45 AM"),
                ItemHistorial(TipoMarcacion.SALIDA, "15 oct, 6:00 PM")
            )
        )
    }
}

