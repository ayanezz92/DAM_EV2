package com.example.vidasalud2.repository

import com.example.vidasalud2.model.Post

class PostRepository {

    // Función que simula obtener datos de una red social
    fun getPosts(): List<Post> {
        return listOf(
            Post(1, "Laura G.", "¡Alcanzé mi meta semanal de 10k pasos! 💪", 12, "Hace 2h"),
            Post(2, "Carlos M.", "¿Alguien para salir a correr mañana en el parque?", 5, "Hace 4h"),
            Post(3, "Ana R.", "Hoy probé una nueva receta de ensalada, ¡buenísima! 🥗", 8, "Hace 6h"),
            Post(4, "Diego S.", "Terminé mi rutina de sueño por 7 días seguidos 😴", 20, "Hace 1d")
        )
    }
}