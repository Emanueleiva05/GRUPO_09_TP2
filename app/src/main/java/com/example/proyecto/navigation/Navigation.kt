package com.example.proyecto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto.ui.screens.CiudadesScreen
import com.example.proyecto.ui.screens.CrearCiudadScreen
import com.example.proyecto.ui.screens.CrearPaisScreen
import com.example.proyecto.ui.screens.ModificarCiudadScreen
import com.example.proyecto.ui.screens.BorrarCiudadesDeUnPaisScreen
import com.example.proyecto.ui.screens.BorrarCiudadNombreScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "ciudades") {
        composable("ciudades") { CiudadesScreen(navController) }
        composable("crear_ciudad") { CrearCiudadScreen(navController) }
        composable("modificar_ciudad") { ModificarCiudadScreen(navController) }
        composable("crear_pais") { CrearPaisScreen(navController)}
        composable("borrar_ciudad_nombre") { BorrarCiudadNombreScreen(navController) }
        composable("borrar_ciudades_pais") { BorrarCiudadesDeUnPaisScreen(navController) }
    }
}
