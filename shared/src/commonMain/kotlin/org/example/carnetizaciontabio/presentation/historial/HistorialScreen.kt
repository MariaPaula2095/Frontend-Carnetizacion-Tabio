package org.example.carnetizaciontabio.presentation.historial

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.carnetizaciontabio.domain.model.TipoMarcacion
import org.example.carnetizaciontabio.presentation.components.BarraSuperior
import org.example.carnetizaciontabio.presentation.theme.AmarilloCarnet
import org.example.carnetizaciontabio.presentation.theme.AzulNoche
import org.example.carnetizaciontabio.presentation.theme.BordeAcento
import org.example.carnetizaciontabio.presentation.theme.BordeGris
import org.example.carnetizaciontabio.presentation.theme.CarnetizacionTheme
import org.example.carnetizaciontabio.presentation.theme.EstadoInactivo
import org.koin.compose.viewmodel.koinViewModel

private val Meses = listOf("ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic")

/*
  Convierte la fecha del backend ("2026-10-16T07:45:00") en texto legible ("16 oct, 7:45 AM").
  Si el formato no es el esperado, devuelve el texto tal cual para no mostrar una pantalla rota.
 */
internal fun formatearFechaHora(iso: String): String {
    if (iso.length < 16 || iso[10] != 'T') return iso
    val dia = iso.substring(8, 10).toIntOrNull()
    val mes = iso.substring(5, 7).toIntOrNull()
    val hora = iso.substring(11, 13).toIntOrNull()
    val minutos = iso.substring(14, 16)
    if (dia == null || mes == null || hora == null || mes !in 1..12) return iso

    val sufijo = if (hora < 12) "AM" else "PM"
    val hora12 = if (hora % 12 == 0) 12 else hora % 12
    return "$dia ${Meses[mes - 1]}, $hora12:$minutos $sufijo"
}

private val MargenPantalla = 24.dp
private val SeparacionFilas = 12.dp
private val TamanoPunto = 14.dp

/*
  Una fila del historial ya lista para mostrar.
  La fecha llega convertida a texto legible desde el ViewModel.
 */
data class ItemHistorial(
    val tipo: TipoMarcacion?,
    val fecha: String
)

/*
  Pantalla conectada al ViewModel: obtiene el estado y lo delega en [HistorialContent].
 */
@Composable
fun HistorialScreen(
    onBack: () -> Unit = {},
    viewModel: HistorialViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HistorialContent(
        uiState = uiState,
        onBack = onBack,
        onReintentar = viewModel::cargar
    )
}

/*
  Pantalla sin estado propio: recibe todos los datos por parámetro.
  Permite previsualizarla y probarla sin depender de Koin ni del ViewModel.
 */
@Composable
fun HistorialContent(
    uiState: HistorialUiState,
    onBack: () -> Unit = {},
    onReintentar: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        BarraSuperior(onBack = onBack)

        Text(
            text = "Historial",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = AzulNoche,
            modifier = Modifier.padding(
                start = MargenPantalla,
                end = MargenPantalla,
                top = MargenPantalla,
                bottom = 16.dp
            )
        )

        when (uiState) {
            is HistorialUiState.Cargando -> Box(
                modifier = Modifier.fillMaxWidth().padding(MargenPantalla),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is HistorialUiState.SinSesion -> MensajeConReintento(
                texto = "No hay una sesión activa.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                onReintentar = null
            )

            is HistorialUiState.Error -> MensajeConReintento(
                texto = uiState.mensaje,
                color = MaterialTheme.colorScheme.error,
                onReintentar = onReintentar
            )

            is HistorialUiState.Contenido -> {
                if (uiState.marcaciones.isEmpty()) {
                    Mensaje(
                        "Aún no tienes marcaciones.",
                        MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth().navigationBarsPadding(),
                        contentPadding = PaddingValues(
                            start = MargenPantalla,
                            end = MargenPantalla,
                            bottom = MargenPantalla
                        ),
                        verticalArrangement = Arrangement.spacedBy(SeparacionFilas)
                    ) {
                        items(uiState.marcaciones) { FilaHistorial(it) }
                    }
                }
            }
        }
    }
}

/*
  Mensaje centrado con un enlace opcional para volver a consultar.
 */
@Composable
private fun MensajeConReintento(
    texto: String,
    color: Color,
    onReintentar: (() -> Unit)?
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(MargenPantalla),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Mensaje(texto, color)
        if (onReintentar != null) {
            Text(
                text = "Reintentar",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = AzulNoche,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable { onReintentar() }
            )
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

/*
  Punto de color a la izquierda y tarjeta con borde a la derecha.
 */
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

/*
  Etiqueta legible del tipo de marcación. Un tipo nulo se muestra como "Marcación".
 */
private fun TipoMarcacion?.etiqueta(): String = when (this) {
    TipoMarcacion.ENTRADA -> "Entrada"
    TipoMarcacion.SALIDA -> "Salida"
    TipoMarcacion.ENTRADA_ALMUERZO -> "Entrada almuerzo"
    TipoMarcacion.SALIDA_ALMUERZO -> "Salida almuerzo"
    null -> "Marcación"
}

/*
  Color del indicador según el tipo de marcación.
 */
private fun TipoMarcacion?.color(): Color = when (this) {
    TipoMarcacion.ENTRADA, TipoMarcacion.ENTRADA_ALMUERZO -> BordeAcento
    TipoMarcacion.SALIDA, TipoMarcacion.SALIDA_ALMUERZO -> AmarilloCarnet
    null -> EstadoInactivo
}

@Preview
@Composable
private fun HistorialPreview() {
    CarnetizacionTheme {
        HistorialContent(
            uiState = HistorialUiState.Contenido(
                listOf(
                    ItemHistorial(TipoMarcacion.ENTRADA, "Hoy, 7:45 AM"),
                    ItemHistorial(TipoMarcacion.SALIDA_ALMUERZO, "Hoy, 12:00 PM"),
                    ItemHistorial(TipoMarcacion.ENTRADA_ALMUERZO, "Hoy, 12:57 PM"),
                    ItemHistorial(TipoMarcacion.SALIDA, "Ayer, 6:00 PM"),
                    ItemHistorial(TipoMarcacion.ENTRADA, "Ayer, 7:45 AM"),
                    ItemHistorial(TipoMarcacion.SALIDA, "15 oct, 6:00 PM")
                )
            )
        )
    }
}
