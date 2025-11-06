package com.example.vidasalud2.features.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
// --- IMPORTS AÑADIDOS ---
import androidx.navigation.NavController
import com.example.vidasalud2.navigation.AppScreens

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    navController: NavController // <-- 1. PARÁMETRO AÑADIDO
) {

    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // --- 1. Encabezado (Hola, Laura) ---
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                // ... (el resto de tu Row)
            ) {
                Text(
                    text = "Hola, ${uiState.userName}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // --- 2. Indicador de Pasos (Animado) ---
        item {
            CircularStepIndicator(
                currentValue = uiState.currentSteps,
                goalValue = uiState.goalSteps
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        // --- 3. Tarjetas de Resumen ---
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

        // --- 4. Tarjeta de Recomendación ---
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
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        // --- 2. ACCIÓN DE CLICK MODIFICADA ---
                        onClick = {
                            navController.navigate(AppScreens.RecommendationDetail.route)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6FAF4E))
                    ) {
                        Text("Ver más")
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryCard(title: String, value: String) {
    Card(
        modifier = Modifier.size(width = 110.dp, height = 100.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, style = MaterialTheme.typography.titleSmall)
            Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        }
    }
}