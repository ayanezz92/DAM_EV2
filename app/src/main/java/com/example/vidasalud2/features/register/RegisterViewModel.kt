package com.example.vidasalud2.features.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vidasalud2.data.local.ActivityDao
import com.example.vidasalud2.data.local.ActivityEntity
import com.example.vidasalud2.data.local.FoodEntity
import com.example.vidasalud2.data.local.SleepEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val dao: ActivityDao) : ViewModel() {

    // --- MENSAJES (TOAST) ---
    private val _saveMessage = MutableStateFlow<String?>(null)
    val saveMessage = _saveMessage.asStateFlow()

    fun clearMessage() { _saveMessage.value = null }

    // ===========================
    // 1. LÓGICA DE ACTIVIDAD
    // ===========================
    private val _activityName = MutableStateFlow("")
    val activityName = _activityName.asStateFlow()

    private val _activityDuration = MutableStateFlow("")
    val activityDuration = _activityDuration.asStateFlow()

    private val _activityIntensity = MutableStateFlow(1f)
    val activityIntensity = _activityIntensity.asStateFlow()

    fun onActivityNameChange(text: String) { _activityName.value = text }
    fun onActivityDurationChange(text: String) { _activityDuration.value = text }
    fun onActivityIntensityChange(value: Float) { _activityIntensity.value = value }

    fun saveActivity() {
        // --- VALIDACIÓN: Si está vacío, no guardar ---
        if (_activityName.value.isBlank() || _activityDuration.value.isBlank()) {
            _saveMessage.value = "Error: Debes ingresar nombre y duración"
            return // Se detiene aquí y no guarda
        }

        viewModelScope.launch {
            try {
                val activity = ActivityEntity(
                    type = _activityName.value,
                    duration = _activityDuration.value,
                    intensity = _activityIntensity.value
                )
                dao.insertar(activity)

                // Limpiar y avisar
                _activityName.value = ""
                _activityDuration.value = ""
                _activityIntensity.value = 1f
                _saveMessage.value = "¡Actividad guardada correctamente!"
            } catch (e: Exception) {
                _saveMessage.value = "Error al guardar: ${e.message}"
            }
        }
    }

    // ===========================
    // 2. LÓGICA DE ALIMENTACIÓN
    // ===========================
    private val _foodName = MutableStateFlow("")
    val foodName = _foodName.asStateFlow()

    private val _calories = MutableStateFlow("")
    val calories = _calories.asStateFlow()

    fun onFoodNameChange(text: String) { _foodName.value = text }
    fun onCaloriesChange(text: String) { _calories.value = text }

    fun saveFood() {
        // --- VALIDACIÓN ---
        if (_foodName.value.isBlank() || _calories.value.isBlank()) {
            _saveMessage.value = "Error: Ingresa el alimento y sus calorías"
            return
        }

        viewModelScope.launch {
            try {
                val food = FoodEntity(
                    foodName = _foodName.value,
                    calories = _calories.value.toIntOrNull() ?: 0
                )
                dao.insertFood(food)
                _foodName.value = ""
                _calories.value = ""
                _saveMessage.value = "¡Comida guardada correctamente!"
            } catch (e: Exception) {
                _saveMessage.value = "Error: ${e.message}"
            }
        }
    }

    // ===========================
    // 3. LÓGICA DE SUEÑO
    // ===========================
    private val _sleepHours = MutableStateFlow("")
    val sleepHours = _sleepHours.asStateFlow()

    private val _sleepQuality = MutableStateFlow(2f)
    val sleepQuality = _sleepQuality.asStateFlow()

    fun onSleepHoursChange(text: String) { _sleepHours.value = text }
    fun onSleepQualityChange(value: Float) { _sleepQuality.value = value }

    fun saveSleep() {
        // --- VALIDACIÓN ---
        if (_sleepHours.value.isBlank()) {
            _saveMessage.value = "Error: Indica cuántas horas dormiste"
            return
        }

        viewModelScope.launch {
            try {
                val qualityText = when(_sleepQuality.value.toInt()) {
                    1 -> "Mala"
                    2 -> "Regular"
                    else -> "Buena"
                }
                val sleep = SleepEntity(
                    hours = _sleepHours.value.toFloatOrNull() ?: 0f,
                    quality = qualityText
                )
                dao.insertSleep(sleep)
                _sleepHours.value = ""
                _sleepQuality.value = 2f
                _saveMessage.value = "¡Sueño guardado correctamente!"
            } catch (e: Exception) {
                _saveMessage.value = "Error: ${e.message}"
            }
        }
    }
}

class RegisterViewModelFactory(private val dao: ActivityDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}