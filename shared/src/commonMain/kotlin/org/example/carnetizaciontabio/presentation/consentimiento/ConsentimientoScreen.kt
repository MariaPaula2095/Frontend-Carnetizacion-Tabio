package org.example.carnetizaciontabio.presentation.consentimiento

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.carnetizaciontabio.presentation.components.BarraSuperior
import org.example.carnetizaciontabio.presentation.components.BotonAccion
import org.example.carnetizaciontabio.presentation.theme.AzulNoche
import org.example.carnetizaciontabio.presentation.theme.BordeGris
import org.example.carnetizaciontabio.presentation.theme.CarnetizacionTheme
import org.example.carnetizaciontabio.presentation.theme.VerdeAccion
import org.example.carnetizaciontabio.presentation.theme.TextoPrincipal
import org.koin.compose.viewmodel.koinViewModel

// ---- Ajustes de espaciado ----
private val MargenPantalla = 24.dp
private val SeparacionVertical = 16.dp

// ---- Estilo del texto ----
private val ColorTituloSeccion = AzulNoche        // titulos: mas oscuros para resaltar
private val ColorTextoCuerpo = TextoPrincipal     // cuerpo: gris oscuro, un poco mas suave que los titulos
private val AlineacionTitulos = TextAlign.Start

private data class SeccionConsentimiento(val titulo: String, val cuerpo: String)

// TODO: texto aprobado por la empresa.
// El numero se agrega solo al dibujar, no se escribe en el titulo.
private val Secciones = listOf(
    SeccionConsentimiento(
        titulo = "Tratamiento de datos",
        cuerpo = "EMSERTABIO E.S.P., como responsable del tratamiento, recolectará y usará los siguientes datos tuyos: nombre y apellidos, número de cédula, fotografía, cargo, área y los registros de fecha y hora de tus entradas y salidas."
    ),
    SeccionConsentimiento(
        titulo = "Finalidad",
        cuerpo = "Identificarte con tu carné digital y controlar el acceso y la asistencia a las instalaciones de la empresa. No se usan datos biométricos (huella o rostro): el registro se hace únicamente leyendo el código QR de tu carné."
    ),
    SeccionConsentimiento(
        titulo = "Seguridad",
        cuerpo = "EMSERTABIO E.S.P. aplica medidas técnicas y administrativas para proteger tus datos contra acceso no autorizado, pérdida o uso indebido. Tu información solo puede ser consultada por el personal autorizado de la empresa y no se utiliza para fines distintos a los descritos."
    ),
    SeccionConsentimiento(
        titulo = "Derechos del usuario",
        cuerpo = "Puedes conocer, actualizar y rectificar tus datos; solicitar prueba de esta autorización; ser informado del uso que se les da; y revocar la autorización o pedir la supresión de tus datos cuando no exista un deber legal o contractual de conservarlos."
    )
)

private const val CierreConsentimiento =
    "Al marcar la casilla y pulsar «Aceptar y continuar», declaras que leíste esta información y autorizas de manera libre, previa e informada el tratamiento descrito."

/*
  Conecta la pantalla con el ViewModel y delega el dibujo a ConsentimientoContent.
 */
@Composable
fun ConsentimientoScreen(
    onAceptar: () -> Unit = {},
    onBack: () -> Unit = {},
    viewModel: ConsentimientoViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ConsentimientoContent(
        uiState = uiState,
        onAceptarClick = viewModel::aceptar,
        onAceptado = onAceptar,
        onBack = onBack,
        onReintentar = viewModel::cargar
    )
}

/*
  Recibe todo por parametro para poder previsualizar y testear sin Koin.
  La casilla es estado propio de la pantalla (acepto); lo demas llega en el uiState.
 */
@Composable
fun ConsentimientoContent(
    uiState: ConsentimientoUiState,
    onAceptarClick: () -> Unit = {},
    onAceptado: () -> Unit = {},
    onBack: () -> Unit = {},
    onReintentar: () -> Unit = {}
) {
    when (uiState) {
        is ConsentimientoUiState.Cargando -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is ConsentimientoUiState.SinSesion -> {
            MensajeConsentimiento(
                texto = "No hay una sesión activa.",
                onBack = onBack
            )
        }

        is ConsentimientoUiState.Error -> {
            MensajeConsentimiento(
                texto = uiState.mensaje,
                onBack = onBack,
                onReintentar = onReintentar
            )
        }

        is ConsentimientoUiState.Contenido -> {
            ConsentimientoContenido(
                estado = uiState,
                onAceptarClick = onAceptarClick,
                onContinuar = onAceptado,
                onBack = onBack
            )
        }
    }
}

/*
  Mensaje centrado para los estados SinSesion y Error, con reintento opcional.
 */
@Composable
private fun MensajeConsentimiento(
    texto: String,
    onBack: () -> Unit,
    onReintentar: (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        BarraSuperior(onBack = onBack)

        Column(
            modifier = Modifier.fillMaxSize().padding(MargenPantalla),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = texto,
                style = MaterialTheme.typography.bodyLarge,
                color = ColorTextoCuerpo,
                textAlign = TextAlign.Center
            )
            if (onReintentar != null) {
                Text(
                    text = "Reintentar",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = ColorTituloSeccion,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clickable { onReintentar() }
                )
            }
        }
    }
}

/*
  Layout de la politica. Toma los datos resueltos del estado Contenido.
 */
@Composable
private fun ConsentimientoContenido(
    estado: ConsentimientoUiState.Contenido,
    onAceptarClick: () -> Unit,
    onContinuar: () -> Unit,
    onBack: () -> Unit
) {
    // Si ya acepto antes, la casilla arranca marcada y no se puede desmarcar.
    var acepto by remember(estado.yaAceptado) { mutableStateOf(estado.yaAceptado) }
    val scroll = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        BarraSuperior(onBack = onBack)

        // weight(1f) hace que esta columna ocupe el resto de la pantalla: el texto se desplaza
        // dentro de su caja y la casilla y el boton quedan siempre visibles abajo.
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(MargenPantalla),
            verticalArrangement = Arrangement.spacedBy(SeparacionVertical)
        ) {
            Text(
                text = "Consentimiento",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = AzulNoche,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(BordeGris, RoundedCornerShape(12.dp))
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 8.dp)  // menos margen a la derecha: ahi va la barra
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .barraDesplazamiento(scroll, ColorTituloSeccion.copy(alpha = 0.45f))
                        .verticalScroll(scroll)
                        .padding(end = 12.dp),  // el texto no pasa por debajo de la barra
                    verticalArrangement = Arrangement.spacedBy(16.dp)   // espacio entre secciones
                ) {
                    Secciones.forEachIndexed { indice, seccion ->
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "${indice + 1}. ${seccion.titulo}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = ColorTituloSeccion,
                                textAlign = AlineacionTitulos,
                                modifier = Modifier.fillMaxWidth()   // sin esto la alineacion no se nota: el texto solo ocupa lo que mide
                            )
                            Text(
                                text = seccion.cuerpo,
                                style = MaterialTheme.typography.bodyMedium,
                                color = ColorTextoCuerpo
                            )
                        }
                    }
                    Text(
                        text = CierreConsentimiento,
                        style = MaterialTheme.typography.bodyMedium,
                        color = ColorTextoCuerpo
                    )
                }
            }

            Text(
                text = "Versión de política: ${estado.versionPolitica}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // Toda la fila es tocable, no solo el cuadrito: es mas facil de acertar con el dedo
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !estado.yaAceptado) { acepto = !acepto },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = acepto,
                    onCheckedChange = null,
                    enabled = !estado.yaAceptado
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = if (estado.yaAceptado) {
                        "Ya aceptaste esta versión de la política"
                    } else {
                        "He leído y acepto los términos"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }


            if (estado.errorAlEnviar != null) {
                Text(
                    text = estado.errorAlEnviar,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            if (estado.yaAceptado) {
                BotonAccion(
                    texto = "Continuar",
                    color = VerdeAccion,
                    onClick = onContinuar
                )
            } else {
                BotonAccion(
                    texto = if (estado.enviando) "Enviando..." else "Aceptar y continuar",
                    color = VerdeAccion,
                    onClick = onAceptarClick,
                    enabled = acepto && !estado.enviando
                )
            }
        }
    }
}

@Preview
@Composable
private fun ConsentimientoPreview() {
    CarnetizacionTheme {
        ConsentimientoContent(
            uiState = ConsentimientoUiState.Contenido(
                versionPolitica = "1.0",
                yaAceptado = false
            )
        )
    }
}

/*
  Dibuja una barra de desplazamiento siempre visible en el borde derecho.
  Su alto indica que parte del contenido se ve y su posicion, cuanto se ha bajado.
  Va antes de verticalScroll en la cadena, para medir el area visible y no el contenido completo.
 */
private fun Modifier.barraDesplazamiento(
    estado: ScrollState,
    color: Color,
    ancho: Dp = 4.dp
): Modifier = drawWithContent {
    drawContent()
    val maximo = estado.maxValue
    // maxValue vale Int.MAX_VALUE hasta que se mide el contenido; si es 0, todo cabe y no hace falta barra
    if (maximo > 0 && maximo != Int.MAX_VALUE) {
        val alturaVisible = size.height
        val alturaTotal = alturaVisible + maximo
        val alturaBarra = (alturaVisible * alturaVisible / alturaTotal).coerceAtLeast(24.dp.toPx())
        val recorrido = alturaVisible - alturaBarra
        val posicion = recorrido * (estado.value.toFloat() / maximo)
        drawRoundRect(
            color = color,
            topLeft = Offset(size.width - ancho.toPx(), posicion),
            size = Size(ancho.toPx(), alturaBarra),
            cornerRadius = CornerRadius(ancho.toPx() / 2)
        )
    }
}
