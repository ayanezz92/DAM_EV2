package com.example.vidasalud2.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    // Un "flujo" que la vista escuchará.
    // _isAuthenticated será privado, isAuthenticated será público y de solo lectura.
    private val _isAuthenticated = MutableStateFlow<Boolean?>(null) // Null = Cargando
    val isAuthenticated = _isAuthenticated.asStateFlow()

    init {
        // En cuanto el ViewModel se crea, chequea la sesión.
        checkUserSession()
    }

    private fun checkUserSession() {
        viewModelScope.launch {
            // --- Lógica de persistencia ---
            // 1. Aquí llamaremos al Repositorio para que consulte la BD Room.
            // 2. Por AHORA, vamos a simularlo:
            //    - Simulamos 2 segundos de carga.
            delay(2000)
            //    - Simulamos que "no hay sesión activa".
            _isAuthenticated.value = false

            // Si quisieras simular una sesión activa, cambia a:
            // _isAuthenticated.value = true
        }
    }
}