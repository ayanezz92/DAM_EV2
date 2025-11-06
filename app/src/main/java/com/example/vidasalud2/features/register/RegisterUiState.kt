package com.example.vidasalud2.features.register

data class RegisterUiState(
    // Estado para los Tabs
    val selectedTab: RegisterTab = RegisterTab.Actividad,

    // Estado para el formulario de Actividad
    val activityType: String = "",
    val duration: String = "",
    val intensity: Float = 0f,

    // --- CAMPOS AÑADIDOS para Alimentación ---
    val foodItem: String = "",
    val foodCalories: String = "", // Nuevo

    // --- CAMPOS AÑADIDOS para Sueño ---
    val sleepHours: String = "",  // Nuevo
    val sleepQuality: Float = 1f, // Nuevo (1f a 5f)

    // --- ESTADO PARA EL BOTÓN GUARDAR ---
    val isSaving: Boolean = false,         // Nuevo
    val saveMessage: String? = null      // Nuevo
)