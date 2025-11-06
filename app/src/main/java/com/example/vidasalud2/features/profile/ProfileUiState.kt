package com.example.vidasalud2.features.profile

// ¡Ya no necesitamos 'import android.net.Uri'!

data class ProfileUiState(
    val userName: String = "Laura G.",
    val activityStreak: String = "Racha: 30 días de actividad",
    val isLoading: Boolean = false,
    val navigateToLogin: Boolean = false

    // El campo 'profileImageUri: Uri?' se ha eliminado.
)