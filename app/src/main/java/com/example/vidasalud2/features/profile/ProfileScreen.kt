package com.example.vidasalud2.features.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage // Necesario para mostrar la foto de la galería
import com.example.vidasalud2.R
import com.example.vidasalud2.navigation.AppScreens

@Composable
fun ProfileScreen(mainNavController: NavController) { // Recibe el navController principal
    val context = LocalContext.current

    // 1. ESTADO PARA GUARDAR LA FOTO SELECCIONADA
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // 2. LANZADOR DE GALERÍA (Para elegir foto)
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        // Cuando el usuario elige una foto, la guardamos aquí
        if (uri != null) {
            selectedImageUri = uri
        }
    }

    // Colores corporativos
    val primaryGreen = Color(0xFF2E7D32)
    val bgGray = Color(0xFFF4F6F8)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = bgGray
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Mi Perfil",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = primaryGreen,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // --- SECCIÓN DE FOTO DE PERFIL ---
            Box(contentAlignment = Alignment.BottomEnd) {
                // FOTO (Círculo grande)
                if (selectedImageUri != null) {
                    // Si el usuario eligió foto, mostramos esa
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(2.dp, primaryGreen, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Si no, mostramos el avatar por defecto (tu logo o un icono genérico)
                    Image(
                        painter = painterResource(id = R.drawable.app_logo), // O usa Icons.Default.AccountCircle
                        contentDescription = "Foto por defecto",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(2.dp, primaryGreen, CircleShape),
                        contentScale = ContentScale.Fit // Fit para que tu logo se vea completo
                    )
                }

                // BOTÓN DE CÁMARA (Pequeño círculo verde)
                IconButton(
                    onClick = {
                        // 3. ACCIÓN AL DAR CLICK: ABRIR GALERÍA
                        galleryLauncher.launch("image/*")
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(primaryGreen)
                        .border(2.dp, Color.White, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Cambiar foto",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre y Correo
            Text(
                text = "Benja Alumno",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "benja@duocuc.cl",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- OPCIONES DE MENÚ ---
            ProfileOptionItem(
                icon = Icons.Default.Notifications,
                text = "Notificaciones",
                onClick = { mainNavController.navigate(AppScreens.Notifications.route) }
            )
            ProfileOptionItem(
                icon = Icons.Default.Sync,
                text = "Conexión de Datos",
                onClick = { mainNavController.navigate(AppScreens.DataConnections.route) }
            )
            ProfileOptionItem(
                icon = Icons.Default.CreditCard,
                text = "Suscripción Premium",
                onClick = { mainNavController.navigate(AppScreens.Subscription.route) }
            )
            ProfileOptionItem(
                icon = Icons.Default.Help,
                text = "Ayuda y Soporte",
                onClick = { mainNavController.navigate(AppScreens.Help.route) }
            )
            ProfileOptionItem(
                icon = Icons.Default.Settings,
                text = "Configuración de Anuncios",
                onClick = { mainNavController.navigate(AppScreens.AdSettings.route) }
            )

            Spacer(modifier = Modifier.weight(1f))

            // BOTÓN CERRAR SESIÓN
            Button(
                onClick = {
                    // Navegar al Login y borrar historial para no poder volver atrás
                    mainNavController.navigate(AppScreens.Login.route) {
                        popUpTo(0) // Borra todo el stack
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE)), // Rojo muy claro
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Cerrar Sesión", color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold)
            }

            // Espacio final para que no choque con el menú de navegación
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun ProfileOptionItem(icon: ImageVector, text: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF2E7D32))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = text, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
        }
    }
}