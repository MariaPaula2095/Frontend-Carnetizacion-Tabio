package org.example.carnetizaciontabio.presentation.consentimiento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.usecase.AceptarConsentimiento
import org.example.carnetizaciontabio.domain.usecase.ObtenerMiConsentimiento

// Estado de la pantalla de consentimiento.
sealed interface ConsentimientoUiState {
    data object Cargando : ConsentimientoUiState
    data object SinSesion : ConsentimientoUiState
    data class Error(val mensaje: String) : ConsentimientoUiState
    data class Contenido(
        val versionPolitica: String,
        val yaAceptado: Boolean,
        val enviando: Boolean = false,
        val errorAlEnviar: String? = null
    ) : ConsentimientoUiState
}


/*
 Consulta el consentimiento al entrar y registra la aceptacion.
 El backend responde 404 cuando el trabajador nunca ha aceptado,
 por lo que ese codigo se interpreta como "no ha aceptado" y no como error.
 */
class ConsentimientoViewModel(
    private val obtenerMiConsentimiento: ObtenerMiConsentimiento,
    private val aceptarConsentimiento: AceptarConsentimiento
) : ViewModel() {

    private val _uiState = MutableStateFlow<ConsentimientoUiState>(ConsentimientoUiState.Cargando)
    val uiState: StateFlow<ConsentimientoUiState> = _uiState.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _uiState.value = ConsentimientoUiState.Cargando
            when (val resultado = obtenerMiConsentimiento()) {
                is ApiResult.Success -> {
                    _uiState.value = ConsentimientoUiState.Contenido(
                        versionPolitica = resultado.data.versionPolitica.ifBlank { VERSION_POR_DEFECTO },
                        yaAceptado = resultado.data.aceptado
                    )
                }
                is ApiResult.Error -> {
                    _uiState.value = when (resultado.code) {
                        401, 403 -> ConsentimientoUiState.SinSesion
                        404 -> ConsentimientoUiState.Contenido(
                            versionPolitica = VERSION_POR_DEFECTO,
                            yaAceptado = false
                        )
                        else -> ConsentimientoUiState.Error(resultado.message)
                    }
                }
            }
        }
    }

    fun aceptar() {
        val actual = _uiState.value
        if (actual !is ConsentimientoUiState.Contenido || actual.enviando || actual.yaAceptado) return

        viewModelScope.launch {
            _uiState.value = actual.copy(enviando = true, errorAlEnviar = null)
            when (val resultado = aceptarConsentimiento(actual.versionPolitica)) {
                is ApiResult.Success -> {
                    _uiState.value = actual.copy(enviando = false, yaAceptado = true)
                }

                is ApiResult.Error -> {
                    _uiState.value = if (resultado.code == 401 || resultado.code == 403) {
                        ConsentimientoUiState.SinSesion
                    } else {
                        actual.copy(enviando = false, errorAlEnviar = resultado.message)
                    }
                }
            }
        }
    }

    private companion object {
        const val VERSION_POR_DEFECTO = "1.0"
    }
}
