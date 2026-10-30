package org.example.carnetizaciontabio.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.usecase.LoginTrabajador
import org.example.carnetizaciontabio.presentation.theme.CarnetizacionTheme
import org.example.carnetizaciontabio.presentation.theme.FondoLogin
import org.example.carnetizaciontabio.presentation.theme.ImageUrls
import org.koin.compose.koinInject

@Composable
fun LoginScreen(onIngresar: () -> Unit = {}) {
    var cedula by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val loginTrabajador: LoginTrabajador = koinInject()

    LoginContent(
        cedula = cedula,
        pin = pin,
        onCedulaChange = { cedula = it },
        onPinChange = { pin = it },
        errorMessage = errorMessage,
        isLoading = isLoading,
        onIngresar = {
            errorMessage = null
            isLoading = true
            scope.launch {
                when (val resultado = loginTrabajador(cedula, pin)) {
                    // La sesion ya quedo guardada en memoria: ahora se navega a Home
                    is ApiResult.Success -> onIngresar()
                    // El mensaje ya viene listo para mostrarlo al usuario
                    is ApiResult.Error -> errorMessage = resultado.message
                }
                isLoading = false
            }
        }
    )
}

// Dibujo de la pantalla. No guarda estado: recibe por parametros, asi se puede previsualizar y probar facil.
@Composable
fun LoginContent(
    cedula: String,
    pin: String,
    onCedulaChange: (String) -> Unit,
    onPinChange: (String) -> Unit,
    onIngresar: () -> Unit,
    errorMessage: String? = null,
    isLoading: Boolean = false
) {
    // El degradado va en la caja exterior para que cubra toda la pantalla, incluso bajo las barras del sistema.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoLogin)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()                  // el espacio seguro va adentro, no junto al fondo
                .verticalScroll(rememberScrollState()) // si el teclado ocupa espacio, se puede desplazar
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Tarjeta blanca
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    // Logo
                    AsyncImage(
                        model = ImageUrls.LOGO_EMSERTABIO,
                        contentDescription = "Logo de EMSERTABIO",
                        modifier = Modifier.size(104.dp)
                    )

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ){
                        Text(
                            text = "EMSERTABIO S.A E.S.P",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "SERVICIOS PÚBLICOS",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Text(
                    text = "Ingreso al sistema",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    value = cedula,
                    onValueChange = onCedulaChange,
                    label = { Text("Ingrese su Cédula") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = pin,
                    onValueChange = onPinChange,
                    label = { Text("Ingrese su Pin") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(), // oculta los caracteres
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                // Solo aparece si hay un error que mostrar
                if (errorMessage != null) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Button(
                    onClick = onIngresar,
                    enabled = !isLoading,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary // el verde de accion
                    ),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Ingresar")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginPreview() {
    CarnetizacionTheme {
        LoginContent(
            cedula = "",
            pin = "",
            onCedulaChange = {},
            onPinChange = {},
            onIngresar = {}
        )
    }
}