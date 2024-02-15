package com.example.proyectofinalintmov.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectofinalintmov.barralateral.BarraLateral
import com.example.proyectofinalintmov.bienvenida.Bienvenida
import com.example.proyectofinalintmov.data.User
import com.example.proyectofinalintmov.menu.Menu
import com.example.proyectofinalintmov.model.Routes
import com.example.proyectofinalintmov.scrollprovincias.ScrollProvincias
import com.example.proyectofinalintmov.usersesion.UserSesion
import com.google.relay.compose.SiblingsAlignedModifier

@Composable
fun WelcomePage(navController: NavHostController){
    Row(
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
                text = "Inicio",
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
            modifier = Modifier.fillMaxWidth(0.95f))
        {
            Bienvenida(bienvenidoADrHouseTextContent = "Bienvenido Dr House")
            ScrollProvincias(modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(top = 100.dp)
            )
        }
        Column( verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxHeight(0.9f))
        {
            Menu(modifier = Modifier
                .padding(end = 10.dp, bottom = 10.dp)
                .align(Alignment.End)
            )
        }
        Column( verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxHeight())
        {
            UserSesion(modifier = Modifier
                .padding(end = 10.dp, bottom = 10.dp)
                .align(Alignment.End)
            )
        }
    }
}