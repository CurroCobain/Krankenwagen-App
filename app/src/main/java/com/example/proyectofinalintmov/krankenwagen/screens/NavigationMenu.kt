package com.example.proyectofinalintmov.krankenwagen.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectofinalintmov.R
import com.example.proyectofinalintmov.iconmenu.IconMenu
import com.example.proyectofinalintmov.krankenwagen.model.Routes
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import com.example.proyectofinalintmov.sesion.Sesion
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NavigationMenu(
    navController: NavController,
    viewModel: KrankenwagenViewModel){
    var row1Color by remember { mutableStateOf(Color.Transparent) }
    var row2Color by remember { mutableStateOf(Color.Transparent) }
    var row3Color by remember { mutableStateOf(Color.Transparent) }
    var row4Color by remember { mutableStateOf(Color.Transparent) }

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(74, 121, 66))
    ) {
        Spacer(modifier = Modifier.padding(50.dp))

        // ---------------------------- Efecto de la linea Inicio -------------------------------
        RowColorEffect(row1Color) { newColor ->
            row1Color = newColor
        }
        //  ------------------------------------------- Inicio -------------------------------
        Row(
            Modifier
                .fillMaxWidth()
                .background(color = row1Color)
                .clickable {
                    row1Color = Color.LightGray
                    navController.navigate(Routes.PantallaWelcome.route)
                })
        {
            Spacer(modifier = Modifier.padding(start = 40.dp))
            Image(
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp),
                painter = painterResource(id = R.drawable.barra_lateral_welc),
                contentDescription = "Inicio")
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Text(text = "Inicio",
                fontSize = 40.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
        Spacer(modifier = Modifier.padding(20.dp))
        // ---------------------------- Efecto de la linea Hospitales -------------------------------
        RowColorEffect(row2Color) { newColor ->
            row2Color = newColor
        }
        //  ------------------------------------------- Hospitales -------------------------------
        Row(
            Modifier
                .fillMaxWidth()
                .background(color = row2Color)
                .clickable {
                    row2Color = Color.LightGray
                    navController.navigate(Routes.PantallaHospitals.route)
                })
        {
            Spacer(modifier = Modifier.padding(start = 40.dp))
            Image(
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp),
                painter = painterResource(id = R.drawable.barra_lateral_hosp),
                contentDescription = "Hospitales")
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Text(text = "Hospitales",
                fontSize = 40.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
        Spacer(modifier = Modifier.padding(20.dp))
        // ---------------------------- Efecto de la linea Ambulancias -------------------------------
        RowColorEffect(row3Color) { newColor ->
            row3Color = newColor
        }
        //  ------------------------------------------- Ambulancias -------------------------------
        Row(
            Modifier
                .fillMaxWidth()
                .background(color = row3Color)
                .clickable {
                    row3Color = Color.LightGray
                    viewModel.getAllAmb {
                        navController.navigate(Routes.PantallaAmbulances.route)
                    }
                })
        {
            Spacer(modifier = Modifier.padding(start = 40.dp))
            Image(
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp),
                painter = painterResource(id = R.drawable.barra_lateral_amb),
                contentDescription = "Ambulancias")
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Text(text = "Ambulancias",
                fontSize = 40.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
        Spacer(modifier = Modifier.padding(20.dp))
        // ---------------------------- Efecto de la linea Creación -------------------------------
        RowColorEffect(row4Color) { newColor ->
            row4Color = newColor
        }
        //  ------------------------------------------- Creación -------------------------------
        Row(
            Modifier
                .fillMaxWidth()
                .background(color = row4Color)
                .clickable {
                    row4Color = Color.LightGray
                    navController.navigate(Routes.PantallaCreate.route)
                })
        {
            Spacer(modifier = Modifier.padding(start = 40.dp))
            Image(
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .padding(top = 5.dp),
                painter = painterResource(id = R.drawable.barra_lateral_vector),
                contentDescription = "Creación")
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Text(text = "Creación \n \n de recursos",
                fontSize = 40.sp,
                color = Color.White
            )
        }
    }
}

/**
 * Funcion que realiza el efecto de cambio de color de los botones al ser pulsados
 */
@Composable
fun RowColorEffect(color: Color, onColorChange: (Color) -> Unit) {
    LaunchedEffect(color) {
        if (color == Color.LightGray) {
            delay(200)
            onColorChange(Color.Transparent)
        }
    }
}

/**
 * Composable que muestra la barra de menú.
 */
@Composable
fun BarraMenu(
    viewModel: KrankenwagenViewModel,
    drawerState1: DrawerState,
    drawerState2: DrawerState
) {
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        Spacer(modifier = Modifier.padding(start = 60.dp))
        // Icono de menú
        IconMenu(
            modifier = Modifier
                .width(80.dp)
                .height(80.dp),
            onMenuTapped = {
                scope.launch {
                    drawerState1.apply {
                        if (isClosed) open() else close()
                    }
                }
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.CenterVertically)
        )
        // Icono registrarse/iniciar sesión
        Sesion(
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .padding(end = 10.dp),
            onSesionTapped = {
                scope.launch {
                    drawerState2.apply {
                        if (isClosed) open() else close()
                    }
                }
            }
        )
    }
}