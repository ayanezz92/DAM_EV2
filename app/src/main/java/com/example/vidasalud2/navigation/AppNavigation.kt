package com.example.vidasalud2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
// --- IMPORTACIONES LIMPIAS ---
import com.example.vidasalud2.features.login.LoginScreen
import com.example.vidasalud2.features.main.MainScreen
import com.vidasalud.features.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Splash.route // Empezamos en el Splash
    ) {
        // --- Pantalla 1: Splash Screen ---
        composable(AppScreens.Splash.route) {
            // Esta línea ahora funcionará porque el import es correcto
            SplashScreen(navController = navController)
        }

        // --- Pantalla 2: Login Screen ---
        composable(AppScreens.Login.route) {
            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    navController.navigate(AppScreens.Home.route) {
                        popUpTo(AppScreens.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // --- Pantalla 3: Home (MainScreen) ---
        composable(AppScreens.Home.route) {
            MainScreen(mainNavController = navController)
        }
    }
}