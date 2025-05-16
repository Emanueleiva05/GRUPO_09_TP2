package com.example.proyecto.ui.screens

import android.widget.Space
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proyecto.DBHelper
import java.nio.file.WatchEvent


@Composable
fun CrearCiudadScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dbHelper = DBHelper(context)
    var nombreCiudad by remember { mutableStateOf("") }
    var poblacionCiudad by remember { mutableStateOf("") }
    var paisCiudad by remember { mutableStateOf("") }
    val paises = remember { mutableStateListOf<String>() }

    Column(modifier = Modifier.fillMaxSize()
        .padding(20.dp)) {

        Text(
            text = "Nombre de la ciudad: ",
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
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(15.dp))


        Text(
            text = "Poblacion de la ciudad: ",
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
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Seleccionar el pais: ",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        LaunchedEffect(Unit) {
            paises.clear()
            paises.addAll(dbHelper.obtenerPaises())
        }

        PaisSelector(
            paises = paises,
            paisSeleccionado = paisCiudad,
            onPaisSeleccionado = { paisCiudad = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val poblacionInt = poblacionCiudad.toIntOrNull()
                if (poblacionInt != null) {
                    dbHelper.insertCiudad(nombreCiudad, poblacionInt, paisCiudad)
                } else {
                    // Aquí podrías mostrar un mensaje de error
                }
            },
            modifier = Modifier
                .height(53.dp)
                .width(127.dp)
        ) {
            Text(
                text = "Crear ciudad",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
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
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = paisSeleccionado,
            onValueChange = {},
            readOnly = true, // Importante para que no sea editable
            label = { Text("Seleccionar país") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor() // Ancla el menú al TextField
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
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