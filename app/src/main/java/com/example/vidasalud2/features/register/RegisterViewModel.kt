package com.example.vidasalud2.features.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    // --- Eventos Comunes ---
    fun onTabSelected(tab: RegisterTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun onSaveMessageShown() {
        _uiState.update { it.copy(saveMessage = null) }
    }

    // --- Eventos de Actividad ---
    fun onActivityTypeChange(newType: String) {
        _uiState.update { it.copy(activityType = newType) }
    }
    fun onDurationChange(newDuration: String) {
        _uiState.update { it.copy(duration = newDuration) }
    }
    fun onIntensityChange(newIntensity: Float) {
        _uiState.update { it.copy(intensity = newIntensity) }
    }

    // --- Eventos de Alimentación (Actualizados) ---
    fun onFoodItemChange(newFood: String) {
        _uiState.update { it.copy(foodItem = newFood) }
    }
    fun onFoodCaloriesChange(newCalories: String) {
        _uiState.update { it.copy(foodCalories = newCalories) }
    }

    // --- Eventos de Sueño (Nuevos) ---
    fun onSleepHoursChange(newHours: String) {
        _uiState.update { it.copy(sleepHours = newHours) }
    }
    fun onSleepQualityChange(newQuality: Float) {
        _uiState.update { it.copy(sleepQuality = newQuality) }
    }

    // --- ¡LÓGICA DE GUARDADO AÑADIDA! ---
    fun onSaveData() {
        if (_uiState.value.isSaving) return // Evitar doble click

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }

            // Simulamos un guardado en la red o base de datos
            when (_uiState.value.selectedTab) {
                RegisterTab.Actividad -> {
                    Log.d("RegisterViewModel", "Guardando Actividad: ${uiState.value.activityType}")
                }
                RegisterTab.Alimentacion -> {
                    Log.d("RegisterViewModel", "Guardando Comida: ${uiState.value.foodItem}")
                }
                RegisterTab.Sueño -> {
                    Log.d("RegisterViewModel", "Guardando Sueño: ${uiState.value.sleepHours}h")
                }
            }
            delay(1500) // Simulación de red

            // Reseteamos campos y mostramos mensaje
            _uiState.update {
                it.copy(
                    isSaving = false,
                    saveMessage = "¡Registro guardado exitosamente!",
                    // Reseteamos los campos
                    activityType = "",
                    duration = "",
                    intensity = 0f,
                    foodItem = "",
                    foodCalories = "",
                    sleepHours = "",
                    sleepQuality = 1f
                )
            }
        }
    }
}