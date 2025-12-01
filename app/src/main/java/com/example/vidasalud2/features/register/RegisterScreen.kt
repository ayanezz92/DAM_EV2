package com.example.vidasalud2.features.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vidasalud2.data.local.AppDatabase

@Composable
fun RegisterScreen() {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val dao = database.activityDao()
    val viewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModelFactory(dao)
    )

    val message by viewModel.saveMessage.collectAsState()
    LaunchedEffect(message) {
        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearMessage()
        }
    }

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Actividad", "Alimentación", "Sueño")

    // Colores personalizados
    val primaryGreen = Color(0xFF2E7D32) // Verde oscuro elegante
    val bgGray = Color(0xFFF4F6F8)

    Surface(modifier = Modifier.fillMaxSize(), color = bgGray) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                "Nuevo Registro",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = primaryGreen
            )
            Spacer(modifier = Modifier.height(16.dp))

            // --- TABS ESTILIZADOS ---
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.White,
                contentColor = primaryGreen,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = primaryGreen
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title, fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal) },
                        selectedContentColor = primaryGreen,
                        unselectedContentColor = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- TARJETA DE FORMULARIO ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.padding(24.dp)) {
                    when (selectedTabIndex) {
                        0 -> ActivityForm(viewModel, primaryGreen)
                        1 -> FoodForm(viewModel, primaryGreen)
                        2 -> SleepForm(viewModel, primaryGreen)
                    }
                }
            }
        }
    }
}

// --- FORMULARIO DE ACTIVIDAD ---
@Composable
fun ActivityForm(viewModel: RegisterViewModel, color: Color) {
    val name by viewModel.activityName.collectAsState()
    val duration by viewModel.activityDuration.collectAsState()
    val intensity by viewModel.activityIntensity.collectAsState()

    Column {
        IconTitle(text = "Registrar Ejercicio", icon = Icons.Default.FitnessCenter, color = color)

        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.onActivityNameChange(it) },
            label = { Text("Tipo (ej: Correr)") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.FitnessCenter, contentDescription = null) }
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = duration,
            onValueChange = { viewModel.onActivityDurationChange(it) },
            label = { Text("Duración (min)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Timer, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Intensidad: ${intensity.toInt()}/5", color = Color.Gray)
        Slider(
            value = intensity,
            onValueChange = { viewModel.onActivityIntensityChange(it) },
            valueRange = 1f..5f,
            steps = 3,
            colors = SliderDefaults.colors(thumbColor = color, activeTrackColor = color)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.saveActivity() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = color)
        ) {
            Text("Guardar Actividad")
        }
    }
}

// --- FORMULARIO DE ALIMENTACIÓN ---
@Composable
fun FoodForm(viewModel: RegisterViewModel, color: Color) {
    val foodName by viewModel.foodName.collectAsState()
    val calories by viewModel.calories.collectAsState()

    Column {
        IconTitle(text = "Registrar Comida", icon = Icons.Default.Restaurant, color = color)

        OutlinedTextField(
            value = foodName,
            onValueChange = { viewModel.onFoodNameChange(it) },
            label = { Text("Alimento") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Restaurant, contentDescription = null) }
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = calories,
            onValueChange = { viewModel.onCaloriesChange(it) },
            label = { Text("Calorías") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.LocalFireDepartment, contentDescription = null) }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { viewModel.saveFood() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = color)
        ) {
            Text("Guardar Comida")
        }
    }
}

// --- FORMULARIO DE SUEÑO ---
@Composable
fun SleepForm(viewModel: RegisterViewModel, color: Color) {
    val hours by viewModel.sleepHours.collectAsState()
    val quality by viewModel.sleepQuality.collectAsState()

    Column {
        IconTitle(text = "Registrar Sueño", icon = Icons.Default.Bedtime, color = color)

        OutlinedTextField(
            value = hours,
            onValueChange = { viewModel.onSleepHoursChange(it) },
            label = { Text("Horas dormidas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Timer, contentDescription = null) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Calidad: ${if(quality < 2) "Mala" else if (quality < 3) "Regular" else "Buena"}", color = Color.Gray)
        Slider(
            value = quality,
            onValueChange = { viewModel.onSleepQualityChange(it) },
            valueRange = 1f..3f,
            steps = 1,
            colors = SliderDefaults.colors(thumbColor = color, activeTrackColor = color)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.saveSleep() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = color)
        ) {
            Text("Guardar Sueño")
        }
    }
}

@Composable
fun IconTitle(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically, modifier = Modifier.padding(bottom = 16.dp)) {
        Icon(imageVector = icon, contentDescription = null, tint = color)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.titleLarge, color = color)
    }
}