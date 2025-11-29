package com.example.vidasalud2.features.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: RegisterViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onSaveData(context) },
                containerColor = Color(0xFF6FAF4E)
            ) {
                Text("Guardar", modifier = Modifier.padding(horizontal = 16.dp))
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

            // --- Selector de Tabs ---
            item {
                // AQUÍ ESTABA EL ERROR: Asegúrate de que 'ordinal' se refiere a la propiedad del enum
                TabRow(selectedTabIndex = uiState.selectedTab.ordinal) {
                    // Iteramos sobre los valores del ENUM
                    RegisterTab.values().forEach { tab ->
                        Tab(
                            selected = uiState.selectedTab == tab,
                            onClick = { viewModel.onTabSelected(tab) },
                            text = { Text(tab.name) } // 'name' es propiedad del enum
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // --- Contenido del Formulario ---
            when (uiState.selectedTab) {
                RegisterTab.Actividad -> item { ActivityForm(uiState, viewModel) }
                RegisterTab.Alimentacion -> item { FoodForm(uiState, viewModel) }
                RegisterTab.Sueño -> item { SleepForm() }
            }
        }
    }
}

// ... (El resto de funciones ActivityForm, FoodForm, SleepForm quedan igual)
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
    }
}

@Composable
fun SleepForm() {
    Text("Formulario de Sueño (A construir)")
}