package com.example.vidasalud2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vidasalud2.features.community.CommunityScreen
import com.example.vidasalud2.features.home.HomeScreen
import com.example.vidasalud2.features.profile.ProfileScreen
import com.example.vidasalud2.features.register.RegisterScreen
import com.example.vidasalud2.features.community.CommunityScreen // Lo crearemos
import com.example.vidasalud2.features.home.HomeScreen // Lo crearemos
import com.example.vidasalud2.features.profile.ProfileScreen // Lo crearemos
import com.example.vidasalud2.features.register.RegisterScreen // Lo crearemos

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.Home.route
    ) {
        composable(route = BottomNavScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomNavScreen.Register.route) {
            RegisterScreen()
        }
        composable(route = BottomNavScreen.Community.route) {
            CommunityScreen()
        }
        composable(route = BottomNavScreen.Profile.route) {
            ProfileScreen()
        }
    }
}