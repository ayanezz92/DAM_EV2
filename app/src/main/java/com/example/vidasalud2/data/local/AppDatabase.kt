package com.example.vidasalud2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 1. AQUÍ AGREGAMOS LAS NUEVAS ENTIDADES (TABLAS)
@Database(
    entities = [ActivityEntity::class, FoodEntity::class, SleepEntity::class],
    version = 2, // 2. SUBIMOS LA VERSIÓN DE 1 A 2
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun activityDao(): ActivityDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    // 3. IMPORTANTE: ESTO BORRA LA BD VIEJA SI CAMBIA LA VERSIÓN (EVITA CRASHES)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}