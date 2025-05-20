package com.example.proyecto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
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
fun CrearCiudadScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dbHelper = DBHelper(context)
    var nombreCiudad by remember { mutableStateOf("") }
    var poblacionCiudad by remember { mutableStateOf("") }
    var paisCiudad by remember { mutableStateOf("") }
    val paises = remember { mutableStateListOf<String>() }
    var mensajeError by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        paises.clear()
        paises.addAll(dbHelper.obtenerPaises())
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)
    ) {

        Text(text = "Nombre de la ciudad:", modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = nombreCiudad,
            onValueChange = { nuevoTexto -> nombreCiudad = nuevoTexto },
            label = { Text("Ingresar nombre de la ciudad") },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(text = "Población de la ciudad:", modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = poblacionCiudad,
            onValueChange = { nuevoTexto ->
                if (nuevoTexto.all { it.isDigit() }) {
                    poblacionCiudad = nuevoTexto
                }
            },
            label = { Text("Ingresar población de la ciudad") },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Seleccionar el país:", modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(15.dp))

        PaisSelector(
            paises = paises,
            paisSeleccionado = paisCiudad,
            onPaisSeleccionado = { paisCiudad = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (mensajeError.isNotEmpty()) {
            Text(text = mensajeError, color = Color.Red, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(10.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    val poblacionInt = poblacionCiudad.toIntOrNull()
                    if (nombreCiudad.isBlank()) {
                        mensajeError = "Debe ingresar el nombre de la ciudad"
                    } else if (poblacionInt == null) {
                        mensajeError = "Población inválida"
                    } else if (paisCiudad.isBlank()) {
                        mensajeError = "Debe seleccionar un país"
                    } else {
                        mensajeError = ""

                        // Obtener id del país para insertCiudad
                        val paisId = dbHelper.obtenerIdPaisPorNombre(paisCiudad)
                        if (paisId != null) {
                            dbHelper.insertCiudad(nombreCiudad, poblacionInt, paisId)
                            navController.navigate("ciudades")
                        } else {
                            mensajeError = "País seleccionado no válido"
                        }
                    }
                },
                modifier = Modifier.height(53.dp).width(127.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Crear ciudad",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaisSelector(
    paises: List<String>,
    paisSeleccionado: String,
    onPaisSeleccionado: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            value = paisSeleccionado,
            onValueChange = {},
            readOnly = true,
            label = { Text("Seleccionar país") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            shape = RoundedCornerShape(16.dp)
        ) {
            paises.forEach { pais ->
                DropdownMenuItem(
                    text = { Text(pais) },
                    onClick = {
                        onPaisSeleccionado(pais)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CrearCiudadPreview() {
    val navController = rememberNavController()
    CrearCiudadScreen(navController = navController)
}