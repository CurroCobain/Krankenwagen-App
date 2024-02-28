package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectofinalintmov.R
import com.example.proyectofinalintmov.barralateral.BarraLateral
import com.example.proyectofinalintmov.bienvenida.Bienvenida
import com.example.proyectofinalintmov.krankenwagen.data.AmbulanceTypes
import com.example.proyectofinalintmov.krankenwagen.model.Routes
import com.example.proyectofinalintmov.krankenwagen.viewModels.AmbulancesViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.HospitalViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.SesionViewModel

/**
 * Scaffold que alberga la página de hospitales
 * Recibe los siguientes parámetros
 * @param navController Controlador de navegación para manejar las transiciones entre pantallas.
 * @param menuDesplegado Indica si el menú está desplegado o no.
 * @param userDesplegado Indica si el diálogo de sesión de usuario está desplegado o no.
 * @param viewModel El ViewModel asociado a la pantalla de bienvenida.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun Hospitals(
    navController: NavHostController,
    viewModel: KrankenwagenViewModel,
    showMenu: Boolean,
    userRegisterd: Boolean,
    sesionViewModel: SesionViewModel,
    hospitalViewModel: HospitalViewModel,
    ambulancesViewModel: AmbulancesViewModel,
    editHosp: Boolean,
    editAmb: Boolean
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
        ContenidoHospitals(
            navController = navController,
            menuDesplegado = showMenu,
            userDesplegado = userRegisterd,
            viewModel = viewModel,
            sesionViewModel = sesionViewModel,
            hospitalViewModel = hospitalViewModel,
            editHosp = editHosp,
            ambulancesViewModel = ambulancesViewModel,
            editAmb = editAmb
        )
    }, bottomBar = {
        BarraMenu(viewModel = viewModel)
    })

}

/**
 * Composable que muestra el contenido de la pantalla de hospitales.
 * Recibe los siguientes parámetros
 * @param navController Controlador de navegación para manejar las transiciones entre pantallas.
 * @param menuDesplegado Indica si el menú está desplegado o no.
 * @param userDesplegado Indica si el diálogo de sesión de usuario está desplegado o no.
 * @param viewModel El ViewModel asociado a la pantalla de bienvenida.
 */
@Composable
fun ContenidoHospitals(
    navController: NavHostController,
    menuDesplegado: Boolean,
    userDesplegado: Boolean,
    viewModel: KrankenwagenViewModel,
    sesionViewModel: SesionViewModel,
    hospitalViewModel: HospitalViewModel,
    ambulancesViewModel: AmbulancesViewModel,
    editHosp: Boolean,
    editAmb: Boolean
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    val provincias = listOf("Almeria", "Cadiz", "Cordoba", "Granada", "Huelva", "Jaen", "Malaga", "Sevilla")
    val selectedProvincia = remember { mutableStateOf("Selecciona una provincia") }
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
                onWelcTapped = {
                    if (sesionViewModel.nombreDoc.value.isNotEmpty()) {
                        viewModel.setProv("")
                        navController.navigate(Routes.PantallaWelcome.route)
                    } else {
                        Toast.makeText(
                            context,
                            "Debe iniciar sesión para poder acceder a la base de datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                onAmbTapped = {
                    if (sesionViewModel.nombreDoc.value.isNotEmpty()) {
                        viewModel.getAllAmb {
                            navController.navigate(Routes.PantallaAmbulances.route)
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Debe iniciar sesión para poder acceder a la base de datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                onHospTapped = {
                    if (sesionViewModel.nombreDoc.value.isNotEmpty()) {
                        viewModel.getAllHosp {
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
                onAddTapped = {
                    if (sesionViewModel.nombreDoc.value.isNotEmpty()) {
                        navController.navigate(Routes.PantallaDocs.route)
                    } else {
                        Toast.makeText(
                            context,
                            "Debe iniciar sesión para poder acceder a la base de datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            )
            {
                Row(modifier = Modifier.padding(start = 20.dp)) {
                    Box(
                        modifier = Modifier
                            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                    ){
                        Text(
                            " Filtrar por provincia ->   ",
                            fontSize = 30.sp,
                            modifier = Modifier.background(color = Color.LightGray)
                        )
                    }

                    Column(modifier = Modifier.clickable(onClick = { expanded = true })) {
                            Text(
                                text = selectedProvincia.value,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(Color.White, RoundedCornerShape(2.dp)),
                                fontSize = 30.sp
                            )


                        Spacer(modifier = Modifier.padding(start = 8.dp, bottom =  8.dp))
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.width(IntrinsicSize.Max)
                        ) {
                            provincias.forEach { provincia ->
                                DropdownMenuItem(onClick = {
                                    selectedProvincia.value = provincia
                                    viewModel.setProv(selectedProvincia.value)
                                    viewModel.getHosp(selectedProvincia.value){
                                        expanded = false
                                    }
                                }) {
                                    Text(
                                        text = provincia,
                                        fontSize = 30.sp)
                                }
                            }
                        }
                    }
                }
                // LazyRow con la lista de hospitales
                LazyHospital(
                    viewModel = viewModel,
                    hospitalViewModel = hospitalViewModel,
                    arrangement = Arrangement.Center,
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    editHosp = editHosp
                )
            }
            when {
                menuDesplegado -> DialogMenu(viewModel, sesionViewModel)
                userDesplegado -> DialogSesion(viewModel, sesionViewModel)
                editHosp -> EditarHosp(viewModel, hospitalViewModel, ambulancesViewModel)
                editAmb -> EditarAmb(viewModel, ambulancesViewModel)
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Suppress("UNUSED_PARAMETER")
@Composable
fun LazyHospital(
    viewModel: KrankenwagenViewModel,
    hospitalViewModel: HospitalViewModel,
    arrangement: Arrangement.HorizontalOrVertical,
    modifier: Modifier,
    editHosp: Boolean
) {
    val miListaHosp by viewModel.listHospitals.collectAsState()
    LazyRow {
        items(miListaHosp) { hospital ->
            Card(modifier = Modifier
                .padding(20.dp)
                .size(250.dp)
                .clickable {
                    hospitalViewModel.asignHospFields(hospital) {
                        viewModel.activaEditHosp()
                    }
                })
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.barra_lateral_hosp),
                        contentDescription = "Hosp avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Text(
                        text = hospital.name,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}







