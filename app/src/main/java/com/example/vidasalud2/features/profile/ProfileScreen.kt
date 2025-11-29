package com.example.vidasalud2.features.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera // El import correcto
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vidasalud2.navigation.AppScreens

@Composable
fun ProfileScreen(
    mainNavController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Lógica para el "Cerrar Sesión" (Esto sí funciona)
    LaunchedEffect(uiState.navigateToLogin) {
        if (uiState.navigateToLogin) {
            mainNavController.navigate(AppScreens.Login.route) {
                popUpTo(AppScreens.Home.route) { inclusive = true }
            }
            viewModel.onLogoutComplete()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Sección de Info de Usuario ---
        Spacer(modifier = Modifier.height(32.dp))

        // --- Botón de Cámara (onClick vacío) ---
        IconButton(
            onClick = { /* TODO: Implementar más tarde */ },
            modifier = Modifier.size(120.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.PhotoCamera,
                contentDescription = "Cambiar foto de perfil",
                modifier = Modifier.fillMaxSize(0.7f),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(uiState.userName, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(uiState.activityStreak, fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(32.dp))

        // --- Botones de Navegación ---
        ProfileRowItem(
            text = "Mi Suscripción",
            onClick = { mainNavController.navigate(AppScreens.Subscription.route) }
        )
        ProfileRowItem(
            text = "Conexión de Datos Externos",
            onClick = { mainNavController.navigate(AppScreens.DataConnections.route) }
        )
        ProfileRowItem(
            text = "Preferencias de Notificación",
            onClick = { mainNavController.navigate(AppScreens.Notifications.route) }
        )
        ProfileRowItem(
            text = "Ayuda y Soporte",
            onClick = { mainNavController.navigate(AppScreens.Help.route) }
        )
        ProfileRowItem(
            text = "Configuración de Publicidad",
            onClick = { mainNavController.navigate(AppScreens.AdSettings.route) }
        )

        Spacer(modifier = Modifier.weight(1f))

        // --- Botón de Cerrar Sesión ---
        Button(
            onClick = { viewModel.onLogoutClick() },
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6FAF4E))
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
            } else {
                Text("Cerrar Sesión", fontSize = 16.sp)
            }
        }
    }
}

// Composable de ayuda para las filas
@Composable
private fun ProfileRowItem(text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        HorizontalDivider(color = Color.LightGray)
    }
}