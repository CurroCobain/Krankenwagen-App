package com.example.proyectofinalintmov.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.proyectofinalintmov.R
import com.example.proyectofinalintmov.barralateral.BarraLateral
import com.example.proyectofinalintmov.bienvenida.Bienvenida
import com.example.proyectofinalintmov.model.Routes
import com.example.proyectofinalintmov.viewModels.KrankenwagenViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun Clinics(
    navController: NavHostController,
    viewModel: KrankenwagenViewModel,
    showMenu: Boolean,
    userRegistered: Boolean
) {

    Scaffold(topBar = {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Bienvenida(
                bienvenidoADrHouseTextContent = "Bienvenido/a Dr ${viewModel.nombreDoc.value}"
            )
        }
    }, content = {
        ContenidoClinics(
            navController = navController,
            menuDesplegado = showMenu,
            userDesplegado = userRegistered,
            viewModel = viewModel
        )
    }, bottomBar = {
        BarraMenu(viewModel = viewModel)
    })

}

@Composable
fun ContenidoClinics(
    navController: NavHostController,
    menuDesplegado: Boolean,
    userDesplegado: Boolean,
    viewModel: KrankenwagenViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        // Fondo de la pantalla
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentScale = ContentScale.FillWidth,
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize()
        )
        Row(modifier = Modifier.fillMaxSize()) {
            BarraLateral(
                onWelcTapped = { navController.navigate(Routes.PantallaWelcome.route) },
                onAmbTapped = { navController.navigate(Routes.PantallaAmbulances.route) },
                onHospTapped = { navController.navigate(Routes.PantallaHospitals.route) },
                onDocTapped = { navController.navigate(Routes.PantallaDocs.route) })
            Text(text = "Clinics")
        }
        if (menuDesplegado) {
            DialogMenu(viewModel = viewModel)
        }
        if (userDesplegado) {
            DialogSesion(viewModel = viewModel)
        }
    }
}