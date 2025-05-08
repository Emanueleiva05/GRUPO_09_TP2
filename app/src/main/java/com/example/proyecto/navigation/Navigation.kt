package com.example.proyecto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto.ui.screens.HomeScreen
import com.example.proyecto.ui.screens.AleatorioScreen
import com.example.proyecto.ui.screens.CiudadesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("aleatorio") { AleatorioScreen(navController) }
        composable("ciudades") { CiudadesScreen(navController) }
    }
}
