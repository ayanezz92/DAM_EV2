package com.example.vidasalud2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vidasalud2.features.analysis.HistoryScreen // <--- IMPORTANTE
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

        // --- NUEVA RUTA AGREGADA ---
        composable(BottomNavScreen.Analysis.route) {
            HistoryScreen()
        }

        composable(BottomNavScreen.Profile.route) {
            ProfileScreen(mainNavController = mainNavController)
        }
    }
}