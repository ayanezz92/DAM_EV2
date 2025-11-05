package com.example.vidasalud2.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.vidasalud2.R // Importa tus íconos

// Define las rutas para la navegación anidada
sealed class BottomNavScreen(val route: String, val title: String, val icon: Int) {
    object Home : BottomNavScreen(
        route = "home",
        title = "Inicio",
        icon = R.drawable.ic_home // Reemplaza con tu ícono
    )
    object Register : BottomNavScreen(
        route = "register",
        title = "Registro",
        icon = R.drawable.ic_register // Reemplaza con tu ícono
    )
    object Community : BottomNavScreen(
        route = "community",
        title = "Comunidad",
        icon = R.drawable.ic_community // Reemplaza con tu ícono
    )
    object Profile : BottomNavScreen(
        route = "profile",
        title = "Perfil",
        icon = R.drawable.ic_profile // Reemplaza con tu ícono
    )
}
