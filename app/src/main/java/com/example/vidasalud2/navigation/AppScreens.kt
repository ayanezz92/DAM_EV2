package com.example.vidasalud2.navigation

// Objeto sellado para definir las rutas de cada pantalla
sealed class AppScreens(val route: String) {
    object Splash : AppScreens("splash_screen")
    object Login : AppScreens("login_screen")
    object Home : AppScreens("home_screen")
    object Register : AppScreens("register_screen")
    object Community : AppScreens("community_screen")
    object Analysis : AppScreens("analysis_screen")
    object Profile : AppScreens("profile_screen")
}