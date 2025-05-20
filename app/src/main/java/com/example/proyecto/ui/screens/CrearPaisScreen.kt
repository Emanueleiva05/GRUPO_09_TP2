package com.example.proyecto.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.proyecto.DBHelper

@Composable
fun CrearPaisScreen(navController: NavHostController) {
    val context = LocalContext.current
    var nombrePais by remember { mutableStateOf("") }
    val dbHelper = remember { DBHelper(context) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Agregar un nuevo pais")

        OutlinedTextField(
            value = nombrePais,
            onValueChange = { nombrePais = it },
            label = { Text("Nombre del pais") }
        )

        Button(
            onClick =  {
                if (nombrePais.isNotBlank()) {
                    val resultado = dbHelper.insertPais(nombrePais.trim())
                    if (resultado) {
                        Toast.makeText(context, "Pais agregado con exito", Toast.LENGTH_SHORT).show()
                        navController.navigate("ciudades")
                    } else {
                        Toast.makeText(context, "Error al agregar pais", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "El nombre no puede estar vacio", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Guardar") }

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate("ciudades") }
        ) { Text("Cancelar") }
    }
}

@Preview(showBackground = true)
@Composable
fun CrearPaisScreenPreview() {
    val navController = rememberNavController()
    CrearPaisScreen(navController = navController)
}