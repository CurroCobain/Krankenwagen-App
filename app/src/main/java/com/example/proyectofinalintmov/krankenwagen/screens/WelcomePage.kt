package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyectofinalintmov.R
import com.example.proyectofinalintmov.bienvenida.Bienvenida
import com.example.proyectofinalintmov.krankenwagen.model.Routes
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.SesionViewModel
import com.example.proyectofinalintmov.scrollprovincias.ScrollProvincias


/**
 * Scaffold que alberga la página de bienvenida
 */
@SuppressLint(
    "RememberReturnType", "UnusedMaterial3ScaffoldPaddingParameter",
    "StateFlowValueCalledInComposition"
)
@Composable
fun WelcomePage(
    navController: NavHostController,
    viewModel: KrankenwagenViewModel,
    userRegistered: Boolean,
    sesionViewModel: SesionViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet( modifier = Modifier.fillMaxWidth(0.3f)) {
                NavigationMenu(navController)
            }
        }
    ) {
        PrevContWelc(
            navController,
            userRegistered,
            viewModel,
            sesionViewModel,
            drawerState
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PrevContWelc(
    navController : NavHostController,
    userDesplegado : Boolean,
    viewModel : KrankenwagenViewModel,
    sesionViewModel : SesionViewModel,
    drawerState: DrawerState
){
    // Se almacena el nombre del Dr para mostrarlo en pantalla
    val nombreDocReg by sesionViewModel.nombreDoc.collectAsState()

    // Scaffold que compone la pantalla
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
        // Contenido del Scaffold
        ContenidoWelcome(
            navController = navController,
            userDesplegado = userDesplegado,
            viewModel = viewModel,
            sesionViewModel = sesionViewModel
        )
    }, bottomBar = {
        // Barra para acceder al menú y a las opciones de sesión
        BarraMenu(viewModel = viewModel, drawerState = drawerState)
    })
}

/**
 * Composable que muestra el contenido de la pantalla de bienvenida.
 */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ContenidoWelcome(
    navController: NavHostController,
    userDesplegado: Boolean,
    viewModel: KrankenwagenViewModel,
    sesionViewModel: SesionViewModel
) {
    val context = LocalContext.current
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
        Row(modifier = Modifier.fillMaxSize())
        {
            // Scroll con las imágenes de las provincias para poder filtrar los datos
            ScrollProvincias(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(top = 100.dp),
                // Filtrar por Almería
                onAlmerATapped = {
                    if (sesionViewModel.nombreDoc.value.isNotEmpty()) {
                        viewModel.getHosp("Almeria") {
                            viewModel.setProv("Almeria")
                            navController.navigate(Routes.PantallaHospitals.route)
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Debe iniciar sesión para poder acceder a la base de datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                },
                // Filtrar por Cádiz
                onCDizTapped = {
                    if (sesionViewModel.nombreDoc.value.isNotEmpty()) {
                        viewModel.getHosp("Cadiz") {
                            viewModel.setProv("Cadiz")
                            navController.navigate(Routes.PantallaHospitals.route)
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Debe iniciar sesión para poder acceder a la base de datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                // Filtrar por Córdoba
                onCRdobaTapped = {
                    if (sesionViewModel.nombreDoc.value.isNotEmpty()) {
                        viewModel.getHosp("Cordoba") {
                            viewModel.setProv("Cordoba")
                            navController.navigate(Routes.PantallaHospitals.route)
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Debe iniciar sesión para poder acceder a la base de datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                // Filtrar por Granada
                onGranadaTapped = {
                    if (sesionViewModel.nombreDoc.value.isNotEmpty()) {
                        viewModel.getHosp("Granada") {
                            viewModel.setProv("Granada")
                            navController.navigate(Routes.PantallaHospitals.route)
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Debe iniciar sesión para poder acceder a la base de datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                // Filtrar por Huelva
                onHuelvaTapped = {
                    if (sesionViewModel.nombreDoc.value.isNotEmpty()) {
                        viewModel.getHosp("Huelva") {
                            viewModel.setProv("Huelva")
                            navController.navigate(Routes.PantallaHospitals.route)
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Debe iniciar sesión para poder acceder a la base de datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                // Filtrar por Jaen
                onJaenTapped = {
                    if (sesionViewModel.nombreDoc.value.isNotEmpty()) {
                        viewModel.getHosp("Jaen") {
                            viewModel.setProv("Jaen")
                            navController.navigate(Routes.PantallaHospitals.route)
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Debe iniciar sesión para poder acceder a la base de datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                // Filtrar por Málaga
                onMLagaTapped = {
                    if (sesionViewModel.nombreDoc.value.isNotEmpty()) {
                        viewModel.getHosp("Malaga") {
                            viewModel.setProv("Malaga")
                            navController.navigate(Routes.PantallaHospitals.route)
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Debe iniciar sesión para poder acceder a la base de datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                // Filtrar por Sevilla
                onSevillaTapped = {
                    if (sesionViewModel.nombreDoc.value.isNotEmpty()) {
                        viewModel.getHosp("Sevilla") {
                            viewModel.setProv("Sevilla")
                            navController.navigate(Routes.PantallaHospitals.route)
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Debe iniciar sesión para poder acceder a la base de datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
        }
        // Cuando se modifica alguna de las variables que lo gestionan se muestran los distintos menús
        if (userDesplegado){
            // Si se pulsa sobre usuario se abre el diálogo correspondiente
             DialogSesion(viewModel, sesionViewModel)
        }
    }
}





