package com.example.proyecto.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proyecto.DBHelper


@Composable
fun ModificarCiudadScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dbHelper = DBHelper(context)
    var nombreCiudad by remember { mutableStateOf("") }
    var poblacionCiudad by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()
        .padding(20.dp)) {

        Text(
            text = "Modificar poblacion",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center,

        )

        Text(
            text = "Nombre de la ciudad a cambiar poblacion: ",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = nombreCiudad,
            onValueChange = { nuevoTexto ->
                nombreCiudad = nuevoTexto
            },
            label = { Text("Ingresar nombre de la ciudad") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
        )

        Spacer(modifier = Modifier.height(15.dp))


        Text(
            text = "Nueva poblacion de la ciudad: ",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = poblacionCiudad,
            onValueChange = { nuevoTexto ->
                // Solo aceptar números (opcional)
                if (nuevoTexto.all { it.isDigit() }) {
                    poblacionCiudad = nuevoTexto
                }
            },
            label = { Text("Ingresar población de la ciudad") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    val poblacionInt = poblacionCiudad.toIntOrNull()
                    if (poblacionInt != null) {
                        dbHelper.modificarPoblacion(nombreCiudad, poblacionInt)
                    } else {
                        // Aquí podrías mostrar un mensaje de error
                    }
                    navController.navigate("ciudades")
                },
                modifier = Modifier
                    .height(53.dp)
                    .width(127.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Modificar poblacion",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            // Botón cancelar / volver
            OutlinedButton(
                onClick = { navController.navigate("ciudades") },
                modifier = Modifier
                    .height(53.dp)
                    .width(127.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Cancelar",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModificarCiudadPreview() {
    val navController = rememberNavController()
    ModificarCiudadScreen(navController = navController)
}