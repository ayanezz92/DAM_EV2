package com.example.vidasalud2.features.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vidasalud2.data.local.ActivityEntity
import com.example.vidasalud2.data.local.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    // Ahora usa la clase RegisterUiState del otro archivo automáticamente
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onTabSelected(tab: RegisterTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun onSaveData(context: Context) {
        viewModelScope.launch {
            val database = AppDatabase.getDatabase(context)
            val dao = database.activityDao()

            val newActivity = ActivityEntity(
                type = _uiState.value.activityType.ifBlank { "Actividad General" },
                duration = _uiState.value.duration,
                intensity = _uiState.value.intensity
            )

            dao.insertActivity(newActivity)
            println("✅ GUARDADO EN ROOM: $newActivity")

            // Limpiar campos
            _uiState.update { it.copy(activityType = "", duration = "0", intensity = 2.5f) }
        }
    }

    fun onActivityTypeChange(type: String) {
        _uiState.update { it.copy(activityType = type) }
    }

    fun onDurationChange(minutes: String) {
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