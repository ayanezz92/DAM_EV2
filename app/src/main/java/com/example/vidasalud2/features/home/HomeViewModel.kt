package com.example.vidasalud2.features.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// Estado de la UI para la pantalla Home
data class HomeUiState(
    val userName: String = "Benja", // Cambiado para tu demo
    val currentSteps: Int = 6500,   // Pasos actuales simulados
    val goalSteps: Int = 10000,     // Meta de pasos
    val sleepHours: Float = 6.5f,   // Horas de sueño
    val activityMinutes: Int = 45,  // Minutos de ejercicio
    val nutritionCalories: Int = 1880 // Calorías consumidas
)

class HomeViewModel : ViewModel() {

    // Usamos StateFlow para que la UI se actualice automáticamente si cambiamos estos datos
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    // NOTA: En el futuro, aquí agregaremos una función "loadUserData()"
    // que llamará a Room o a la API para reemplazar estos datos falsos por los reales.
}