package com.example.vidasalud2.features.home


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// Estado de la UI para la pantalla Home
data class HomeUiState(
    val userName: String = "Laura", // Simulado
    val currentSteps: Int = 8500, // Simulado
    val goalSteps: Int = 10000, // Simulado
    val sleepHours: Float = 7.5f, // Simulado
    val activityMinutes: Int = 60, // Simulado
    val nutritionCalories: Int = 1880 // Simulado
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    // En el futuro, aquí irían funciones para cargar estos datos
    // desde el Repositorio y la base de datos Room.

    // Por ahora, el estado se inicializa con los datos simulados.
}