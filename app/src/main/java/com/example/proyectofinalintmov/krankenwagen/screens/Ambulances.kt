package com.example.proyectofinalintmov.krankenwagen.screens


import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectofinalintmov.R
import com.example.proyectofinalintmov.bienvenida.Bienvenida
import com.example.proyectofinalintmov.krankenwagen.viewModels.AmbulancesViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.SesionViewModel


/**
 * Composable que muestra la pantalla principal de las Ambulancias
 */
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun Ambulances(
    navController: NavHostController,
    viewModel: KrankenwagenViewModel,
    sesionViewModel: SesionViewModel,
    ambulancesViewModel: AmbulancesViewModel

) {
    // Estado del menú lateral de navegación
    val drawerState1 = rememberDrawerState(initialValue = DrawerValue.Closed)
    // Estado del menú lateral de usuario
    val drawerState2 = rememberDrawerState(initialValue = DrawerValue.Closed)
    // Booleano que controla el estado del diálogo de creación de ambulancias
    val createAmb by viewModel.createAmb.collectAsState()

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
                    PrevContAmb(
                        viewModel,
                        sesionViewModel,
                        ambulancesViewModel,
                        drawerState1,
                        drawerState2,
                        createAmb
                    )
                }
            }
        }
    }
}

/**
 * Composable que muestra el contenido de la pantalla ambulancias y ayuda a conformar el menú inferior
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PrevContAmb(
    viewModel: KrankenwagenViewModel,
    sesionViewModel: SesionViewModel,
    ambulancesViewModel: AmbulancesViewModel,
    drawerState1: DrawerState,
    drawerState2: DrawerState,
    createAmb: Boolean
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
        ContenidoAmbulances(
            viewModel = viewModel,
            ambulancesViewModel = ambulancesViewModel,
        )
        // Si cambia el valor de "createAmb" a true, muestra el diálogo de creación de ambulancias
        if (createAmb) {
            CreateAmbulance(ambulancesViewModel, viewModel)
        }
    }, bottomBar = {
        // Barra para acceder al menú y a las opciones de sesión
        BarraMenu(drawerState1 = drawerState1, drawerState2 = drawerState2)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            // Botón para desplegar el diálogo de creación de ambulancias
            Button(
                onClick = { viewModel.acCreateAmb() },
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
                    text = "Crear ambulancia",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 30.sp,
                    color = Color.Black
                )
            }
        }
    })
}

/**
 * Composable que crea el contenido de la pantalla de Ambulancias
 */
@Composable
fun ContenidoAmbulances(
    viewModel: KrankenwagenViewModel,
    ambulancesViewModel: AmbulancesViewModel,
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
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize(0.8f)
        ) {
            // LazyVerticalGrid con la lista de ambulancias
            LazyAmbulance(
                viewModel = viewModel,
                ambulancesViewModel = ambulancesViewModel,
                arrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize(),
                //miListaAmb
            )
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
    modifier: Modifier,
) {
    // variable que se usa para desplegar el mnú de edición de las ambulancias
    val editar by viewModel.editAmb.collectAsState()
    // listado de ambulancias
    val miListaAmb by viewModel.listAmbulancias.collectAsState()
    // LazyVerticalGrid que muestra las ambulancias
    LazyVerticalGrid(
        columns = GridCells.Fixed(3 ),
        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 6.dp)
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
                    modifier = Modifier
                        .fillMaxSize()
                        // Si la ambulancia está ocupada cambiamos el fondo a color rojo
                        .background(
                            color = if (!ambulance.free)
                                Color.Gray
                            else
                                Color(70, 130, 180)
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
                            fontSize = 30.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
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
