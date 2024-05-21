package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
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
import com.example.proyectofinalintmov.krankenwagen.viewModels.UrgenciesViewModel

/**
 * Composable que alberga las opciones de crear ambulancia y crear hospital
 */
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun Create(
    navController: NavHostController,
    viewModel: KrankenwagenViewModel,
    showMenu: Boolean,
    userRegistered: Boolean,
    sesionViewModel: SesionViewModel,
    ambulancesViewModel: AmbulancesViewModel,
    hospitalViewModel: HospitalViewModel,
    urgenciesViewModel: UrgenciesViewModel
) {
    val drawerState1 = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerState2 = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState1,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.3f)) {
                NavigationMenu(navController, viewModel)
            }
        }
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            ModalNavigationDrawer(
                drawerState = drawerState2,
                drawerContent = {
                    ModalDrawerSheet(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .fillMaxHeight()
                    ) {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            DialogSesion(viewModel = viewModel, sesionViewModel = sesionViewModel)
                        }
                    }
                }
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    PrevContCreate(
                        navController,
                        userRegistered,
                        viewModel,
                        sesionViewModel,
                        ambulancesViewModel,
                        hospitalViewModel,
                        drawerState1,
                        drawerState2,
                        urgenciesViewModel
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PrevContCreate(
    navController: NavHostController,
    userRegistered: Boolean,
    viewModel: KrankenwagenViewModel,
    sesionViewModel: SesionViewModel,
    ambulancesViewModel: AmbulancesViewModel,
    hospitalViewModel: HospitalViewModel,
    drawerState1: DrawerState,
    drawerState2: DrawerState,
    urgenciesViewModel: UrgenciesViewModel
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
            userDesplegado = userRegistered,
            viewModel = viewModel,
            sesionViewModel = sesionViewModel,
            ambulancesViewModel = ambulancesViewModel,
            hospitalViewModel = hospitalViewModel,
            urgenciesViewModel = urgenciesViewModel
        )
    }, bottomBar = {
        // Barra para acceder al menú y a las opciones de sesión
        BarraMenu(viewModel = viewModel, drawerState1, drawerState2)
    })

}

/**
 * Composable que muestra el contenido de la pantalla de Centros de Salud.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContenidoCreate(
    navController: NavHostController,
    userDesplegado: Boolean,
    viewModel: KrankenwagenViewModel,
    sesionViewModel: SesionViewModel,
    ambulancesViewModel: AmbulancesViewModel,
    hospitalViewModel: HospitalViewModel,
    urgenciesViewModel: UrgenciesViewModel
) {
    // Se almacena el estado de createAmb para desplegar el diálogo de creación en función de dicho valor
    val createAmb by viewModel.createAmb.collectAsState()
    // Se almacena el estado de createHosp para desplegar el diálogo de creación en función de dicho valor
    val createHosp by viewModel.createHosp.collectAsState()
    // Se almacena el estado de createUrg para desplegar el diálogo de creación en función de dicho valor
    val createUrg by viewModel.createUrg.collectAsState()
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
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
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
                Spacer(modifier = Modifier.padding(20.dp))
                // Card "Crear Urgencia"
                Card(
                    modifier = Modifier
                        .height(400.dp)
                        .width(300.dp)
                        .clickable { viewModel.acCreateUrg() }
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Imagen de hospital
                        Image(
                            painter = painterResource(id = R.drawable.urgencia),
                            contentDescription = "Crear urgencia",
                            modifier = Modifier.size(120.dp)
                        )
                        Text(
                            text = "Crear urgencia",
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
            createAmb -> CreateAmbulance(ambulancesViewModel, viewModel)
            createHosp -> CreateHospital(hospitalViewModel, viewModel)
            createUrg -> CreateUrg(context, viewModel, urgenciesViewModel,
                modifier = Modifier.border(2.dp, Color.Black, shape = MaterialTheme.shapes.medium),
            )
        }
    }
}
