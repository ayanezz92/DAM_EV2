package com.example.vidasalud2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_table")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val foodName: String,
    val calories: Int,
    val timestamp: Long = System.currentTimeMillis()
)