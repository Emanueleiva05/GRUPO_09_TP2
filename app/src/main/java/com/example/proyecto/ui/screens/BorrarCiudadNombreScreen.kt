package com.example.proyecto.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proyecto.DBHelper

@Composable
fun BorrarCiudadNombreScreen(navController: NavHostController) {
    val context = LocalContext.current
    val dbHelper = DBHelper(context)

    var ciudades by remember { mutableStateOf(listOf<DBHelper.CiudadDetalle>()) }
    var ciudadNom by remember { mutableStateOf("") }
    var paisNom by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        ciudades = dbHelper.obtenerCiudades()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Eliminar ciudad",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = paisNom,
            onValueChange = { paisNom = it },
            label = { Text("Nombre del país (opcional)") },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        CiudadSelector(
            ciudades = ciudades,
            ciudadSeleccionada = ciudadNom,
            onCiudadSeleccionada = { ciudadNom = it },
            filtroPais = paisNom
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    when {
                        ciudadNom.isBlank() -> {
                            Toast.makeText(context, "Por favor, ingrese el nombre de una ciudad",
                                Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            val exito = dbHelper.borrarCiudadPorNombre(ciudadNom)
                            if (exito) {
                                Toast.makeText(context, "Ciudad de '$ciudadNom' eliminada con exito", Toast.LENGTH_SHORT).show()
                                ciudadNom = ""
                                navController.navigate("ciudades")
                            } else {
                                Toast.makeText(context, "Se produjo un error al eliminar la ciudad", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .height(53.dp)
                    .width(127.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Borrar ciudad",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            OutlinedButton(
                onClick = { navController.navigate("ciudades") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .height(53.dp)
                    .width(127.dp)
            ) {
                Text("Cancelar")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CiudadSelector(
    ciudades: List<DBHelper.CiudadDetalle>,
    ciudadSeleccionada: String,
    onCiudadSeleccionada: (String) -> Unit,
    filtroPais: String
) {
    var expanded by remember { mutableStateOf(false) }

    val ciudadesFiltradas = if (filtroPais.isNotBlank()) {
        ciudades.filter { it.paisNombre.trim().contains(filtroPais.trim(), ignoreCase = true) }
    } else {
        ciudades
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}
    ) {
        TextField(
            value = ciudadSeleccionada,
            onValueChange = {},
            readOnly = true,
            label = { Text("Seleccionar ciudad") },
            shape = RoundedCornerShape(16.dp),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false}
        ) {
            ciudadesFiltradas.forEach { ciudad ->
                DropdownMenuItem(
                    text = { Text(ciudad.nombre) },
                    onClick = {
                        onCiudadSeleccionada(ciudad.nombre)
                        expanded = false
                    }
                )
            }

            if (ciudadesFiltradas.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("Sin resultados") },
                    onClick = { expanded = false }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BorrarCiudadNombrePreview() {
    val navController = rememberNavController()
    BorrarCiudadNombreScreen(navController = navController)
}
