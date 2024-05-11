package com.example.proyectofinalintmov.krankenwagen.screens


import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.proyectofinalintmov.krankenwagen.model.Routes
import com.example.proyectofinalintmov.krankenwagen.viewModels.AmbulancesViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.SesionViewModel


/**
 * Composable que muestra la pantalla principal de las Ambulancias
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun Ambulances(
    navController: NavHostController,
    viewModel: KrankenwagenViewModel,
    showMenu: Boolean,
    userRegisterd: Boolean,
    sesionViewModel: SesionViewModel,
    ambulancesViewModel: AmbulancesViewModel

) {
    // Se almacena el nombre del Dr para mostrarlo en pantalla
    val nombreDocReg by sesionViewModel.nombreDoc.collectAsState()

    // Scaffold que compone la pantalla
    Scaffold(topBar = {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Mensaje de bienvenida al Dr, se muestra el nombre una vez iniciada la sesión
            Bienvenida(
                bienvenidoADrHouseTextContent = "Bienvenido/a Dr $nombreDocReg"
            )
        }
    }, content = {
        // Contenido del Scaffold
        ContenidoAmbulances(
            navController = navController,
            menuDesplegado = showMenu,
            userDesplegado = userRegisterd,
            viewModel = viewModel,
            sesionViewModel = sesionViewModel,
            ambulancesViewModel = ambulancesViewModel
        )
    }, bottomBar = {
        // Barra para acceder al menú y a las opciones de sesión
        //BarraMenu(viewModel = viewModel)
    })

}

/**
 * Composable que crea el contenido de la pantalla de Ambulancias
 */
@Composable
fun ContenidoAmbulances(
    navController: NavHostController,
    menuDesplegado: Boolean,
    userDesplegado: Boolean,
    viewModel: KrankenwagenViewModel,
    ambulancesViewModel: AmbulancesViewModel,
    sesionViewModel: SesionViewModel
) {
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
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxSize()
            ) {
                // LazyVerticalGrid con la lista de ambulancias
                LazyAmbulance(
                    viewModel = viewModel,
                    ambulancesViewModel = ambulancesViewModel,
                    arrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        // Cuando se modifica alguna de las variables que lo gestionan se muestran los distintos menús
        when {
            menuDesplegado -> DialogMenu(viewModel, sesionViewModel)
            userDesplegado -> DialogSesion(viewModel, sesionViewModel)
        }
    }
}

/**
 * Composable que muestra las distintas ambulancias de la base de datos
 */
@SuppressLint("StateFlowValueCalledInComposition")
@Suppress("UNUSED_PARAMETER")
@Composable
fun LazyAmbulance(
    viewModel: KrankenwagenViewModel,
    ambulancesViewModel: AmbulancesViewModel,
    arrangement: Arrangement.HorizontalOrVertical,
    modifier: Modifier
) {
    // variable que se usa para desplegar el mnú de edición de las ambulancias
    val editar by viewModel.editAmb.collectAsState()
    // lista de ambulancias que se envía al lazyVerticalGrid
    val miListaAmb by viewModel.listAmbulancias.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
    ) {
        // Item ambulancia conformado por una imagen y la matrícula de la ambulancia
        items(miListaAmb) { ambulance ->
            Card(modifier = Modifier
                .padding(50.dp)
                .size(250.dp)
                .clickable {
                    // Si hacemos click se asignan los valores de la ambulancia al ambulancesviewModel para su gestión
                    ambulancesViewModel.asignAmbFields(ambulance)
                    // Se activa el diálogo de edición
                    viewModel.activaEditAmb()
                })
            {
                // Columna que alberga la imagen y la matrícula de la ambulancia
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxSize()
                        // Si la ambulancia está ocupada cambiamos el fondo a color rojo
                        .background(
                            color = if (!ambulance.isFree)
                                Color.Red
                            else
                                Color.Transparent
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .fillMaxHeight(0.85f),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Imagen de la ambulancia
                        Image(
                            painter = painterResource(id = R.drawable.ambulancia),
                            contentDescription = "Hosp avatar",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                    Row {
                        // Matrícula de la ambulancia
                        Text(
                            text = ambulance.plate,
                            fontSize = 30.sp
                        )
                    }
                }
            }
        }
    }
    // Si se modifica el valor de editar abrimos el diálogo de edición
    if (editar) {
        EditarAmb(viewModel, ambulancesViewModel)
    }
}
