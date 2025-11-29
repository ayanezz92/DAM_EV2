package com.example.vidasalud2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    // Función para insertar una actividad
    @Insert
    suspend fun insertActivity(activity: ActivityEntity)

    // Función para leer todas las actividades (ordenadas por fecha)
    @Query("SELECT * FROM activity_table ORDER BY date DESC")
    fun getAllActivities(): Flow<List<ActivityEntity>>
}