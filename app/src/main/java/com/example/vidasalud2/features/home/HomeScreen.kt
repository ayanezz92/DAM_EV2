package com.example.vidasalud2.features.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun HomeScreen(
    navController: NavController, // Navegación
    viewModel: HomeViewModel = viewModel() // ViewModel Principal
) {
    // Estado de los datos del Home (Pasos, sueño, etc.)
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // --- 1. ENCABEZADO (Hola, Usuario) ---
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Hola, ${uiState.userName}",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // --- 2. TARJETA DEL CLIMA (API EXTERNA) ---
        item {
            WeatherCard() // <--- ¡AQUÍ INTEGRÁMOS EL CLIMA!
            Spacer(modifier = Modifier.height(24.dp))
        }

        // --- 3. INDICADOR DE PASOS (CIRCULAR) ---
        item {
            // Nota: Asegúrate de tener este Composable creado en tu proyecto.
            // Si te da error rojo, avísame y te paso el código del CircularStepIndicator.
            CircularStepIndicator(
                currentValue = uiState.currentSteps,
                goalValue = uiState.goalSteps
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        // --- 4. TARJETAS DE RESUMEN (Sueño, Actividad, Nutrición) ---
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryCard(title = "Sueño", value = "${uiState.sleepHours}h")
                SummaryCard(title = "Actividad", value = "${uiState.activityMinutes}m")
                SummaryCard(title = "Nutrición", value = "${uiState.nutritionCalories} Cal")
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // --- 5. TARJETA DE RECOMENDACIÓN ---
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F8EC)) // Verde muy claro
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Recomendación Destacada:\nBebe 2L de agua diarios",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Button(
                        onClick = {
                            navController.navigate(AppScreens.RecommendationDetail.route)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6FAF4E))
                    ) {
                        Text("Ver más")
                    }
                }
            }
            // Espacio extra al final para que no lo tape el menú de abajo
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// ==========================================
// COMPONENTES AUXILIARES
// ==========================================

@Composable
fun WeatherCard(viewModel: WeatherViewModel = viewModel()) {
    val weather by viewModel.weatherState.collectAsState()
    val loading by viewModel.isLoading.collectAsState()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA)), // Cian clarito
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Clima en Santiago",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF006064)
                )

                if (loading) {
                    Text(text = "Cargando...", style = MaterialTheme.typography.bodySmall)
                } else {
                    weather?.let {
                        Text(
                            text = "${it.temperature}°C",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF006064)
                        )
                        Text(
                            text = "Viento: ${it.windspeed} km/h",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    } ?: Text("Sin conexión", color = Color.Red)
                }
            }

            // Icono de Sol
            Icon(
                imageVector = Icons.Filled.WbSunny,
                contentDescription = "Clima",
                modifier = Modifier.size(56.dp),
                tint = Color(0xFFFBC02D) // Amarillo Sol
            )
        }
    }
}

@Composable
fun SummaryCard(title: String, value: String) {
    Card(
        modifier = Modifier.size(width = 100.dp, height = 100.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
    }
}