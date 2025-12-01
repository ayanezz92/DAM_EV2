package com.example.vidasalud2.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack // <--- NUEVO
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class RecommendationItem(
    val id: Int,
    val title: String,
    val description: String,
    val icon: ImageVector
)

@Composable
fun RecommendationDetailScreen(navController: NavController) {
    val recommendationsList = listOf(
        RecommendationItem(
            1, "Hidratación Óptima",
            "Bebe al menos 2 litros de agua al día. Lleva una botella contigo siempre para recordarlo.",
            Icons.Default.LocalDrink
        ),
        RecommendationItem(
            2, "Higiene del Sueño",
            "Intenta dormir 7-8 horas. Evita pantallas una hora antes de ir a la cama para un descanso profundo.",
            Icons.Default.Bedtime
        ),
        RecommendationItem(
            3, "Movimiento Diario",
            "No necesitas un gimnasio. Camina 30 minutos al día o usa las escaleras en lugar del ascensor.",
            Icons.Default.DirectionsRun
        ),
        RecommendationItem(
            4, "Comida Consciente",
            "Prioriza verduras y proteínas en tus platos. Reduce los ultraprocesados y el azúcar añadido.",
            Icons.Default.Restaurant
        ),
        RecommendationItem(
            5, "Salud Mental",
            "Dedica 5 minutos al día para respirar conscientemente o meditar. Reduce el estrés notablemente.",
            Icons.Default.SelfImprovement
        )
    )

    val primaryGreen = Color(0xFF2E7D32)
    val bgGray = Color(0xFFF4F6F8)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = bgGray
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // --- 1. BARRA SUPERIOR CON FLECHA ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                // BOTÓN ATRÁS
                IconButton(
                    onClick = { navController.popBackStack() }, // Acción de volver
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = primaryGreen,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // TÍTULO (Ahora al lado de la flecha)
                Text(
                    text = "Recomendaciones",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = primaryGreen
                )
            }

            Text(
                text = "Tips personalizados para mejorar tu bienestar",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(start = 4.dp, bottom = 24.dp)
            )

            // --- 2. LISTA DE TARJETAS ---
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                items(recommendationsList) { item ->
                    RecommendationCard(item, primaryGreen)
                }
            }
        }
    }
}

@Composable
fun RecommendationCard(item: RecommendationItem, primaryColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(primaryColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = primaryColor,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    lineHeight = 20.sp
                )
            }
        }
    }
}