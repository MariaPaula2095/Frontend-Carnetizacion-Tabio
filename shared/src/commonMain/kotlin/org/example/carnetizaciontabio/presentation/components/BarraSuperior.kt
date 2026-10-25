package org.example.carnetizaciontabio.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.example.carnetizaciontabio.presentation.theme.ImageUrls

/*
  Barra azul superior con escudo y logo. Si recibe onCerrarSesion, muestra a la derecha el icono de salir;
  si no, no lo muestra (asi la reutilizan las pantallas que llevan flecha de regreso).
 */
@Composable
fun BarraSuperior(onCerrarSesion: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = ImageUrls.ESCUDO_TABIO,
            contentDescription = "Escudo de Tabio",
            modifier = Modifier.height(50.dp)
        )
        Box(modifier = Modifier.width(1.dp).height(32.dp).background(Color.White.copy(alpha = 0.6f)))
        AsyncImage(
            model = ImageUrls.LOGO_EMSERTABIO,
            contentDescription = "Logo de EMSERTABIO",
            modifier = Modifier.height(50.dp)
        )

        if (onCerrarSesion != null) {
            // Un Spacer con weight(1f) ocupa el espacio libre de la fila y empuja lo que sigue al extremo derecho
            Spacer(Modifier.weight(1f))
            // La zona tactil mide 44 dp aunque el icono sea de 24: asi es facil de tocar con el dedo
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onCerrarSesion),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageUrls.LOGOUT,
                    contentDescription = "Cerrar sesión",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}