package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyectofinalintmov.R
import com.example.proyectofinalintmov.barralateral.BarraLateral
import com.example.proyectofinalintmov.bienvenida.Bienvenida
import com.example.proyectofinalintmov.iconmenu.IconMenu
import com.example.proyectofinalintmov.krankenwagen.model.Routes
import com.example.proyectofinalintmov.scrollprovincias.ScrollProvincias
import com.example.proyectofinalintmov.sesion.Sesion
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.SesionViewModel

/**
 * Scaffold que alberga la página de bienvenida
 * Recibe los siguientes parámetros
 * @param navController Controlador de navegación para manejar las transiciones entre pantallas.
 * @param menuDesplegado Indica si el menú está desplegado o no.
 * @param userDesplegado Indica si el diálogo de sesión de usuario está desplegado o no.
 * @param viewModel El ViewModel asociado a la pantalla de bienvenida.
 */
@SuppressLint("RememberReturnType", "UnusedMaterial3ScaffoldPaddingParameter",
    "StateFlowValueCalledInComposition"
)
@Composable
fun WelcomePage(
    navController: NavHostController,
    viewModel: KrankenwagenViewModel,
    showMenu: Boolean,
    userRegistered: Boolean,
    sesionViewModel: SesionViewModel
) {
    val nombreDocReg by sesionViewModel.nombreDoc.collectAsState()
    Scaffold(topBar = {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Bienvenida(
                bienvenidoADrHouseTextContent = "Bienvenido/a Dr $nombreDocReg"
            )
        }
    }, content = {
        ContenidoWelcome(
            navController = navController,
            menuDesplegado = showMenu,
            userDesplegado = userRegistered,
            viewModel = viewModel,
            sesionViewModel = sesionViewModel
        )
    }, bottomBar = {
        BarraMenu(viewModel = viewModel)
    })

}


/**
 * Composable que muestra el contenido de la pantalla de bienvenida.
 * Recibe los siguientes parámetros
 * @param navController Controlador de navegación para manejar las transiciones entre pantallas.
 * @param menuDesplegado Indica si el menú está desplegado o no.
 * @param userDesplegado Indica si el diálogo de sesión de usuario está desplegado o no.
 * @param viewModel El ViewModel asociado a la pantalla de bienvenida.
 */
@Composable
fun ContenidoWelcome(
    navController: NavHostController,
    menuDesplegado: Boolean,
    userDesplegado: Boolean,
    viewModel: KrankenwagenViewModel,
    sesionViewModel: SesionViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Fondo de la pantalla
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentScale = ContentScale.FillWidth,
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize()
        )
        Row(modifier = Modifier.fillMaxSize()) {
            // Barra lateral de navegación
            BarraLateral(
                onWelcTapped = { navController.navigate(Routes.PantallaWelcome.route) },
                onAmbTapped = { navController.navigate(Routes.PantallaAmbulances.route) },
                onHospTapped = { navController.navigate(Routes.PantallaHospitals.route) },
                onDocTapped = { navController.navigate(Routes.PantallaDocs.route) }
            )
            // Scroll con las imágenes de las provincias para poder filtrar los datos
            ScrollProvincias(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(top = 100.dp),
                // Filtrar por Almería
                onAlmerATapped = {
                    viewModel.filterBy("Almeria")
                    navController.navigate(Routes.PantallaHospitals.route)
                },
                // Filtrar por Cádiz
                onCDizTapped = {
                    viewModel.filterBy("Cadiz")
                    navController.navigate(Routes.PantallaHospitals.route)
                },
                // Filtrar por Córdoba
                onCRdobaTapped = {
                    viewModel.filterBy("Cordoba")
                    navController.navigate(Routes.PantallaHospitals.route)
                },
                // Filtrar por Granada
                onGranadaTapped = {
                    viewModel.filterBy("Granada")
                    navController.navigate(Routes.PantallaHospitals.route)
                },
                // Filtrar por Huelva
                onHuelvaTapped = {
                    viewModel.filterBy("Huelva")
                    navController.navigate(Routes.PantallaHospitals.route)
                },
                // Filtrar por Jaen
                onJaenTapped = {
                    viewModel.filterBy("Jaen")
                    navController.navigate(Routes.PantallaHospitals.route)
                },
                // Filtrar por Málaga
                onMLagaTapped = {
                    viewModel.filterBy("Malaga")
                    navController.navigate(Routes.PantallaHospitals.route)
                },
                // Filtrar por Sevilla
                onSevillaTapped = {
                    viewModel.filterBy("Sevilla")
                    navController.navigate(Routes.PantallaHospitals.route)
                }
            )
        }
        // Si se pulsa menú se abre el diálogo correspondiente
        if (menuDesplegado) {
            DialogMenu(viewModel, sesionViewModel)
        }
        // Si se pulsa sobre usuario se abre el diálogo correspondiente
        if (userDesplegado) {
            DialogSesion(viewModel, sesionViewModel)
        }
    }
}



/**
 * Composable que muestra la barra de menú.
 * Recibe el siguiente parámetro
 * @param viewModel El ViewModel asociado a la barra de menú.
 */
@Composable
fun BarraMenu(viewModel: KrankenwagenViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        Spacer(modifier = Modifier.padding(start = 160.dp))
        // Icono de menú
        IconMenu(
            modifier = Modifier
                .width(80.dp)
                .height(80.dp),
            onMenuTapped = { viewModel.openMenu() }
        )
        Spacer(modifier = Modifier.fillMaxWidth(0.9f))
        // Icono registrarse/iniciar sesión
        Sesion(
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .padding(end = 10.dp),
            onSesionTapped = { viewModel.openSesion() }
        )
    }
}


