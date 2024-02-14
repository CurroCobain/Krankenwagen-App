package com.example.proyectofinalintmov.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectofinalintmov.barralateral.BarraLateral
// import com.example.myapplication.bienvenida.Bienvenida
// import com.example.myapplication.model.Routes
import com.example.proyectofinalintmov.model.Routes

@Composable
fun WelcomePage(navController: NavHostController){
    Box(
        modifier = Modifier
            .background(Color(225, 241, 222))
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(Color(74, 121, 66))
                .fillMaxHeight()
                .width(144.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        )
        {
            Text(
                text = "Ambul",
                color = Color.Black,
                fontSize = 30.sp,
                modifier = Modifier.background(Color.White)
            )
            BarraLateral(
                onWelcTapped = { navController.navigate(Routes.PantallaWelcome.route) },
                onAmbTapped = { navController.navigate(Routes.PantallaAmbulances.route) },
                onHospTapped = { navController.navigate(Routes.PantallaHospitals.route) },
                onDocTapped = { navController.navigate(Routes.PantallaDocs.route) }
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth())
        {
            // Bienvenida(bienvenidoADrHouseTextContent = "Bienvenido Dr House")
        }
    }
}