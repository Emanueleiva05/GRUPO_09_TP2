package com.example.proyecto.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun BorrarCiudadesDeUnPaisScreen(navController: NavHostController) {
    var paisNom by remember { mutableStateOf("") }
    val context = LocalContext.current
    val dbHelper = DBHelper(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "Eliminar ciudades por país",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = paisNom,
            onValueChange = { paisNom = it },
            label = { Text("Nombre del país") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Button(
            onClick = {
                if (paisNom.isNotBlank()) {
                    dbHelper.borrarCiudadesDeUnPais(paisNom)
                    Toast.makeText(context, "Ciudades de '$paisNom' eliminadas", Toast.LENGTH_SHORT).show()
                    paisNom = ""
                    navController.navigate("ciudades")
                } else {
                    Toast.makeText(context, "Por favor, ingrese el nombre de un país", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Borrar ciudades")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BorrarCiudadesDeUnPaisPreview() {
    val navController = rememberNavController()
    BorrarCiudadesDeUnPaisScreen(navController = navController)
}
