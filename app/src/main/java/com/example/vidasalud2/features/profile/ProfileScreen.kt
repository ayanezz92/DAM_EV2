package com.example.vidasalud2.features.profile // Asegúrate que el package sea el correcto

import android.Manifest // Import para permisos
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build // Import para verificar la versión de Android
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.vidasalud2.R // Importa tu R
import com.example.vidasalud2.notifications.NotificationHelper // <-- IMPORT AÑADIDO
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current

    // --- 1. Lógica para generar un URI temporal para la CÁMARA ---
    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    fun getTmpFileUri(context: Context): Uri {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", context.cacheDir)
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider", // Debe coincidir con el AndroidManifest
            imageFile
        )
    }

    // --- 2. Lógica para los LANZADORES de actividades ---

    // Lanzador para la GALERÍA
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                viewModel.onImageChange(uri)
            }
            viewModel.onDismissImageDialog()
        }
    )

    // Lanzador para la CÁMARA
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success && cameraImageUri != null) {
                viewModel.onImageChange(cameraImageUri)
            }
            viewModel.onDismissImageDialog()
        }
    )

    // --- 3. Lógica para PERMISOS (CÁMARA) ---

    // Permiso de CÁMARA
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
            if (granted) {
                // Si el permiso fue otorgado, lanzar la cámara
                cameraImageUri = getTmpFileUri(context)
                cameraLauncher.launch(cameraImageUri!!)
            } else {
                // (Opcional: Mostrar un Snackbar diciendo que el permiso es necesario)
                viewModel.onDismissImageDialog()
            }
        }
    )

    // --- LÓGICA DE NOTIFICACIÓN AÑADIDA ---
    val notificationHelper = NotificationHelper(context)

    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else {
            // En versiones anteriores, el permiso es automático
            mutableStateOf(true)
        }
    }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasNotificationPermission = granted
            if (granted) {
                // Si se concede, mostrar la notificación de prueba
                notificationHelper.showTestNotification()
            } else {
                // (Opcional: Mostrar Snackbar de permiso denegado)
            }
        }
    )
    // --- FIN DE LÓGICA AÑADIDA ---


    // --- 4. Interfaz de Usuario (UI) ---
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Encabezado ---
        item {
            Text(
                text = "Configuración y Perfil",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }

        // --- Foto de Perfil ---
        item {
            AsyncImage(
                model = uiState.imageUri ?: R.drawable.profile_placeholder, // Un placeholder
                contentDescription = "Foto de Perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .clickable { viewModel.onShowImageDialog() }, // <-- Acción aquí
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = uiState.userName,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = uiState.activityStreak,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(24.dp))
            Divider()
        }

        // --- Opciones de Configuración ---
        item {
            ProfileOptionItem(title = "Mi Suscripción")
            ProfileOptionItem(title = "Conexión de Datos Externos")

            // --- ÍTEM DE NOTIFICACIÓN MODIFICADO ---
            ProfileOptionItem(
                title = "Preferencias de Notificación",
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        // En Android 13+, verificar/pedir permiso
                        if (hasNotificationPermission) {
                            notificationHelper.showTestNotification()
                        } else {
                            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    } else {
                        // En versiones < 13, solo mostrar
                        notificationHelper.showTestNotification()
                    }
                }
            )
            // --- FIN DE MODIFICACIÓN ---

            ProfileOptionItem(title = "Ayuda y Soporte")
            ProfileOptionItem(title = "Configuración de Publicidad")
        }

        // --- Botón Cerrar Sesión ---
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { /* TODO: Navegar a Login */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6FAF4E))
            ) {
                Text("Cerrar Sesión")
            }
        }
    }

    // --- 5. Diálogo de Selección (Cámara/Galería) ---
    if (viewModel.showImageSourceDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onDismissImageDialog() },
            title = { Text("Cambiar foto de perfil") },
            text = { Text("Elige una fuente para tu nueva foto") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Lanzar Galería (PickVisualMedia)
                        galleryLauncher.launch(
                            androidx.activity.result.PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                ) {
                    Text("Galería")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        // Verificar permiso de cámara
                        if (hasCameraPermission) {
                            cameraImageUri = getTmpFileUri(context)
                            cameraLauncher.launch(cameraImageUri!!)
                        } else {
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                ) {
                    Text("Cámara")
                }
            }
        )
    }
}


@Composable
fun ProfileOptionItem(title: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // El onClick se usa aquí
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // (Aquí iría el ícono correspondiente de la maqueta)
        // Icon(painter = ..., contentDescription = null, modifier = Modifier.padding(end = 16.dp))
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
    }
    Divider()
}