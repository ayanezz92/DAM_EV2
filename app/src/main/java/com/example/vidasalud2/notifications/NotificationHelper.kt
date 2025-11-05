package com.example.vidasalud2.notifications


import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.vidasalud2.R

class NotificationHelper(private val context: Context) {

    private val CHANNEL_ID = "vidasalud_channel_id"
    private val NOTIFICATION_ID = 1

    init {
        createNotificationChannel()
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "VidaSalud Recordatorios"
            val descriptionText = "Canal para recordatorios de VidaSalud"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Registrar el canal
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showTestNotification() {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_home) // Reemplaza con tu ícono de app
            .setContentTitle("¡Hola desde VidaSalud!")
            .setContentText("Esta es una notificación de prueba.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Permiso ya debe estar verificado antes de llamar a esto
        try {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
        } catch (e: SecurityException) {
            // Manejar error si el permiso no fue dado
            e.printStackTrace()
        }
    }
}