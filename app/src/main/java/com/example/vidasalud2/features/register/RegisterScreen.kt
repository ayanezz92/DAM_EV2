package com.example.vidasalud2.features.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: RegisterViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // --- INICIO DE LA CORRECCIÓN ---
    // 1. Copiamos el valor a una variable local estable
    val saveMessage = uiState.saveMessage

    // 2. Usamos esa variable local en el LaunchedEffect
    LaunchedEffect(saveMessage) {
        if (saveMessage != null) {
            snackbarHostState.showSnackbar(saveMessage) // 3. Usamos la copia
            viewModel.onSaveMessageShown()
        }
    }
    // --- FIN DE LA CORRECCIÓN ---

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onSaveData() },
                containerColor = Color(0xFF6FAF4E)
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Guardar", modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                Text(
                    text = "Añadir Nuevo Registro",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            item {
                TabRow(selectedTabIndex = uiState.selectedTab.ordinal) {
                    RegisterTab.values().forEach { tab ->
                        Tab(
                            selected = uiState.selectedTab == tab,
                            onClick = { viewModel.onTabSelected(tab) },
                            text = { Text(tab.name) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            when (uiState.selectedTab) {
                RegisterTab.Actividad -> item { ActivityForm(uiState, viewModel) }
                RegisterTab.Alimentacion -> item { FoodForm(uiState, viewModel) }
                RegisterTab.Sueño -> item { SleepForm(uiState, viewModel) }
            }
        }
    }
}

@Composable
fun ActivityForm(uiState: RegisterUiState, viewModel: RegisterViewModel) {
    Column {
        Text("Actividad", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.activityType,
            onValueChange = { viewModel.onActivityTypeChange(it) },
            label = { Text("Tipo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.duration,
            onValueChange = { viewModel.onDurationChange(it) },
            label = { Text("Duración (Minutos)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Intensidad: ${uiState.intensity.toInt()}/5")
        Slider(
            value = uiState.intensity,
            onValueChange = { viewModel.onIntensityChange(it) },
            valueRange = 0f..5f,
            steps = 4,
            colors = SliderDefaults.colors(thumbColor = Color(0xFF6FAF4E), activeTrackColor = Color(0xFF6FAF4E))
        )
    }
}

@Composable
fun FoodForm(uiState: RegisterUiState, viewModel: RegisterViewModel) {
    Column {
        Text("Alimentación", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.foodItem,
            onValueChange = { viewModel.onFoodItemChange(it) },
            label = { Text("Artículo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.foodCalories,
            onValueChange = { viewModel.onFoodCaloriesChange(it) },
            label = { Text("Calorías (kcal)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SleepForm(uiState: RegisterUiState, viewModel: RegisterViewModel) {
    Column {
        Text("Sueño", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.sleepHours,
            onValueChange = { viewModel.onSleepHoursChange(it) },
            label = { Text("Horas de Sueño") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Calidad del Sueño: ${uiState.sleepQuality.toInt()}/5")
        Slider(
            value = uiState.sleepQuality,
            onValueChange = { viewModel.onSleepQualityChange(it) },
            valueRange = 1f..5f,
            steps = 3,
            colors = SliderDefaults.colors(thumbColor = Color(0xFF6FAF4E), activeTrackColor = Color(0xFF6FAF4E))
        )
    }
}

enum class RegisterTab {
    Actividad,
    Alimentacion,
    Sueño
}