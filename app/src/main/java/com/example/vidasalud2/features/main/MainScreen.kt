package com.example.vidasalud2.features.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource // Necesario si usas iconos vectoriales
import androidx.compose.ui.res.painterResource // Necesario si usas iconos PNG/Drawable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vidasalud2.navigation.BottomNavGraph
import com.example.vidasalud2.navigation.BottomNavScreen

@Composable
fun MainScreen(mainNavController: NavController) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        // --- AQUÍ ESTABA EL PROBLEMA DEL SCROLL ---
        // Usamos Box + fillMaxSize para que la pantalla hija (Home)
        // tenga todo el espacio disponible para hacer su propio scroll.
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            BottomNavGraph(
                navController = navController,
                mainNavController = mainNavController
            )
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Register,
        BottomNavScreen.Community,
        BottomNavScreen.Analysis,
        BottomNavScreen.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        screens.forEach { screen ->
            NavigationBarItem(
                label = { Text(screen.title) },
                icon = {
                    // Carga el icono correctamente usando painterResource para drawables (R.drawable.x)
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.title
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}