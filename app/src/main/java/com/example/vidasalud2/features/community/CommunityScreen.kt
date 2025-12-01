package com.example.vidasalud2.features.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Modelo de datos (Ya no usamos 'likes' visualmente, pero lo dejo por si acaso)
data class Post(
    val id: Int,
    val user: String,
    val time: String,
    val content: String,
    val initialColor: Color
)

@Composable
fun CommunityScreen() {
    // Datos simulados
    val posts = listOf(
        Post(1, "Laura Gómez", "Hace 2h", "¡Hoy logré correr 5km sin parar! 🏃‍♀️ Muy feliz con mi progreso.", Color(0xFFE91E63)),
        Post(2, "Carlos Ruiz", "Hace 5h", "¿Alguien tiene una receta de batido de proteínas que recomiende? 🥤", Color(0xFF2196F3)),
        Post(3, "Ana Perez", "Ayer", "Descansar también es parte del entrenamiento. Hoy toca día de sueño reparador 😴", Color(0xFFFF9800)),
        Post(4, "Comunidad VidaSalud", "Fijado", "¡Bienvenidos al reto de hidratación! 💧 Beban sus 2L hoy.", Color(0xFF4CAF50)),
        Post(5, "Pedro M.", "Hace 1d", "Empezando la semana con toda la energía en el gimnasio 💪", Color(0xFF9C27B0))
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF4F6F8) // Gris suave de fondo
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Comunidad",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )
            Text(
                text = "Novedades y consejos",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(posts) { post ->
                    PostCard(post)
                }
            }
        }
    }
}

@Composable
fun PostCard(post: Post) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // --- CABECERA (Avatar + Nombre) ---
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Avatar circular con inicial
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(post.initialColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = post.user.first().toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = post.user,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = post.time,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // --- CONTENIDO DEL POST ---
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )

            // (Aquí eliminamos los botones de interacción para dejarlo limpio)
        }
    }
}