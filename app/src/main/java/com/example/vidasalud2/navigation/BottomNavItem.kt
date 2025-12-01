package com.example.vidasalud2.navigation

import com.example.vidasalud2.R

// Define las rutas para la navegación anidada
sealed class BottomNavScreen(val route: String, val title: String, val icon: Int) {
    object Home : BottomNavScreen(
        route = "home",
        title = "Inicio",
        icon = R.drawable.ic_home
    )
    object Register : BottomNavScreen(
        route = "register",
        title = "Registro",
        icon = R.drawable.ic_register
    )
    object Community : BottomNavScreen(
        route = "community",
        title = "Comunidad",
        icon = R.drawable.ic_community
    )
    object Analysis : BottomNavScreen( // <--- NUEVA OPCIÓN AGREGADA
        route = "analysis_screen",
        title = "Historial",
        icon = R.drawable.ic_home // URGENTE: Cambia esto por un ícono de lista si tienes uno (ej: ic_list)
    )
    object Profile : BottomNavScreen(
        route = "profile",
        title = "Perfil",
        icon = R.drawable.ic_profile
    )
}