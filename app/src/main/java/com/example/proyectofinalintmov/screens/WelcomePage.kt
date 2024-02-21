package com.example.proyectofinalintmov.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.proyectofinalintmov.R
import com.example.proyectofinalintmov.barralateral.BarraLateral
import com.example.proyectofinalintmov.bienvenida.Bienvenida
import com.example.proyectofinalintmov.iconmenu.IconMenu
import com.example.proyectofinalintmov.model.Routes
import com.example.proyectofinalintmov.scrollprovincias.ScrollProvincias
import com.example.proyectofinalintmov.sesion.Sesion
import com.example.proyectofinalintmov.viewModels.WelcomePageViewModel

@SuppressLint("RememberReturnType", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WelcomePage(
    navController: NavHostController,
    viewModel: WelcomePageViewModel,
    showMenu: Boolean,
    userRegistered: Boolean
) {

    Scaffold(topBar = {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Bienvenida(
                bienvenidoADrHouseTextContent = stringResource(R.string.bienvenido_dr_house)
            )
        }
    }, content = {
        ContenidoWelcome(
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
fun ContenidoWelcome(
    navController: NavHostController,
    menuDesplegado: Boolean,
    userDesplegado: Boolean,
    viewModel: WelcomePageViewModel
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
            ScrollProvincias(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(top = 100.dp)
            )
        }
        if (menuDesplegado) {
            DialogMenu(viewModel = viewModel)
        }
        if (userDesplegado) {
            DialogSesion(viewModel = viewModel)
        }
    }
}

@Composable
fun DialogMenu(viewModel: WelcomePageViewModel) {
    Dialog(
        onDismissRequest = { viewModel.closeMenu() },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { viewModel.closeMenu() }) {
                    Text(text = stringResource(R.string.confirmar))
                }
            }
        }
    )

}

@Composable
fun DialogSesion(viewModel: WelcomePageViewModel) {
    Dialog(
        onDismissRequest = { viewModel.closeSesion() },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { viewModel.closeSesion() }) {
                    Text(text = stringResource(R.string.confirmar))
                }
            }
        }
    )
}

@Composable
fun BarraMenu(viewModel: WelcomePageViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        Spacer(modifier = Modifier.padding(start = 160.dp))
        IconMenu(
            modifier = Modifier
                .width(80.dp)
                .height(80.dp),
            onMenuTapped = { viewModel.openMenu() }
        )
        Spacer(modifier = Modifier.fillMaxWidth(0.9f))
        Sesion(
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .padding(end = 10.dp),
            onSesionTapped = { viewModel.openSesion() })
    }
}







