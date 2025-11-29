package com.example.vidasalud2.features.profile // O .features.connections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// --- IMPORTACIÓN AÑADIDA ---
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataConnectionsScreen(
    navController: NavController
) {
    var googleFitConnected by remember { mutableStateOf(false) }
    var samsungHealthConnected by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Conexión de Datos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Conecta tus apps de salud para sincronizar tus datos automáticamente.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            ConnectionRow(
                text = "Google Fit",
                icon = Icons.Filled.Watch,
                isChecked = googleFitConnected,
                onCheckedChange = { googleFitConnected = it }
            )

            HorizontalDivider()

            ConnectionRow(
                text = "Samsung Health",
                icon = Icons.Filled.PhoneAndroid,
                isChecked = samsungHealthConnected,
                onCheckedChange = { samsungHealthConnected = it }
            )

           HorizontalDivider()
        }
    }
}

@Composable
private fun ConnectionRow(
    text: String,
    icon: ImageVector,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = text, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text, style = MaterialTheme.typography.titleMedium)
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            // --- LÍNEA CORREGIDA ---
            // Ahora la referencia a 'Color' funciona
            colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF6FAF4E))
        )
    }
}