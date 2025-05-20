package com.example.proyecto.ui.screens

import android.R
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import android.content.Context
import android.widget.Button
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.proyecto.DBHelper
import java.nio.file.WatchEvent

@Composable
fun CiudadesScreen(navController: NavHostController) {
    var paisNom by remember { mutableStateOf("") }
    val context = LocalContext.current
    val dbHelper = DBHelper(context)

    Column(modifier = Modifier.fillMaxSize()
        .padding(20.dp)) {

        Box {
            Row { //Para crear el pais
                Button(onClick = { navController.navigate("crear_pais") },
                        modifier = Modifier.height(50.dp)
                            .width(127.dp)) {
                    Text("Crear pais")
                }
                Spacer(Modifier.width(20.dp))
                TextField(
                    value = paisNom,
                    onValueChange = { nuevoTexto ->
                        paisNom = nuevoTexto
                    },
                    label = { Text("Nombre del pais") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        //Para crear una ciudad
        Button(onClick = {navController.navigate("crear_ciudad")},
                modifier = Modifier
                    .height(50.dp)) {
            Text("Crear ciudad")
        }

        Spacer(modifier = Modifier.height(500.dp))

        Row { //Para borrar ciudades de un pais
            Button(onClick = { dbHelper.borrarCiudadesDeUnPais(paisNom) },
                    modifier = Modifier
                        .height(53.dp)
                        .width(127.dp)
                    ){
                Text(
                    text = "Borrar ciudades",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                    )
            }

            Spacer(modifier = Modifier.width(10.dp))

            TextField(
                value = paisNom,
                onValueChange = { nuevoTexto ->
                    paisNom = nuevoTexto
                },
                label = { Text("Nombre del pais") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        //Para modificar la poblacion de una ciudad
        Button(onClick = {navController.navigate("modificar_ciudad")},
                modifier = Modifier
                    .height(53.dp)) {
            Text("Modificar poblacion de ciudad ")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CiudadesScreenPreview() {
    val navController = rememberNavController()
    CiudadesScreen(navController = navController)
}