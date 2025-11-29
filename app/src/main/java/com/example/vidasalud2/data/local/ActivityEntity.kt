package com.example.vidasalud2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity_table")
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String, // Ej: "Correr", "Nadar"
    val duration: String, // Ej: "30"
    val intensity: Float, // Ej: 3.0
    val date: Long = System.currentTimeMillis() // Fecha actual automática
)