package com.example.proyecto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun HomeScreen(navController: NavHostController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, bottom = 50.dp, start = 20.dp, end = 20.dp)
            .background(color = Color.Blue)){
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "Selecione una opcion",
                    style = TextStyle(fontSize = 30.sp),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 40.dp)
                )
                Spacer(modifier = Modifier.height(100.dp))
                Button(onClick = {
                    navController.navigate("aleatorio")
                }, modifier = Modifier
                    .height(50.dp)
                    .width(300.dp),
                   colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                    Text("Ir a Aleatorio")
                }
                Spacer(modifier = Modifier.height(50.dp))
                Button(onClick = {
                    navController.navigate("ciudades")
                }, modifier = Modifier
                    .height(50.dp)
                    .width(300.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                    Text("Ir a Ciudades")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = NavHostController(LocalContext.current))
}