package com.example.vidasalud2.data.local  // <--- ¡ESTA LÍNEA ES CRUCIAL!

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_table")
data class SleepEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val hours: Float,
    val quality: String,
    val timestamp: Long = System.currentTimeMillis()
)