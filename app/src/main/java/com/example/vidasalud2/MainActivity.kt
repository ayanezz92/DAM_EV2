package com.example.vidasalud2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.vidasalud2.navigation.AppNavigation
import com.example.vidasalud2.notifications.NotificationHelper // Import de notificaciones
import com.example.vidasalud2.ui.theme.VidaSalud2Theme
import com.example.vidasalud2.ui.theme.VidaSalud2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- LÍNEA AÑADIDA ---
        // Crear el canal de notificación tan pronto como la app inicie
        NotificationHelper(this).createNotificationChannel()
        // ---------------------

        setContent {
            VidaSalud2Theme()  { // Tu tema de Compose
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // ¡Aquí llamamos a nuestro navegador!
                    AppNavigation()
                }
            }
        }
    }
}