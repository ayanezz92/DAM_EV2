package com.example.vidasalud2.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vidasalud2.R // Reemplaza con tu logo

@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    // Obtenemos el estado actual desde el ViewModel
    val uiState = viewModel.uiState

    // --- Diseño de la Interfaz (View) ---
    // (Basado en Login.jpg)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.vidasalud_logo), // Tu logo
            contentDescription = "Logo VidaSalud",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "VidaSalud",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF6FAF4E) // Color verde
        )

        Spacer(modifier = Modifier.height(48.dp))

        // --- Campo de Usuario ---
        OutlinedTextField(
            value = uiState.username,
            onValueChange = { viewModel.onUsernameChange(it) },
            label = { Text("Usuario o Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = uiState.isUsernameError, // Validación visual
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        if (uiState.isUsernameError) {
            Text("El campo no puede estar vacío", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Campo de Contraseña ---
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = uiState.isPasswordError, // Validación visual
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        if (uiState.isPasswordError) {
            Text("El campo no puede estar vacío", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- Botón de Iniciar Sesión ---
        Button(
            onClick = { viewModel.onLoginClicked(onLoginSuccess) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6FAF4E))
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Iniciar Sesión", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}