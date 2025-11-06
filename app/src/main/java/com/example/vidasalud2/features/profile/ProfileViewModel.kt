package com.example.vidasalud2.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    // --- Lógica de Cerrar Sesión ---
    fun onLogoutClick() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            delay(1000) // Simulación

            _uiState.update { it.copy(isLoading = false, navigateToLogin = true) }
        }
    }

    // Resetea el trigger después de navegar
    fun onLogoutComplete() {
        _uiState.update { it.copy(navigateToLogin = false) }
    }
}