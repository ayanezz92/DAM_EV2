package com.example.vidasalud2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    // --- ACTIVIDAD FÍSICA ---
    @Insert
    suspend fun insertar(activity: ActivityEntity)

    @Query("SELECT * FROM activity_table ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<ActivityEntity>>

    // --- ALIMENTACIÓN ---
    @Insert
    suspend fun insertFood(food: FoodEntity)

    @Query("SELECT * FROM food_table ORDER BY timestamp DESC")
    fun getAllFood(): Flow<List<FoodEntity>>

    // --- SUEÑO ---
    @Insert
    suspend fun insertSleep(sleep: SleepEntity)

    @Query("SELECT * FROM sleep_table ORDER BY timestamp DESC")
    fun getAllSleep(): Flow<List<SleepEntity>>
}