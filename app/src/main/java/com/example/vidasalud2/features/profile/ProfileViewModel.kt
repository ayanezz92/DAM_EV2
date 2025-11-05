package com.example.vidasalud2.features.profile


import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProfileUiState(
    val userName: String = "Laura G.",
    val activityStreak: String = "Racha: 30 días de actividad",
    val imageUri: Uri? = null // El URI de la foto seleccionada
)

class ProfileViewModel : ViewModel() {

    var uiState by mutableStateOf(ProfileUiState())
        private set

    // Controla si debemos mostrar el diálogo de selección (Cámara o Galería)
    var showImageSourceDialog by mutableStateOf(false)
        private set

    fun onImageChange(newUri: Uri?) {
        uiState = uiState.copy(imageUri = newUri)
    }

    fun onShowImageDialog() {
        showImageSourceDialog = true
    }

    fun onDismissImageDialog() {
        showImageSourceDialog = false
    }
}