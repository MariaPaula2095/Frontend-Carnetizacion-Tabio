package org.example.carnetizaciontabio.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Boton ancho de color solido, como los de los mockups. El color y el alto se eligen en cada uso.
@Composable
fun BotonAccion(
    texto: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    alto: Dp = 52.dp
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(alto),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color, contentColor = Color.White)
    ) {
        Text(texto, style = MaterialTheme.typography.titleMedium)
    }
}