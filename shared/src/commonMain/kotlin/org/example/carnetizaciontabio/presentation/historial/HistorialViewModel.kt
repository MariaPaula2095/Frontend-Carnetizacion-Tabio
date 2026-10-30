package org.example.carnetizaciontabio.presentation.historial

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.usecase.ObtenerHistorial

/*
  Estado y lógica de la pantalla de historial.
  Consulta las marcaciones del trabajador y las expone listas para la UI.
 */
class HistorialViewModel(
    private val obtenerHistorial: ObtenerHistorial
) : ViewModel() {

    var items by mutableStateOf<List<ItemHistorial>>(emptyList())
        private set
    var isLoading by mutableStateOf(true)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        cargar()
    }

    /*
      Consulta el historial en el backend y actualiza el estado de la pantalla.
     */
    fun cargar() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            when (val resultado = obtenerHistorial()) {
                is ApiResult.Success -> items = resultado.data.map { marcacion ->
                    ItemHistorial(
                        tipo = marcacion.tipo,
                        fecha = formatearFechaHora(marcacion.fechaHora)
                    )
                }

                is ApiResult.Error -> errorMessage = resultado.message
            }
            isLoading = false
        }
    }
}
