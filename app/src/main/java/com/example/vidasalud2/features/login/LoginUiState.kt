package com.example.vidasalud2.features.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",

    // Errores de validación (null = sin error)
    val emailError: String? = null,
    val passwordError: String? = null,

    // Para mostrar un spinner de carga
    val isLoading: Boolean = false,

    // Para mostrar un error general (ej. "Credenciales incorrectas")
    val loginError: String? = null
)