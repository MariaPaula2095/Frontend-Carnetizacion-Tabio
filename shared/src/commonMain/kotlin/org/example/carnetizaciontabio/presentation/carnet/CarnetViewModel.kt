package org.example.carnetizaciontabio.presentation.carnet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.usecase.ObtenerAreas
import org.example.carnetizaciontabio.domain.usecase.ObtenerTrabajadorActual

/*
 ViewModel del carnet. Junta dos fuentes:
  - ObtenerTrabajadorActual: el trabajador que inicio sesion (no es suspend, puede ser null).
  - ObtenerAreas: la lista de areas, para resolver el areaId al nombre.
*/
class CarnetViewModel(
    private val obtenerTrabajadorActual: ObtenerTrabajadorActual,
    private val obtenerAreas: ObtenerAreas
) : ViewModel() {

    private val _uiState = MutableStateFlow<CarnetUiState>(CarnetUiState.Cargando)
    val uiState: StateFlow<CarnetUiState> = _uiState.asStateFlow()

    init {
        cargarCarnet()
    }

    fun cargarCarnet() {
        viewModelScope.launch {
            _uiState.value = CarnetUiState.Cargando

            // Si no hay sesion, no hay carnet que mostrar.
            val trabajador = obtenerTrabajadorActual()
            if (trabajador == null) {
                _uiState.value = CarnetUiState.SinSesion
                return@launch
            }

            // El area se resuelve desde el areaId cruzando con la lista de areas.
            // Si el areaId es null o no aparece en la lista, el carnet se muestra sin area.
            when (val resultado = obtenerAreas()) {
                is ApiResult.Success -> {
                    val area = trabajador.areaId?.let { id ->
                        resultado.data.firstOrNull { it.id == id }
                    }
                    _uiState.value = CarnetUiState.Contenido(
                        DatosCarnet(
                            nombreCompleto = trabajador.nombreCompleto,
                            cargo = trabajador.cargo,
                            cedula = trabajador.cedula,
                            fotoUrl = trabajador.fotoUrl,
                            area = area?.nombre
                        )
                    )
                }
                is ApiResult.Error -> {
                    _uiState.value = if (resultado.code == 401 || resultado.code == 403) {
                        CarnetUiState.SinSesion
                    } else {
                        CarnetUiState.Error(resultado.message)
                    }
                }
            }
        }
    }
}
