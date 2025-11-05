package com.example.vidasalud2.features.analysis


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Y AÑADIRLA a `BottomNavGraph.kt`
// composable(route = BottomNavScreen.Analysis.route) { // <-- ¡Debes añadir esta ruta!
//     AnalysisScreen()
// }

@Composable
fun AnalysisScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Análisis y Metas",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // --- Gráfico Simulado 1 ---
        item {
            ChartCard(title = "Sueño Semanal")
            Spacer(modifier = Modifier.height(16.dp))
        }

        // --- Gráfico Simulado 2 ---
        item {
            ChartCard(title = "Pasos Promedio Mensual")
            Spacer(modifier = Modifier.height(24.dp))
        }

        // --- Recomendaciones (Simuladas) ---
        item {
            Text(
                text = "Recomendaciones",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                RecommendationCard(
                    text = "¡Genial! Mantén tu racha de 7 días durmiendo más de 7 horas.",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                RecommendationCard(
                    text = "Bebe 2L agua para mejorar tu hidratación.",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun ChartCard(title: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp), // Altura fija para el gráfico
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            // (Aquí iría el componente real del gráfico, ej: LineChart)
            // Por ahora, simulamos con un Box
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("Simulación de Gráfico", color = Color.Gray)
            }
        }
    }
}

@Composable
fun RecommendationCard(text: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(150.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F8EC)) // Verde claro
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
            Text(text)
        }
    }
}