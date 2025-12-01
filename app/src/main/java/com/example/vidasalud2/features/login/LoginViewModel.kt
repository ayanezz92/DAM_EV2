package com.example.vidasalud2.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    // 1. Estados que la pantalla está escuchando
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError = _loginError.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess = _loginSuccess.asStateFlow()

    // 2. Función para intentar loguearse
    fun login(email: String, pass: String) {
        viewModelScope.launch {
            // Validaciones simples
            if (email.isBlank() || pass.isBlank()) {
                _loginError.value = "Por favor, llena todos los campos."
                return@launch
            }

            // Simulamos carga de red (2 segundos)
            _isLoading.value = true
            _loginError.value = null // Limpiamos errores previos
            delay(2000)

            // Simulamos lógica de éxito
            // (Aquí podrías conectar con Firebase o tu Backend en el futuro)
            if (pass.length >= 4) { // Ejemplo: si la contraseña tiene más de 4 letras, pasa
                _isLoading.value = false
                _loginSuccess.value = true
            } else {
                _isLoading.value = false
                _loginError.value = "Contraseña incorrecta (usa min 4 caracteres)"
            }
        }
    }
}