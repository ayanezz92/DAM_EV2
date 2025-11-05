package com.vidasalud.features.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vidasalud2.navigation.AppScreens
import com.example.vidasalud2.R // Asegúrate de tener tu logo en res/drawable
import com.example.vidasalud2.features.splash.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = viewModel()
) {
    // 1. Escuchamos el estado de autenticación del ViewModel
    val isAuthenticated by viewModel.isAuthenticated.collectAsStateWithLifecycle()

    // 2. Efecto que se lanza UNA VEZ cuando el estado cambia de null
    LaunchedEffect(key1 = isAuthenticated) {
        if (isAuthenticated != null) { // Si ya no está cargando
            // Navegamos según el resultado
            if (isAuthenticated == true) {
                // Opción A: Usuario autenticado -> Va a Home
                navController.navigate(AppScreens.Home.route) {
                    popUpTo(AppScreens.Splash.route) { inclusive = true }
                }
            } else {
                // Opción B: Usuario NO autenticado -> Va a Login
                navController.navigate(AppScreens.Login.route) {
                    popUpTo(AppScreens.Splash.route) { inclusive = true }
                }
            }
        }
    }

    // 3. Animación para la barra de progreso
    var progress by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = "SplashProgress"
    )

    // Efecto para simular la carga (visual)
    LaunchedEffect(key1 = true) {
        delay(500) // Pequeña espera
        progress = 1f
    }

    // --- Diseño de la Interfaz (View) ---
    // (Basado en Pantalla De Carga.jpg)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.vidasalud_logo), // Reemplaza con tu logo
                contentDescription = "Logo VidaSalud",
                modifier = Modifier.size(150.dp) // Ajusta el tamaño
            )

            Spacer(modifier = Modifier.height(24.dp))

            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                color = Color(0xFF6FAF4E), // Color verde de tu marca
                trackColor = Color.LightGray
            )
        }
    }
}