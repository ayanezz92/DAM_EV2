package com.example.vidasalud2.features.analysis

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vidasalud2.data.local.ActivityEntity
import com.example.vidasalud2.data.local.AppDatabase
import com.example.vidasalud2.data.local.FoodEntity
import com.example.vidasalud2.data.local.SleepEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen() {
    val context = LocalContext.current
    val dao = remember { AppDatabase.getDatabase(context).activityDao() }

    val activities by dao.obtenerTodas().collectAsState(initial = emptyList())
    val foods by dao.getAllFood().collectAsState(initial = emptyList())
    val sleeps by dao.getAllSleep().collectAsState(initial = emptyList())

    // 1. Column principal ocupa TODA la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // TÍTULO FIJO (NO SCROLLEA)
        Text(
            text = "Tu Historial",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Desliza para ver más",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. LISTA SCROLLEABLE (OCUPA EL RESTO DEL ESPACIO)
        LazyColumn(
            modifier = Modifier
                .weight(1f) // Esto es vital para el scroll
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 100.dp) // Espacio extra abajo
        ) {

            // SECCIÓN ACTIVIDAD
            item { SectionHeader("Actividad Física", Icons.Default.Star, Color(0xFF1976D2)) }
            if (activities.isEmpty()) item { EmptyState("Sin ejercicios") }
            itemsIndexed(activities) { index, item ->
                ActivityItem(item, index + 1)
            }

            // SECCIÓN ALIMENTACIÓN
            item { SectionHeader("Alimentación", Icons.Default.ShoppingCart, Color(0xFF388E3C)) }
            if (foods.isEmpty()) item { EmptyState("Sin comidas") }
            itemsIndexed(foods) { index, item ->
                FoodItem(item, index + 1)
            }

            // SECCIÓN SUEÑO
            item { SectionHeader("Sueño", Icons.Default.Face, Color(0xFFFBC02D)) }
            if (sleeps.isEmpty()) item { EmptyState("Sin registros de sueño") }
            itemsIndexed(sleeps) { index, item ->
                SleepItem(item, index + 1)
            }
        }
    }
}

// --- FUNCIONES AUXILIARES ---

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@Composable
fun SectionHeader(title: String, icon: ImageVector, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = color)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = color)
    }
}

@Composable
fun EmptyState(text: String) {
    Text(text = text, color = Color.Gray, modifier = Modifier.padding(start = 32.dp))
}

// --- TARJETAS ---

@Composable
fun ActivityItem(item: ActivityEntity, number: Int) {
    HistoryCard(color = Color(0xFFE3F2FD)) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "#$number ${item.type}", fontWeight = FontWeight.Bold)
                Text(text = formatDate(item.date), style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Text(text = "${item.duration} min - Intensidad: ${item.intensity.toInt()}/5")
        }
    }
}

@Composable
fun FoodItem(item: FoodEntity, number: Int) {
    HistoryCard(color = Color(0xFFE8F5E9)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(text = "#$number ${item.foodName}", fontWeight = FontWeight.Bold)
                Text(text = formatDate(item.timestamp), style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Surface(color = Color(0xFF4CAF50), shape = RoundedCornerShape(16.dp)) {
                Text(text = "${item.calories} kcal", color = Color.White, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
            }
        }
    }
}

@Composable
fun SleepItem(item: SleepEntity, number: Int) {
    HistoryCard(color = Color(0xFFFFF9C4)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(text = "#$number Descanso (${item.quality})", fontWeight = FontWeight.Bold)
                Text(text = "${item.hours} Horas", style = MaterialTheme.typography.bodyMedium)
            }
            Text(text = formatDate(item.timestamp), style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}

@Composable
fun HistoryCard(color: Color, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}