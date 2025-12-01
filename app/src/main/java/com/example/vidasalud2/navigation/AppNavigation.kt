package com.example.vidasalud2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vidasalud2.features.home.RecommendationDetailScreen
import com.example.vidasalud2.features.login.LoginScreen
import com.example.vidasalud2.features.main.MainScreen
import com.example.vidasalud2.features.profile.SubscriptionScreen
import com.example.vidasalud2.features.profile.DataConnectionsScreen
import com.example.vidasalud2.features.profile.NotificationsScreen
import com.example.vidasalud2.features.profile.HelpScreen
import com.example.vidasalud2.features.profile.AdSettingsScreen
import com.vidasalud.features.splash.SplashScreen

// --- IMPORTACIÓN NUEVA (Para ver la pantalla de datos) ---
import com.example.vidasalud2.features.analysis.HistoryScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Splash.route
    ) {

        composable(AppScreens.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(AppScreens.Login.route) {
            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    navController.navigate(AppScreens.Home.route) {
                        popUpTo(AppScreens.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(AppScreens.Home.route) {
            MainScreen(mainNavController = navController)
        }

        composable(AppScreens.RecommendationDetail.route) {
            RecommendationDetailScreen(navController = navController)
        }

        composable(AppScreens.Subscription.route) {
            SubscriptionScreen(navController = navController)
        }

        composable(AppScreens.DataConnections.route) {
            DataConnectionsScreen(navController = navController)
        }

        composable(AppScreens.Notifications.route) {
            NotificationsScreen(navController = navController)
        }

        composable(AppScreens.Help.route) {
            HelpScreen(navController = navController)
        }

        composable(AppScreens.AdSettings.route) {
            AdSettingsScreen(navController = navController)
        }

        // --- RUTA NUEVA AGREGADA ---
        // Esto conecta la ruta "analysis_screen" con tu pantalla de Historial
        composable(AppScreens.Analysis.route) {
            HistoryScreen()
        }
    }
}