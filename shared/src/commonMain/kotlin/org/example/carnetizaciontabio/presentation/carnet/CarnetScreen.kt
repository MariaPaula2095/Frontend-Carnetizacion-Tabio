package org.example.carnetizaciontabio.presentation.carnet

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.example.carnetizaciontabio.presentation.components.BarraSuperior
import org.example.carnetizaciontabio.presentation.theme.*
import org.koin.compose.viewmodel.koinViewModel

// Ajustes de tamano
private const val AnchoCarnet = 0.78f        // fraccion del ancho de la pantalla que ocupa el carnet
private const val ProporcionCarnet = 0.63f   // ancho / alto: menor a 1 = vertical
private const val ProporcionFranja = 0.44f   // que parte del alto ocupa la franja azul del frente
private val RadioCarnet = 20.dp
private val RadioFranja = 16.dp
private val TamanoFoto = 120.dp
private val AlturaFranjaDorada = 22.dp
private val AlturaCuadritos = 15.dp
private val TamanoEscudo = 40.dp
private val TamanoFranjaColor = 35.dp
private val AlturaLogoPie = 28.dp
private val AlturaLogoReverso = 42.dp

private const val TextoReverso = "Este documento acredita al portador como servidor público de la Alcaldía Municipal de Tabio. \n" +
        "El presente carnet es personal e intransferible, solo servirá como medio de identificación y no compromete a la Alcaldía Municipal de Tabio en ninguna transacción económica. \n \n" +
        "Este documento se debe portar en un lugar visible. \n \n" +
        "En caso de pérdida, favor remitirlo a la Alcaldía Municipal\n" +
        "Tel: (601) 5145878"

// Lo que dibuja el carnet. El ViewModel lo arma juntando al trabajador (login) y su area (backend).
data class DatosCarnet(
    val nombreCompleto: String,
    val cargo: String,
    val cedula: String,
    val fotoUrl: String?,
    val area: String?
)

// Estado de la pantalla del carnet.
sealed interface CarnetUiState {
    data object Cargando : CarnetUiState
    data object SinSesion : CarnetUiState
    data class Error(val mensaje: String) : CarnetUiState
    data class Contenido(val datos: DatosCarnet) : CarnetUiState
}

// Conecta la pantalla con el ViewModel y delega el dibujo a CarnetContent.
@Composable
fun CarnetScreen(
    onBack: () -> Unit = {},
    viewModel: CarnetViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CarnetContent(
        uiState = uiState,
        onBack = onBack,
        onReintentar = viewModel::cargarCarnet
    )
}

// Recibe todo por parametro para poder previsualizar y testear sin Koin.
@Composable
fun CarnetContent(
    uiState: CarnetUiState,
    onBack: () -> Unit = {},
    onReintentar: () -> Unit = {}
) {
    when (uiState) {
        is CarnetUiState.Cargando -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is CarnetUiState.SinSesion -> {
            MensajeCarnet(texto = "No hay una sesión activa.", onBack = onBack)
        }
        is CarnetUiState.Error -> {
            MensajeCarnet(texto = uiState.mensaje, onBack = onBack, onReintentar = onReintentar)
        }
        is CarnetUiState.Contenido -> {
            CarnetContenido(datos = uiState.datos, onBack = onBack)
        }
    }
}

// Pantalla simple para los estados que no tienen carnet que mostrar.
@Composable
private fun MensajeCarnet(
    texto: String,
    onBack: () -> Unit,
    onReintentar: (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        BarraSuperior(onBack = onBack)
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = texto,
                style = MaterialTheme.typography.bodyLarge,
                color = AzulNoche,
                textAlign = TextAlign.Center
            )
            if (onReintentar != null) {
                Text(
                    text = "Reintentar",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = DoradoCarnet,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clickable { onReintentar() }
                )
            }
        }
    }
}

// El carnet como tal, con los datos ya resueltos por el ViewModel.
@Composable
private fun CarnetContenido(datos: DatosCarnet, onBack: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        BarraSuperior(onBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Mi Carnet",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = AzulNoche
            )
            CarnetGiratorio(datos)
            Text(
                text = "Toca el carnet para voltearlo",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/*
  Carnet que gira al tocarlo. Un numero (rotacion) va de 0 a 180 con animacion;
  mientras es menor a 90 se dibuja el frente y despues el reverso.
 */
@Composable
private fun CarnetGiratorio(datos: DatosCarnet) {
    var mostrarReverso by remember { mutableStateOf(false) }
    val rotacion by animateFloatAsState(
        targetValue = if (mostrarReverso) 180f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "giroCarnet"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth(AnchoCarnet)
            .aspectRatio(ProporcionCarnet)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { mostrarReverso = !mostrarReverso }
            .graphicsLayer {
                rotationY = rotacion
                cameraDistance = 12f * density
            }
            .shadow(8.dp, RoundedCornerShape(RadioCarnet))
            .clip(RoundedCornerShape(RadioCarnet))
            .background(Color.White)
    ) {
        if (rotacion <= 90f) {
            CarnetFrente(datos)
        } else {
            Box(modifier = Modifier.fillMaxSize().graphicsLayer { rotationY = 180f }) {
                CarnetReverso(texto = TextoReverso)
            }
        }
    }
}

@Composable
private fun CarnetFrente(datos: DatosCarnet) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val alturaFranja = maxHeight * ProporcionFranja
        val radioInferior = RoundedCornerShape(
            topStart = 0.dp, topEnd = 0.dp,
            bottomStart = RadioFranja, bottomEnd = RadioFranja
        )

        // Foto de fondo de la franja azul
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(alturaFranja)
                .clip(radioInferior)
        ) {
            AsyncImage(
                model = ImageUrls.FONDO_CARNET,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Bloque azul: toda la franja superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(alturaFranja)
                .clip(radioInferior)
                .background(AzulCarnet.copy(alpha = 0.49f))
        )

        // Bloque verde con el escudo: encima de las banderas
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .width(TamanoFoto)
                .height(alturaFranja - TamanoFoto / 2)
                .clip(radioInferior)
                .background(VerdeFranja.copy(alpha = 0.75f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AsyncImage(
                    model = ImageUrls.ESCUDO_TABIO,
                    contentDescription = "Escudo de Tabio",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(TamanoEscudo)
                )
                AsyncImage(
                    model = ImageUrls.ALCALDIATABIO_S_BLANCO,
                    contentDescription = "Alcaldía Municipal de Tabio",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth(0.92f)
                )
            }
        }

        // Banderas: ocupan todo el ancho de la franja
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(alturaFranja)
                .clip(radioInferior)
        ) {
            AsyncImage(
                model = ImageUrls.BANDERAS_CARNET,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            )
        }

        // Foto del trabajador: el centro queda en el borde inferior de la franja
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = alturaFranja - TamanoFoto / 2)
                .size(TamanoFoto)
                .clip(CircleShape)
                .background(BordeGris)
                .border(4.dp, DoradoCarnet, CircleShape)
        ) {
            if (datos.fotoUrl != null) {
                AsyncImage(
                    model = datos.fotoUrl,
                    contentDescription = "Foto del trabajador",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // Datos del trabajador
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = alturaFranja + TamanoFoto / 2 + 12.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = datos.nombreCompleto.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = DoradoCarnet,
                textAlign = TextAlign.Center
            )
            Text(datos.cargo, style = MaterialTheme.typography.bodyMedium, color = AzulNoche)
            if (datos.area != null) {
                Text(
                    text = datos.area,
                    style = MaterialTheme.typography.bodySmall,
                    color = AzulNoche,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = "CC: ${datos.cedula}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = AzulNoche
            )
        }

        // Franja dorada del pie
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(AlturaFranjaDorada)
                .background(DoradoCarnet)
        )

        // Cuadraditos: su centro cae justo sobre el borde superior del dorado
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 12.dp)
                .offset(y = -(AlturaFranjaDorada + AlturaCuadritos / 2 - 15.dp)),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(Modifier.width(TamanoFranjaColor).height(AlturaCuadritos).background(AzulFranja))
            Box(Modifier.width(TamanoFranjaColor).height(AlturaCuadritos).background(GrisFranja))
            Box(Modifier.width(TamanoFranjaColor).height(AlturaCuadritos).background(VerdeFranja))
        }

        // Logo TABIO en negro, montado sobre el dorado
        AsyncImage(
            model = ImageUrls.ALCALDIATABIO_NEGRO,
            contentDescription = "Alcaldía Municipal de Tabio",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 8.dp)
                .offset(y = -(AlturaFranjaDorada + AlturaLogoPie / 2 - 30.dp))
                .height(AlturaLogoPie)
        )
    }
}

@Composable
private fun CarnetReverso(texto: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth().height(AlturaFranjaDorada).background(DoradoCarnet))

        // Texto mas pequeno para dejar sitio al pie en la zona blanca
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = texto,
                style = MaterialTheme.typography.bodySmall,
                color = AzulNoche,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
        }

        // Cuadritos justo arriba del logo, ambos centrados en la zona blanca
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(Modifier.width(TamanoFranjaColor).height(AlturaCuadritos).background(AzulFranja))
                Box(Modifier.width(TamanoFranjaColor).height(AlturaCuadritos).background(GrisFranja))
                Box(Modifier.width(TamanoFranjaColor).height(AlturaCuadritos).background(VerdeFranja))
            }
            AsyncImage(
                model = ImageUrls.ALCALDIATABIO_NEGRO,
                contentDescription = "Alcaldía Municipal de Tabio",
                contentScale = ContentScale.Fit,
                modifier = Modifier.height(AlturaLogoReverso)
            )
        }

        // Franja dorada sola, sin nada encima
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(AlturaFranjaDorada)
                .background(DoradoCarnet)
        )
    }
}

@Preview
@Composable
private fun CarnetPreview() {
    CarnetizacionTheme {
        CarnetContent(
            uiState = CarnetUiState.Contenido(
                DatosCarnet(
                    nombreCompleto = "Juan Sebastián Silva Cárdenas",
                    cargo = "Contratista",
                    cedula = "1076622641",
                    fotoUrl = null,
                    area = "Oficina ejemplo"
                )
            )
        )
    }
}
