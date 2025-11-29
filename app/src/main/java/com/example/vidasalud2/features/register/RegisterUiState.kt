package com.example.vidasalud2.features.register

// 1. Aquí definimos las Pestañas (Enum)
enum class RegisterTab {
    Actividad,
    Alimentacion,
    Sueño
}

// 2. Aquí definimos los Datos (Data Class)
data class RegisterUiState(
    val selectedTab: RegisterTab = RegisterTab.Actividad,
    val activityType: String = "",
    val duration: String = "0",
    val intensity: Float = 2.5f,
    val foodItem: String = ""
)