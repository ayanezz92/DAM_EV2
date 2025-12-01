package com.example.vidasalud2.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue // Importante para 'by'
import androidx.compose.runtime.setValue // Importante para 'by'
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vidasalud2.R

@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    // Estados de la lógica
    val isLoading by viewModel.isLoading.collectAsState()
    val loginError by viewModel.loginError.collectAsState()
    val loginSuccess by viewModel.loginSuccess.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Colores corporativos (Verde Vida Salud)
    val primaryGreen = Color(0xFF2E7D32)
    val lightGreen = Color(0xFFE8F5E9)

    // Efecto de navegación
    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            onLoginSuccess()
        }
    }

    // --- DISEÑO VISUAL MEJORADO ---
    Box(
        modifier = Modifier
            .fillMaxSize()
            // Fondo degradado elegante
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(lightGreen, Color.White)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 1. LOGO DE LA APP (CORREGIDO)
            Image(
                // Aquí usamos EXACTAMENTE el nombre de tu archivo: logo_app
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "Logo Vida Salud",
                modifier = Modifier.size(120.dp) // Un poco más grande para que luzca
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 2. TEXTO DE BIENVENIDA
            Text(
                text = "¡Bienvenido!",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = primaryGreen
            )
            Text(
                text = "Tu salud, tu mejor proyecto",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 3. CAMPO EMAIL
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo Electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = primaryGreen) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp), // Bordes redondos
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryGreen,
                    focusedLabelColor = primaryGreen,
                    cursorColor = primaryGreen
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 4. CAMPO PASSWORD
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = primaryGreen) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Ver contraseña",
                            tint = Color.Gray
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryGreen,
                    focusedLabelColor = primaryGreen,
                    cursorColor = primaryGreen
                )
            )

            // MENSAJE DE ERROR
            if (loginError != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = loginError ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 5. BOTÓN INGRESAR
            Button(
                onClick = { viewModel.login(email, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !isLoading,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryGreen)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(text = "INGRESAR", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Footer
            Text(
                text = "Versión 1.0 - Vida Salud",
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray
            )
        }
    }
}