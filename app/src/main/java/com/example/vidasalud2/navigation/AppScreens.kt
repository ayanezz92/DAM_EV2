package com.example.vidasalud2.navigation

sealed class AppScreens(val route: String) {
    object Splash : AppScreens("splash_screen")
    object Login : AppScreens("login_screen")
    object Home : AppScreens("home_screen")
    object Register : AppScreens("register_screen")
    object Community : AppScreens("community_screen")
    object Analysis : AppScreens("analysis_screen")
    object Profile : AppScreens("profile_screen")
    object RecommendationDetail : AppScreens("recommendation_detail_screen")

    // --- RUTAS NUEVAS AÑADIDAS ---
    object Subscription : AppScreens("subscription_screen")
    object DataConnections : AppScreens("data_connections_screen")
    object Notifications : AppScreens("notifications_screen")
    object Help : AppScreens("help_screen")
    object AdSettings : AppScreens("ad_settings_screen")
}