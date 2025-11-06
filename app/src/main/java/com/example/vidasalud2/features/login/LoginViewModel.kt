package com.example.vidasalud2.features.login

import android.util.Patterns // Importante para validar email
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    // --- 1. Eventos de cambio de texto ---

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = validateEmail(email)) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = validatePassword(password)) }
    }

    // --- 2. Lógica de Validación ---

    private fun validateEmail(email: String): String? {
        if (email.isBlank()) {
            return "El correo no puede estar vacío"
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Formato de correo no válido"
        }
        return null // Sin error
    }

    private fun validatePassword(password: String): String? {
        if (password.isBlank()) {
            return "La contraseña no puede estar vacía"
        }
        if (password.length < 6) {
            // Puedes cambiar 6 por tu mínimo requerido
            return "La contraseña debe tener al menos 6 caracteres"
        }
        return null // Sin error
    }

    // --- 3. Evento de Click en Login ---

    fun onLoginClick(onLoginSuccess: () -> Unit) {
        // Correr validación por si el usuario no tocó los campos
        val emailError = validateEmail(uiState.value.email)
        val passwordError = validatePassword(uiState.value.password)

        if (emailError != null || passwordError != null) {
            // Mostrar errores y no continuar
            _uiState.update { it.copy(
                emailError = emailError,
                passwordError = passwordError
            )}
            return
        }

        // --- Si la validación es exitosa ---
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, loginError = null) }

            // --- AQUÍ VA TU LÓGICA DE API / FIREBASE ---
            // val success = authRepository.login(uiState.value.email, uiState.value.password)
            // ... simulando una llamada ...
            kotlinx.coroutines.delay(2000)
            val loginExitoso = true // Cambia esto por tu lógica real

            if (loginExitoso) {
                // Navega a la siguiente pantalla
                onLoginSuccess()
            } else {
                _uiState.update { it.copy(
                    isLoading = false,
                    loginError = "Credenciales incorrectas"
                )}
            }
        }
    }
}