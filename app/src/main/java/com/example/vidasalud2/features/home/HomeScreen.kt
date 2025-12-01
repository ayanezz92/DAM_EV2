package com.example.vidasalud2.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.vidasalud2.R
import com.example.vidasalud2.navigation.AppScreens

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF4F6F8)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
        ) {

            // --- 1. CABECERA ---
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // LOGO: Código limpio sin try-catch
                    // Si tienes el archivo 'logo.png' en drawable, esto funcionará.
                    Image(
                        painter = painterResource(id = R.drawable.app_logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(60.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "Hola, ${uiState.userName}!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Vamos por tus metas hoy",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }

            // --- 2. CLIMA ---
            item {
                WeatherCard()
                Spacer(modifier = Modifier.height(24.dp))
            }

            // --- 3. INDICADOR DE PASOS ---
            item {
                Text(
                    text = "Tu Objetivo Diario",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                )

                Box(contentAlignment = Alignment.Center) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 4.dp,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        CircularStepIndicator(
                            currentValue = uiState.currentSteps,
                            goalValue = uiState.goalSteps
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            // --- 4. RESUMEN ---
            item {
                Text(
                    text = "Resumen del día",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SummaryCard(title = "Sueño", value = "${uiState.sleepHours}h", color = Color(0xFFFFF9C4))
                    SummaryCard(title = "Actividad", value = "${uiState.activityMinutes}m", color = Color(0xFFE3F2FD))
                    SummaryCard(title = "Calorías", value = "${uiState.nutritionCalories}", color = Color(0xFFE8F5E9))
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // --- 5. RECOMENDACIÓN ---
            item {
                Text(
                    text = "Tip Saludable",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Hidratación",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF6FAF4E),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Bebe 2L de agua diarios para mantenerte activo.",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                        Button(
                            onClick = { navController.navigate(AppScreens.RecommendationDetail.route) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6FAF4E)),
                            contentPadding = PaddingValues(horizontal = 12.dp)
                        ) {
                            Text("Ver", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

// ------------------------------------
// Componentes Auxiliares
// ------------------------------------

@Composable
fun WeatherCard(viewModel: WeatherViewModel = viewModel()) {
    val weather by viewModel.weatherState.collectAsState()
    val loading by viewModel.isLoading.collectAsState()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0288D1)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Santiago, Chile",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
                if (loading) {
                    Text(text = "...", color = Color.White)
                } else {
                    weather?.let {
                        Text(
                            text = "${it.temperature}°",
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Viento: ${it.windspeed} km/h",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    } ?: Text("Offline", color = Color.White)
                }
            }
            Icon(
                imageVector = Icons.Filled.WbSunny,
                contentDescription = "Clima",
                modifier = Modifier.size(64.dp),
                tint = Color(0xFFFFEB3B)
            )
        }
    }
}

@Composable
fun SummaryCard(title: String, value: String, color: Color) {
    Card(
        modifier = Modifier
            .width(105.dp)
            .height(90.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(text = title, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
    }
}