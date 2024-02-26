package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectofinalintmov.R
import com.example.proyectofinalintmov.barralateral.BarraLateral
import com.example.proyectofinalintmov.bienvenida.Bienvenida
import com.example.proyectofinalintmov.krankenwagen.model.Routes
import com.example.proyectofinalintmov.krankenwagen.viewModels.AmbulancesViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.HospitalViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.SesionViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun Create(
    navController: NavHostController,
    viewModel: KrankenwagenViewModel,
    showMenu: Boolean,
    userRegistered: Boolean,
    sesionViewModel: SesionViewModel,
    ambulancesViewModel: AmbulancesViewModel,
    hospitalViewModel: HospitalViewModel
) {
    val nombreDocReg by sesionViewModel.nombreDoc.collectAsState()
    Scaffold(topBar = {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Bienvenida(
                bienvenidoADrHouseTextContent = "Bienvenido/a Dr $nombreDocReg"
            )
        }
    }, content = {
        ContenidoCreate(
            navController = navController,
            menuDesplegado = showMenu,
            userDesplegado = userRegistered,
            viewModel = viewModel,
            sesionViewModel = sesionViewModel,
            ambulancesViewModel = ambulancesViewModel,
            hospitalViewModel = hospitalViewModel
        )
    }, bottomBar = {
        BarraMenu(viewModel = viewModel)
    })

}

/**
 * Composable que muestra el contenido de la pantalla de Centros de Salud.
 * Recibe los siguientes parámetros
 * @param navController Controlador de navegación para manejar las transiciones entre pantallas.
 * @param menuDesplegado Indica si el menú está desplegado o no.
 * @param userDesplegado Indica si el diálogo de sesión de usuario está desplegado o no.
 * @param viewModel El ViewModel asociado a la pantalla de bienvenida.
 */
@Composable
fun ContenidoCreate(
    navController: NavHostController,
    menuDesplegado: Boolean,
    userDesplegado: Boolean,
    viewModel: KrankenwagenViewModel,
    sesionViewModel: SesionViewModel,
    ambulancesViewModel: AmbulancesViewModel,
    hospitalViewModel: HospitalViewModel
) {
    val createAmb by viewModel.createAmb.collectAsState()
    val createHosp by viewModel.createHosp.collectAsState()
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
                onAmbTapped = {
                    viewModel.getAllAmb {
                        navController.navigate(Routes.PantallaAmbulances.route)
                    }
                },
                onHospTapped = {
                    viewModel.getAllHosp {
                        navController.navigate(Routes.PantallaHospitals.route)
                    }
                },
                onDocTapped = { navController.navigate(Routes.PantallaDocs.route) })
            Text(text = "Clinics")
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Card "Crear Hospital"
                    Card(
                        modifier = Modifier
                            .height(400.dp)
                            .width(300.dp)
                            .clickable { viewModel.acCreateHosp() }
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.barra_lateral_hosp),
                                contentDescription = "Crear Hospital",
                                modifier = Modifier.size(120.dp)
                            )
                            Text(
                                text = "Crear Hospital",
                                fontSize = 30.sp,
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    // Card "Crear Ambulancia"
                    Card(
                        modifier = Modifier
                            .height(400.dp)
                            .width(300.dp)
                            .clickable { viewModel.acCreateAmb() }
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.barra_lateral_amb),
                                contentDescription = "Crear Ambulancia",
                                modifier = Modifier.size(120.dp)
                            )
                            Text(
                                text = "Crear Ambulancia",
                                fontSize = 30.sp,
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
            when {
                menuDesplegado -> DialogMenu(viewModel, sesionViewModel)
                userDesplegado -> DialogSesion(viewModel, sesionViewModel)
                createAmb -> CreateAmbulance(ambulancesViewModel, viewModel)
                createHosp -> CreateHospital(hospitalViewModel, viewModel)
            }
        }
    }
}