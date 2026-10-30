package org.example.carnetizaciontabio.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.carnetizaciontabio.core.ApiResult
import org.example.carnetizaciontabio.domain.usecase.LoginTrabajador

/*
  Estado y logica de la pantalla de inicio de sesion.
  Valida las credenciales contra el backend y notifica cuando la sesion queda abierta.
 */
class LoginViewModel(
    private val loginTrabajador: LoginTrabajador
) : ViewModel() {

    var cedula by mutableStateOf("")
        private set
    var pin by mutableStateOf("")
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set

    // Se activa una sola vez, cuando el login fue exitoso.
    var sesionIniciada by mutableStateOf(false)
        private set

    fun onCedulaChange(valor: String) {
        cedula = valor
        errorMessage = null
    }

    fun onPinChange(valor: String) {
        pin = valor
        errorMessage = null
    }

    /*
      Valida las credenciales. Si son correctas, la sesion queda guardada
      en memoria y [sesionIniciada] pasa a true.
     */
    fun ingresar() {
        if (isLoading) return
        viewModelScope.launch {
            errorMessage = null
            isLoading = true
            when (val resultado = loginTrabajador(cedula, pin)) {
                is ApiResult.Success -> sesionIniciada = true
                is ApiResult.Error -> errorMessage = resultado.message
            }
            isLoading = false
        }
    }

    /*
      Marca la navegacion como consumida. Se llama despues de navegar a Home
      para que el evento no se repita al volver a esta pantalla.
     */
    fun onSesionConsumida() {
        sesionIniciada = false
        cedula = ""
        pin = ""
        errorMessage = null
    }


}
