package com.example.vidasalud2.features.register

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

    // --- ESCUCHAR MENSAJES DE CONFIRMACIÓN ---
    val message by viewModel.saveMessage.collectAsState()
    LaunchedEffect(message) {
        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearMessage()
        }
    }

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Actividad", "Alimentación", "Sueño")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Registro Diario", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // --- AQUÍ CONECTAMOS LOS 3 FORMULARIOS ---
        when (selectedTabIndex) {
            0 -> ActivityForm(viewModel) // ¡Aquí está el que faltaba!
            1 -> FoodForm(viewModel)
            2 -> SleepForm(viewModel)
        }
    }
}

// --- 1. FORMULARIO DE ACTIVIDAD (RECUPERADO) ---
@Composable
fun ActivityForm(viewModel: RegisterViewModel) {
    val name by viewModel.activityName.collectAsState()
    val duration by viewModel.activityDuration.collectAsState()
    val intensity by viewModel.activityIntensity.collectAsState()

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { viewModel.onActivityNameChange(it) },
            label = { Text("Tipo de Actividad (Ej: Correr)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = duration,
            onValueChange = { viewModel.onActivityDurationChange(it) },
            label = { Text("Duración (minutos)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Intensidad: ${intensity.toInt()}")
        Slider(
            value = intensity,
            onValueChange = { viewModel.onActivityIntensityChange(it) },
            valueRange = 1f..5f,
            steps = 3
        )

        Button(onClick = { viewModel.saveActivity() }, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar Actividad")
        }
    }
}

// --- 2. FORMULARIO DE ALIMENTACIÓN ---
@Composable
fun FoodForm(viewModel: RegisterViewModel) {
    val foodName by viewModel.foodName.collectAsState()
    val calories by viewModel.calories.collectAsState()

    Column {
        OutlinedTextField(
            value = foodName,
            onValueChange = { viewModel.onFoodNameChange(it) },
            label = { Text("¿Qué comiste?") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = calories,
            onValueChange = { viewModel.onCaloriesChange(it) },
            label = { Text("Calorías") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.saveFood() }, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar Comida")
        }
    }
}

// --- 3. FORMULARIO DE SUEÑO ---
@Composable
fun SleepForm(viewModel: RegisterViewModel) {
    val hours by viewModel.sleepHours.collectAsState()
    val quality by viewModel.sleepQuality.collectAsState()

    Column {
        OutlinedTextField(
            value = hours,
            onValueChange = { viewModel.onSleepHoursChange(it) },
            label = { Text("Horas dormidas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Calidad: ${if(quality < 2) "Mala" else if (quality < 3) "Regular" else "Buena"}")
        Slider(
            value = quality,
            onValueChange = { viewModel.onSleepQualityChange(it) },
            valueRange = 1f..3f,
            steps = 1
        )

        Button(onClick = { viewModel.saveSleep() }, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar Sueño")
        }
    }
}