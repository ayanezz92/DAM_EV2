package com.example.vidasalud2.features.register

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
// Asegúrate de importar tu ViewModel y UiState si están en otro paquete
import com.example.vidasalud2.features.register.RegisterViewModel
import com.example.vidasalud2.features.register.RegisterUiState
import com.example.vidasalud2.features.register.RegisterTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: RegisterViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onSaveData() },
                containerColor = Color(0xFF6FAF4E)
            ) {
                // (Icono de Guardar/Check)
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
                TabRow(selectedTabIndex = uiState.selectedTab.ordinal) {
                    // Asumiendo que RegisterTab es un enum: enum class RegisterTab { Actividad, Alimentacion, Sueño }
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

            // --- Contenido del Formulario (basado en el Tab) ---
            when (uiState.selectedTab) {
                RegisterTab.Actividad -> item { ActivityForm(uiState, viewModel) } // Pasamos el viewModel
                RegisterTab.Alimentacion -> item { FoodForm(uiState, viewModel) } // Pasamos el viewModel
                RegisterTab.Sueño -> item { SleepForm() } // (Pasar viewModel si es necesario)
            }
        }
    }
}

// --- Composable para el formulario de Actividad ---
@Composable
fun ActivityForm(uiState: RegisterUiState, viewModel: RegisterViewModel) {
    Column {
        Text("Actividad", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.activityType,
            onValueChange = { viewModel.onActivityTypeChange(it) }, // ¡CORREGIDO!
            label = { Text("Tipo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.duration, // Asumimos que duration es un String en UiState
            onValueChange = { viewModel.onDurationChange(it) }, // ¡CORREGIDO!
            label = { Text("Duración (Minutos)") },
            modifier = Modifier.fillMaxWidth()
            // (Considera usar KeyboardOptions para un teclado numérico)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Intensidad: ${uiState.intensity.toInt()}/5") // Mostramos valor
        Slider(
            value = uiState.intensity,
            onValueChange = { viewModel.onIntensityChange(it) }, // ¡CORREGIDO!
            valueRange = 0f..5f, // Rango definido
            steps = 4, // 5 pasos (0, 1, 2, 3, 4, 5)
            colors = SliderDefaults.colors(thumbColor = Color(0xFF6FAF4E), activeTrackColor = Color(0xFF6FAF4E))
        )
    }
}

// --- Composable para el formulario de Alimentación ---
@Composable
fun FoodForm(uiState: RegisterUiState, viewModel: RegisterViewModel) {
    Column {
        Text("Alimentación", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.foodItem,
            onValueChange = { viewModel.onFoodItemChange(it) }, // ¡CORREGIDO!
            label = { Text("Artículo") },
            modifier = Modifier.fillMaxWidth()
        )
        // (Resto de campos: Porción, Hora)
    }
}

// --- Composable para el formulario de Sueño (Placeholder) ---
@Composable
fun SleepForm() {
    Text("Formulario de Sueño (A construir)")
}

// --- (Asegúrate de tener esta enum class, puede ir en este archivo o en otro) ---
enum class RegisterTab {
    Actividad,
    Alimentacion,
    Sueño
}

// (El UiState y ViewModel de ejemplo se omiten ya que están en sus propios archivos)