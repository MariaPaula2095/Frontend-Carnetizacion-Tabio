package org.example.carnetizaciontabio.presentation.historial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.usecase.ObtenerHistorial

/*
  Estado de la pantalla de historial.
 */
sealed interface HistorialUiState {
    data object Cargando : HistorialUiState
    data object SinSesion : HistorialUiState
    data class Error(val mensaje: String) : HistorialUiState
    data class Contenido(val marcaciones: List<ItemHistorial>) : HistorialUiState
}

/*
  Estado y lógica de la pantalla de historial.
  Consulta las marcaciones del trabajador y las expone listas para la UI.
 */
class HistorialViewModel(
    private val obtenerHistorial: ObtenerHistorial
) : ViewModel() {

    private val _uiState = MutableStateFlow<HistorialUiState>(HistorialUiState.Cargando)
    val uiState: StateFlow<HistorialUiState> = _uiState.asStateFlow()

    init {
        cargar()
    }

    /*
      Consulta el historial en el backend y actualiza el estado de la pantalla.
     */
    fun cargar() {
        viewModelScope.launch {
            _uiState.value = HistorialUiState.Cargando
            when (val resultado = obtenerHistorial()) {
                is ApiResult.Success -> {
                    _uiState.value = HistorialUiState.Contenido(
                        resultado.data.map { marcacion ->
                            ItemHistorial(
                                tipo = marcacion.tipo,
                                fecha = formatearFechaHora(marcacion.fechaHora)
                            )
                        }
                    )
                }

                is ApiResult.Error -> {
                    _uiState.value = when (resultado.code) {
                        401, 403 -> HistorialUiState.SinSesion
                        else -> HistorialUiState.Error(resultado.message)
                    }
                }
            }
        }
    }
}
