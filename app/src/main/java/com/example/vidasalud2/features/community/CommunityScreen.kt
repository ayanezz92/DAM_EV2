package com.example.vidasalud2.features.community

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// --- 1. El Modelo de Datos ---
// Define cómo es una "Publicación"
data class CommunityPost(
    val id: String,
    val author: String,
    val timestamp: String,
    val content: String
)

// --- 2. Datos de Prueba ---
// Una lista temporal para que tu pantalla muestre algo.
// Más adelante, esto vendrá de un ViewModel y una API.
val dummyPosts = listOf(
    CommunityPost("1", "Ana Fitness", "Hace 2 horas", "¡Hoy corrí mis primeros 5km! Muy orgullosa. 💪"),
    CommunityPost("2", "Carlos Chef", "Hace 5 horas", "¿Alguien tiene una buena receta de batido de proteína post-entrenamiento?"),
    CommunityPost("3", "Meditación Mindy", "Hace 1 día", "Recordatorio: Tómate 5 minutos para respirar profundamente hoy. #PazMental"),
    CommunityPost("4", "Usuario Nuevo", "Hace 2 días", "¡Hola comunidad! Acabo de unirme. Busco consejos para empezar en el gimnasio.")
)

// --- 3. La Pantalla de Comunidad (Tu Composable principal) ---
// La anotación @OptIn es necesaria porque TopAppBar es experimental
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen() {
    // Scaffold es una plantilla básica de Material Design
    // te da espacios para una barra superior, inferior, etc.
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comunidad") },
                // Colores de ejemplo, puedes personalizarlos
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues -> // 'paddingValues' es el espacio que usa la TopAppBar

        // LazyColumn es una lista que solo "dibuja" los items que se ven
        // en pantalla. Es súper eficiente.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize() // Ocupa toda la pantalla
                .padding(paddingValues) // Aplica el padding de la TopBar
        ) {
            // "items" es como un bucle "for" para la lista
            items(dummyPosts) { post ->
                // Llama a nuestro Composable de tarjeta por cada item
                PostCard(post = post)
            }
        }
    }
}

// --- 4. El Composable para UNA Publicación ---
// Esta es la "tarjeta" que se repetirá en la lista.
@Composable
fun PostCard(post: CommunityPost) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = post.author,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = post.timestamp,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}