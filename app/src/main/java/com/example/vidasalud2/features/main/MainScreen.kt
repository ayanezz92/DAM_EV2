package com.example.vidasalud2.features.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vidasalud2.navigation.BottomNavGraph
import com.example.vidasalud2.navigation.BottomNavScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(mainNavController: NavController) { // <-- Este es el controlador principal
    // Este es el NavController para la barra inferior (anidado)
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController = bottomNavController) }
    ) { paddingValues ->
        // Pasamos el padding al gráfico de navegación anidado
        Box(modifier = Modifier.padding(paddingValues)) {
            // El NavHost que controla las 4 pantallas principales

            // --- AQUÍ ESTÁ LA CORRECCIÓN ---
            // Le pasamos el controlador principal al grafo inferior
            BottomNavGraph(
                navController = bottomNavController,
                mainNavController = mainNavController // <-- PARÁMETRO AÑADIDO
            )
        }
    }
}

// Composable para la Barra de Navegación Inferior
@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Register, // (Nota: Es raro tener "Register" en la barra inferior)
        BottomNavScreen.Community,
        BottomNavScreen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        screens.forEach { screen ->
            NavigationBarItem(
                label = { Text(text = screen.title) },
                icon = { Icon(painterResource(id = screen.icon), contentDescription = screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        // Evita acumular pantallas en el stack
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}