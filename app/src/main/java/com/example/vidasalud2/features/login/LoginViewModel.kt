package com.example.vidasalud2.features.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// Esta clase contiene el ESTADO de la UI del Login
data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isUsernameError: Boolean = false, // Para validación
    val isPasswordError: Boolean = false, // Para validación
    val isLoading: Boolean = false
)

class LoginViewModel : ViewModel() {

    // El estado de la UI (privado)
    var uiState by mutableStateOf(LoginUiState())
        private set

    // Evento que la Vista puede llamar
    fun onUsernameChange(username: String) {
        uiState = uiState.copy(username = username, isUsernameError = false)
    }

    // Evento que la Vista puede llamar
    fun onPasswordChange(password: String) {
        uiState = uiState.copy(password = password, isPasswordError = false)
    }

    // Lógica de validación
    private fun validateFields(): Boolean {
        // Tu regla: "Solo no puede estar vacío"
        val isUsernameValid = uiState.username.isNotBlank()
        val isPasswordValid = uiState.password.isNotBlank()

        uiState = uiState.copy(
            isUsernameError = !isUsernameValid,
            isPasswordError = !isPasswordValid
        )

        return isUsernameValid && isPasswordValid
    }

    // Evento que la Vista llama al pulsar el botón
    fun onLoginClicked(onLoginSuccess: () -> Unit) {
        if (validateFields()) {
            // Si la validación es correcta...
            viewModelScope.launch {
                uiState = uiState.copy(isLoading = true)

                // --- Lógica de Autenticación ---
                // 1. Aquí llamaríamos al Repositorio para verificar en la BD.
                // 2. Por AHORA, simulamos un éxito.
                // 3. (En el futuro, si el login falla, mostraríamos un Toast o Snackbar)

                uiState = uiState.copy(isLoading = false)

                // 4. Llamamos a la función lambda para navegar
                onLoginSuccess()
            }
        }
    }
}