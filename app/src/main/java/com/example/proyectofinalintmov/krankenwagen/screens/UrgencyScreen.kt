package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
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
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.SesionViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.UrgenciesViewModel
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

/**
 * Composable que alberga la pantalla de las urgencias
 */
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun UrgencyScreen(
    navController: NavHostController,
    viewModel: KrankenwagenViewModel,
    sesionViewModel: SesionViewModel,
    urgenciesViewModel: UrgenciesViewModel
) {
    // Estado del menú lateral de navegación
    val drawerState1 = rememberDrawerState(initialValue = DrawerValue.Closed)
    // Estado del menú lateral de usuario
    val drawerState2 = rememberDrawerState(initialValue = DrawerValue.Closed)
    // Booleano que controla el estado del diálogo de creación de urgencias
    val createUrg by viewModel.createUrg.collectAsState()
    // Booleano que controla el estado del diálogo de edición de urgencias
    val editUrg by viewModel.editUrg.collectAsState()
    val context = LocalContext.current
    // listado de urgencias actuales
    val miListaUrg by viewModel.listUrgencies.collectAsState()
    val updatedInfo by viewModel.updatedInfo.collectAsState()
    // booleano que filtra las urgencias finalizadas o sin terminar según su valor
    val filteredUrgencies by viewModel.filteredUrgencies.collectAsState()

    // Composable que sirve para generar el menú lateral de navegación en la app
    ModalNavigationDrawer(
        drawerState = drawerState1,
        drawerContent = {
            //  Desplegable del menú lateral
            ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.3f)) {
                // Contenido del menú lateral
                NavigationMenu(navController, viewModel, sesionViewModel)
            }
        }
    ) {
        Text(text = updatedInfo.toString(), color = Color.Transparent)
        // Invierte el contenido de la app para poder generar un segundo menú lateral en el lado contrario y que se despliegue de forma inversa
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            // Composable que sirve para generar el segundo menú lateral de navegación en la app
            ModalNavigationDrawer(
                drawerState = drawerState2,
                drawerContent = {
                    // Contenido del segundo menú lateral
                    ModalDrawerSheet(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .fillMaxHeight()
                    ) {
                        // Invierte el contenido del menú de usuario para que aparezca de izquierda a derecha
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            SesionMenu(sesionViewModel = sesionViewModel)
                        }
                    }
                }
            ) {
                // Invierte el contenido de la app para que aparezca de izquierda a derecha
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    PrevContUrgencies(
                        viewModel,
                        sesionViewModel,
                        drawerState1,
                        drawerState2,
                        urgenciesViewModel,
                        createUrg,
                        editUrg,
                        miListaUrg,
                        context,
                        filteredUrgencies
                    )
                }
            }
        }
    }
}

/**
 * Composable que muestra el contenido de la pantalla de urgencias y ayuda a conformar el menú inferior
 */
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PrevContUrgencies(
    viewModel: KrankenwagenViewModel,
    sesionViewModel: SesionViewModel,
    drawerState1: DrawerState,
    drawerState2: DrawerState,
    urgenciesViewModel: UrgenciesViewModel,
    createUrg: Boolean,
    editUrg: Boolean,
    miListaUrg: MutableList<Urgencia>,
    context: Context,
    filteredUrgencies: Boolean
) {
    // Se almacena el nombre del Dr para mostrarlo en pantalla
    val nombreDocReg by sesionViewModel.nombreDoc.collectAsState()
    // Scaffold que compone la pantalla
    Scaffold(topBar = {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Bienvenida(
                bienvenidoADrHouseTextContent = "Bienvenido/a Dr $nombreDocReg",
                modifier = Modifier
                    .wrapContentSize()
                    .fillMaxWidth(0.35f)
                    .padding(8.dp)
            )
        }
    }, content = {
        // Contenido del Scaffold
        ContenidoUrgencies(
            viewModel,
            urgenciesViewModel,
            miListaUrg,
            filteredUrgencies
        )
        // cuando se modifica alguno de los valores que los gestionan se muestran los diálogos de edición y creación
        when {
            createUrg -> {
                CreateUrgScreen(
                    context,
                    viewModel,
                    urgenciesViewModel
                )
            }

            editUrg -> {
                EditUrg(
                    context,
                    viewModel,
                    urgenciesViewModel
                )
            }
        }
    }, bottomBar = {
        // Barra para acceder al menú y a las opciones de sesión
        BarraMenu(drawerState1, drawerState2)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                // Botón para activar el diálogo de creación
                Button(
                    onClick = { viewModel.acCreateUrg() },
                    colors = ButtonDefaults.buttonColors(Color.White),
                    modifier = Modifier.border(
                        width = 4.dp, color = Color.Black,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    ),
                    shape = RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp,
                        bottomStart = 8.dp,
                        bottomEnd = 8.dp
                    )
                ) {
                    Text(
                        text = "Crear urgencia",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp,
                        color = Color.Black
                    )
                }
                // Botón para actualizar el listado de las urgencias
                Button(
                    onClick = {
                        viewModel.getUrgencies {
                            viewModel.increaseUpdateInfo()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color.White),
                    modifier = Modifier.border(
                        width = 4.dp, color = Color.Black,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    ),
                    shape = RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp,
                        bottomStart = 8.dp,
                        bottomEnd = 8.dp
                    )
                ) {
                    Text(
                        text = "Actualizar datos",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp,
                        color = Color.Black
                    )
                }
            }
        }
    })

}

/**
 * Composable que muestra el contenido de la pantalla de Urgencias
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContenidoUrgencies(
    viewModel: KrankenwagenViewModel,
    urgenciesViewModel: UrgenciesViewModel,
    miListaUrg: MutableList<Urgencia>,
    filteredUrgencies: Boolean
) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        // Fondo de la pantalla
        Image(
            painter = painterResource(id = R.drawable.newfondo),
            contentScale = ContentScale.FillWidth,
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize()
        )
        // Columna principal
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.76f)
        ) {
            Row (
                Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp)) {
                Button(
                    onClick = {
                        viewModel.setFiltered()
                        viewModel.getUrgencies {
                            viewModel.increaseUpdateInfo()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color.White),
                    modifier = Modifier
                        .border(
                            width = 4.dp, color = Color.Black,
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )
                        )
                        .sizeIn(minWidth = 200.dp),
                    shape = RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp,
                        bottomStart = 8.dp,
                        bottomEnd = 8.dp
                    )
                ) {
                Text(text = if (!filteredUrgencies) "Ver finalizadas" else "Ver activas",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    fontSize = 26.sp)
            }
                Spacer(modifier = Modifier.padding(start = 280.dp))
                Text(text = if (!filteredUrgencies) "Urgencias activas" else " Urgencias finalizadas",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    fontSize = 26.sp)
            }
            Spacer(modifier = Modifier.padding(8.dp))
            // Cabecera con el tipo de datos de cada columna
            Row(
                modifier = Modifier
                    .padding(start = 30.dp, end = 28.dp)
                    .background(Color.Gray, RoundedCornerShape(6.dp))
                    .fillMaxWidth()
            ) {
                // Id de la urgencia
                Text(
                    text = "ID",
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(8.dp),
                    color = Color.White,
                    fontSize = 20.sp
                )
                // Prioridad e la urgencia
                Text(
                    text = "P",
                    modifier = Modifier
                        .weight(0.1f)
                        .padding(8.dp),
                    color = Color.White,
                    fontSize = 20.sp
                )
                // Ambulancia
                Text(
                    text = "Ambulancia",
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(8.dp),
                    color = Color.White,
                    fontSize = 20.sp
                )
                // Nombre del paciente
                Text(
                    text = "Nombre",
                    modifier = Modifier
                        .weight(0.6f)
                        .padding(8.dp),
                    color = Color.White,
                    fontSize = 20.sp
                )
                // Edad del paciente
                Text(
                    text = "Edad",
                    modifier = Modifier
                        .weight(0.2f)
                        .padding(8.dp),
                    color = Color.White,
                    fontSize = 20.sp
                )
                // Dirección de la urgencia
                Text(
                    text = "Dirección",
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(8.dp),
                    color = Color.White,
                    fontSize = 20.sp
                )
                // Hora de creación de la urgencia
                Text(
                    text = "Fecha y hora",
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(8.dp),
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
            // Lary vertical grid con el listado de urgencias
            LazyUrgency(
                viewModel,
                urgenciesViewModel,
                miListaUrg
            )
        }
    }
}

/**
 * Composable que muestra un lazy vertical grid con la lista de urgencias
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LazyUrgency(
    viewModel: KrankenwagenViewModel,
    urgenciesViewModel: UrgenciesViewModel,
    miListaUrg: MutableList<Urgencia>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(horizontal = 5.dp, vertical = 5.dp)
    ) {
        // Item urgencia
        items(miListaUrg) { urgency ->
            // si no tiene ambulancia asignada aparece en blanco, si ya está siendo gestionada aparece verde
            Row(
                modifier = Modifier
                    .padding(start = 30.dp, top = 10.dp, end = 28.dp)
                    .border(width = 1.dp, color = Color.Black)
                    .background(
                        color = if (urgency.ambulance != "No definida") Color.LightGray
                        else Color.White,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .fillMaxWidth()
                    // Si pulsamos sobre la urgencia se nos abre el diálogo de edición de la misma
                    .clickable {
                        viewModel.activaEditUrg()
                        urgenciesViewModel.setAll(urgency)
                    }
            ) {
                // Identificador de la urgencia
                Text(
                    text = urgency.id,
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(8.dp),
                    fontSize = 20.sp
                )
                // Prioridad de la urgencia
                Text(
                    text = urgency.priority.toString(),
                    modifier = Modifier
                        .weight(0.1f)
                        .padding(8.dp),
                    fontSize = 20.sp
                )
                // Ambulancia
                Text(
                    text = urgency.ambulance,
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(8.dp),
                    fontSize = 20.sp
                )
                // Nombre del paciente
                Text(
                    text = urgency.name,
                    modifier = Modifier
                        .weight(0.6f)
                        .padding(8.dp),
                    fontSize = 20.sp
                )
                // Edad del paciente
                Text(
                    text = urgency.age.toString(),
                    modifier = Modifier
                        .weight(0.2f)
                        .padding(8.dp),
                    fontSize = 20.sp
                )
                // Dirección del paciente
                Text(
                    text = urgency.address,
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(8.dp),
                    fontSize = 20.sp
                )
                // Fecha y hora
                Text(
                    text = dateToString(urgency.date!!),
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(8.dp),
                    fontSize = 20.sp
                )
            }
        }
    }
}


@SuppressLint("SimpleDateFormat")
private fun dateToString(date: Timestamp): String{
    val sdf = SimpleDateFormat("HH:mm:ss dd/MM")
    val todate = date.toDate()
    return sdf.format(todate)
}