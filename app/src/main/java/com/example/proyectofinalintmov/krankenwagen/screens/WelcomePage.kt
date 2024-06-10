package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
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
import com.example.proyectofinalintmov.krankenwagen.model.Routes
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.SesionViewModel
import com.example.proyectofinalintmov.scrollprovincias.ScrollProvincias



/**
 * Scaffold que alberga la página de bienvenida
 */
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint(
    "RememberReturnType", "UnusedMaterial3ScaffoldPaddingParameter",
    "StateFlowValueCalledInComposition"
)
@Composable
fun WelcomePage(
    navController: NavHostController,
    viewModel: KrankenwagenViewModel,
    sesionViewModel: SesionViewModel
) {
    // Estado del menú lateral de navegación
    val drawerState1 = rememberDrawerState(initialValue = DrawerValue.Closed)
    // Estado del menú lateral de usuario
    val drawerState2 = rememberDrawerState(initialValue = DrawerValue.Closed)

    // Composable que sirve para generar el menú lateral de navegación en la app
    ModalNavigationDrawer(
        drawerState = drawerState1,
        drawerContent = {
            //  Desplegable del menú lateral
            ModalDrawerSheet( modifier = Modifier.fillMaxWidth(0.3f)) {
                // Contenido del menú lateral
                NavigationMenu(navController, viewModel, sesionViewModel)
            }
        }
    ) {
        // Invierte el contenido de la app para poder generar un segundo menú lateral en el lado contrario y que se despliegue de forma inversa
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl ){
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
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr ){
                            SesionMenu(sesionViewModel = sesionViewModel)
                        }
                    }
                }
            ) {
                // Invierte el contenido de la app para que aparezca de izquierda a derecha
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr ) {
                    PrevContWelc(
                        navController,
                        viewModel,
                        sesionViewModel,
                        drawerState1,
                        drawerState2
                    )
                }
            }
        }
    }
}

/**
 * Composable que muestra el contenido de la pantalla de bienvenida y ayuda a conformar el menú inferior
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PrevContWelc(
    navController: NavHostController,
    viewModel: KrankenwagenViewModel,
    sesionViewModel: SesionViewModel,
    drawerState1: DrawerState,
    drawerState2: DrawerState

){
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
                modifier = Modifier.wrapContentSize()
                    .fillMaxWidth(0.35f)
                    .padding(8.dp)
            )
        }
    }, content = {
        // Contenido del Scaffold
        ContenidoWelcome(
            navController = navController,
            viewModel = viewModel,
            sesionViewModel = sesionViewModel
        )
    }, bottomBar = {
        // Barra para acceder al menú y a las opciones de sesión
        BarraMenu(drawerState1, drawerState2)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            // Botón para desplegar el diálogo de creación de ambulancias
            Button(
                onClick = { viewModel.closeApp() },
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
                    text = "Salir de la aplicación",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    color = Color.Black
                )
            }
        }
    })
}

/**
 * Composable que muestra el contenido de la pantalla de bienvenida.
 */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ContenidoWelcome(
    navController: NavHostController,
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
            painter = painterResource(id = R.drawable.newfondo),
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
    }
}
