package com.example.vidasalud2.features.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Definimos el UiState aquí (puedes moverlo a un archivo separado si quieres)
// Este data class coincide con lo que tu RegisterScreen.kt espera
data class RegisterUiState(
    val selectedTab: RegisterTab = RegisterTab.Actividad,

    // --- Campos de Actividad ---
    val activityType: String = "",
    val duration: String = "0", // Usamos String como en tu nuevo código
    val intensity: Float = 2.5f, // Usamos Float con rango 0-5

    // --- Campos de Alimentación ---
    val foodItem: String = ""
    // (Puedes añadir más campos de formulario aquí)
)

// El ViewModel que contiene toda la lógica
class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onTabSelected(tab: RegisterTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun onSaveData() {
        // TODO: Aquí irá la lógica para guardar en la BD Room
        // Por ahora, solo imprimimos en la consola (simulación)
        println("Datos guardados (simulado): ${uiState.value}")
    }

    // --- Funciones que tu RegisterScreen.kt necesita ---

    fun onActivityTypeChange(type: String) {
        _uiState.update { it.copy(activityType = type) }
    }

    fun onDurationChange(minutes: String) {
        // Opcional: Filtramos para asegurarnos que solo sean números
        if (minutes.all { it.isDigit() } || minutes.isEmpty()) {
            _uiState.update { it.copy(duration = minutes) }
        }
    }

    fun onIntensityChange(newIntensity: Float) {
        _uiState.update { it.copy(intensity = newIntensity) }
    }

    fun onFoodItemChange(food: String) {
        _uiState.update { it.copy(foodItem = food) }
    }
}