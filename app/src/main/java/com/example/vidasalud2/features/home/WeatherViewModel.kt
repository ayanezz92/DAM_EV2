package com.example.vidasalud2.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vidasalud2.data.remote.CurrentWeather
import com.example.vidasalud2.data.remote.WeatherApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val api = WeatherApi.create()

    private val _weatherState = MutableStateFlow<CurrentWeather?>(null)
    val weatherState = _weatherState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        fetchWeather()
    }

    fun fetchWeather() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Coordenadas de Santiago (Puedes cambiarlas por tu ciudad)
                val response = api.getWeather(-33.44, -70.66)
                _weatherState.value = response.currentWeather
            } catch (e: Exception) {
                e.printStackTrace() // Si falla (ej: sin internet)
            } finally {
                _isLoading.value = false
            }
        }
    }
}