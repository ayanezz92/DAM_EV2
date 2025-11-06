package com.example.vidasalud2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vidasalud2.features.analysis.AnalysisScreen
import com.example.vidasalud2.features.community.CommunityScreen
import com.example.vidasalud2.features.home.HomeScreen
import com.example.vidasalud2.features.profile.ProfileScreen
import com.example.vidasalud2.features.register.RegisterScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    mainNavController: NavController
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.Home.route
    ) {

        composable(BottomNavScreen.Home.route) {
            HomeScreen(navController = mainNavController)
        }

        composable(BottomNavScreen.Community.route) {
            CommunityScreen()
        }

        composable(BottomNavScreen.Register.route) {
            RegisterScreen()
        }

        // --- LÍNEA CORREGIDA ---
        composable(BottomNavScreen.Profile.route) {
            // Pasamos el controlador principal para que Profile pueda navegar
            ProfileScreen(mainNavController = mainNavController)
        }
    }
}