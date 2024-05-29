package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectofinalintmov.R
import com.example.proyectofinalintmov.bienvenida.Bienvenida
import com.example.proyectofinalintmov.krankenwagen.data.Urgencia
import com.example.proyectofinalintmov.krankenwagen.viewModels.AmbulancesViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.HospitalViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.SesionViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.UrgenciesViewModel

/**
 * Composable que alberga la pantalla de las urgencias
 */
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun UrgencyScreen(
    navController: NavHostController,
    viewModel: KrankenwagenViewModel,
    userRegistered: Boolean,
    sesionViewModel: SesionViewModel,
    ambulancesViewModel: AmbulancesViewModel,
    hospitalViewModel: HospitalViewModel,
    urgenciesViewModel: UrgenciesViewModel
) {
    val drawerState1 = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerState2 = rememberDrawerState(initialValue = DrawerValue.Closed)
    val createUrg by viewModel.createUrg.collectAsState()
    val context = LocalContext.current
    val miListaUrg by viewModel.listUrgencies.collectAsState()

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
                        urgenciesViewModel,
                        createUrg,
                        miListaUrg,
                        context
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
    urgenciesViewModel: UrgenciesViewModel,
    createUrg: Boolean,
    miListaUrg: MutableList<Urgencia>,
    context: Context
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
            navController,
            userRegistered,
           viewModel,
            sesionViewModel,
            ambulancesViewModel,
            hospitalViewModel,
            urgenciesViewModel,
            miListaUrg
        )
        if(createUrg){
            CreateUrgScreen(context,
                viewModel,
                urgenciesViewModel
            )
        }
    }, bottomBar = {
        // Barra para acceder al menú y a las opciones de sesión
        BarraMenu(viewModel = viewModel, drawerState1, drawerState2)
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ){
            Button(onClick = { viewModel.acCreateUrg() },
                colors = ButtonDefaults.buttonColors(Color(74, 121, 66)),
                shape = RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 8.dp,
                    bottomStart = 8.dp,
                    bottomEnd = 8.dp
                )
            ) {
                Text(text = "Crear urgencia",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp
                )
            }
        }
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
    urgenciesViewModel: UrgenciesViewModel,
    miListaUrg: MutableList<Urgencia>
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
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.76f)
                .padding(top = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 30.dp, end = 28.dp)
                    .background(Color.Gray, RoundedCornerShape(6.dp))
                    .fillMaxWidth()
            ) {
                Text(
                    text = "ID",
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(8.dp),
                    color = Color.White,
                    fontSize = 20.sp
                )
                Text(
                    text = "Prioridad",
                    modifier = Modifier
                        .weight(0.4f)
                        .padding(8.dp),
                    color = Color.White,
                    fontSize = 20.sp
                )
                Text(
                    text = "Nombre",
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(8.dp),
                    color = Color.White,
                    fontSize = 20.sp
                )
                Text(
                    text = "Edad",
                    modifier = Modifier
                        .weight(0.2f)
                        .padding(8.dp),
                    color = Color.White,
                    fontSize = 20.sp
                )
                Text(
                    text = "Dirección",
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
            LazyUrgency(
                viewModel,
                miListaUrg
            )
        }
    }
}

@Composable
fun LazyUrgency(
    viewModel: KrankenwagenViewModel,
    miListaUrg: MutableList<Urgencia>
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(horizontal = 5.dp, vertical = 5.dp)
    ) {
        // Item urgencia
        items(miListaUrg) { urgency ->
            Row(
                modifier = Modifier
                    .padding(start = 30.dp, top = 10.dp, end = 28.dp)
                    .background(
                        color = if (!urgency.complete) Color.White else Color.Red,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .fillMaxWidth()
                    .clickable {
                        viewModel.activaEditAmb()
                    }
            ) {
                Text(
                    text = urgency.id,
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(8.dp),
                    fontSize = 20.sp
                )
                Text(
                    text = urgency.priority.toString(),
                    modifier = Modifier
                        .weight(0.4f)
                        .padding(8.dp),
                    fontSize = 20.sp
                )
                Text(
                    text = urgency.name,
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(8.dp),
                    fontSize = 20.sp
                )
                Text(
                    text = urgency.age.toString(),
                    modifier = Modifier
                        .weight(0.2f)
                        .padding(8.dp),
                    fontSize = 20.sp
                )
                Text(
                    text = urgency.address,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    fontSize = 20.sp
                )
            }
        }
    }
}
