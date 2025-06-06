package com.example.proyecto.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.border
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.proyecto.DBHelper

@Composable
fun CiudadesScreen(navController: NavHostController) {
    var ciudadNom by remember { mutableStateOf("") }
    val context = LocalContext.current
    val dbHelper = DBHelper(context)

    var ciudades by remember { mutableStateOf(listOf<DBHelper.CiudadDetalle>()) }
    val ciudadesFiltradas = ciudades.filter {
        it.nombre.contains(ciudadNom, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        ciudades = dbHelper.obtenerCiudades()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { navController.navigate("crear_pais") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .height(50.dp)
                        .width(127.dp)
                ) {
                    Text("Crear pais")
                }
                Button(
                    onClick = { navController.navigate("crear_ciudad") },

                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .height(50.dp)
                        .width(127.dp)
                ) {
                    Text("Crear ciudad")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                TextField(
                    value = ciudadNom,
                    onValueChange = { nuevoTexto -> ciudadNom = nuevoTexto },
                    label = { Text("Ingrese el nombre de una ciudad") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(9.dp)
        ) {
            if (ciudades.isEmpty()) {
                Text(
                    text = "No hay ciudades para mostrar",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyColumn {
                    items(ciudadesFiltradas) { ciudad ->
                        Text(
                            text = "${ciudad.nombre}, ${ciudad.paisNombre} - ${ciudad
                                .poblacion} habitantes",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Divider(color = Color.LightGray)
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { navController.navigate("borrar_ciudades_pais") },
                    modifier = Modifier
                        .height(53.dp)
                        .width(160.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Borrar ciudades",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = { navController.navigate("borrar_ciudad_nombre") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .height(53.dp)
                        .width(160.dp)
                ) {
                    Text(
                        text = "Borrar ciudad por nombre",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { navController.navigate("modificar_ciudad") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.height(53.dp)
                ) {
                    Text("Modificar poblacion de ciudad")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CiudadesScreenPreview() {
    val navController = rememberNavController()
    CiudadesScreen(navController = navController)
}
