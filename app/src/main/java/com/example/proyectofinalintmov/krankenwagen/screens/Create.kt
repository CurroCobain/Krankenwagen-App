package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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

/**
 * Composable que alberga las opciones de crear ambulancia y crear hospital
 */
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
    // Se almacena el nombre del Dr para mostrarlo en pantalla
    val nombreDocReg by sesionViewModel.nombreDoc.collectAsState()

    // Scaffold que compone la pantalla
    Scaffold(topBar = {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Bienvenida(
                bienvenidoADrHouseTextContent = "Bienvenido/a Dr $nombreDocReg"
            )
        }
    }, content = {
        // Contenido del Scaffold
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
        // Barra para acceder al menú y a las opciones de sesión
        BarraMenu(viewModel = viewModel)
    })

}

/**
 * Composable que muestra el contenido de la pantalla de Centros de Salud.
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
    // Se almacena el estado de createAmb para desplegar el diálogo de creación en función de dicho valor
    val createAmb by viewModel.createAmb.collectAsState()
    // Se almacena el estado de createHosp para desplegar el diálogo de creación en función de dicho valor
    val createHosp by viewModel.createHosp.collectAsState()
    val context = LocalContext.current
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
            // Barra de navegación lateral
            BarraLateral(
                // Icono de inicio
                onWelcTapped = {
                    // Si el nombre del Dr no está vacío se entiende que la sesión ha sido iniciada y se permite la navegación
                    if (sesionViewModel.nombreDoc.value.isNotEmpty()) {
                        viewModel.setProv("")
                        navController.navigate(Routes.PantallaWelcome.route)
                    } else {
                        // Si no se ha iniciado sesión se manda mensaje de error
                        Toast.makeText(
                            context,
                            "Debe iniciar sesión para poder acceder a la base de datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                // Icono de Ambulancias
                onAmbTapped = {
                    // Si el nombre del Dr no está vacío se entiende que la sesión ha sido iniciada y se permite la navegación
                    if (sesionViewModel.nombreDoc.value.isNotEmpty()) {
                        viewModel.getAllAmb {
                            navController.navigate(Routes.PantallaAmbulances.route)
                        }
                    } else {
                        // Si no se ha iniciado sesión se manda mensaje de error
                        Toast.makeText(
                            context,
                            "Debe iniciar sesión para poder acceder a la base de datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                // Icono de Hospitales
                onHospTapped = {
                    // Si el nombre del Dr no está vacío se entiende que la sesión ha sido iniciada y se permite la navegación
                    if (sesionViewModel.nombreDoc.value.isNotEmpty()) {
                        viewModel.getAllHosp {
                            navController.navigate(Routes.PantallaHospitals.route)
                        }
                    } else {
                        // Si no se ha iniciado sesión se manda mensaje de error
                        Toast.makeText(
                            context,
                            "Debe iniciar sesión para poder acceder a la base de datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                // Icono de Creación
                onAddTapped = {
                    // Si el nombre del Dr no está vacío se entiende que la sesión ha sido iniciada y se permite la navegación
                    if (sesionViewModel.nombreDoc.value.isNotEmpty()) {
                        navController.navigate(Routes.PantallaCreate.route)
                    } else {
                        // Si no se ha iniciado sesión se manda mensaje de error
                        Toast.makeText(
                            context,
                            "Debe iniciar sesión para poder acceder a la base de datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
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
                            // Imagen de hospital
                            Image(
                                painter = painterResource(id = R.drawable.hospital),
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
                            // Imagen de ambulancia
                            Image(
                                painter = painterResource(id = R.drawable.ambulancia),
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
            // Cuando se modifica alguna de las variables que lo gestionan se muestran los distintos menús
            when {
                menuDesplegado -> DialogMenu(viewModel, sesionViewModel)
                userDesplegado -> DialogSesion(viewModel, sesionViewModel)
                createAmb -> CreateAmbulance(ambulancesViewModel, viewModel)
                createHosp -> CreateHospital(hospitalViewModel, viewModel)
            }
        }
    }
}