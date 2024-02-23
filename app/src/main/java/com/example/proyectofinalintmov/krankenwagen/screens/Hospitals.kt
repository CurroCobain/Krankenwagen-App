package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.proyectofinalintmov.R
import com.example.proyectofinalintmov.barralateral.BarraLateral
import com.example.proyectofinalintmov.bienvenida.Bienvenida
import com.example.proyectofinalintmov.krankenwagen.data.Ambulance
import com.example.proyectofinalintmov.krankenwagen.data.Hospital
import com.example.proyectofinalintmov.krankenwagen.model.Routes
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel

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
    userRegisterd: Boolean
) {
    viewModel.getHosp()
    viewModel.getHosp()
    viewModel.getHosp()
    viewModel.getHosp()
    viewModel.getHosp()
    viewModel.getHosp()
    viewModel.getHosp()

    Scaffold(topBar = {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Bienvenida(
                bienvenidoADrHouseTextContent = "Bienvenido/a Dr ${viewModel.nombreDoc.value}"
            )
        }
    }, content = {
        ContenidoHospitals(
            navController = navController,
            menuDesplegado = showMenu,
            userDesplegado = userRegisterd,
            viewModel = viewModel
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
    viewModel: KrankenwagenViewModel
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
        Row(modifier = Modifier.fillMaxSize()) {
            // Barra de navegación lateral
            BarraLateral(
                onWelcTapped = { navController.navigate(Routes.PantallaWelcome.route) },
                onAmbTapped = { navController.navigate(Routes.PantallaAmbulances.route) },
                onHospTapped = { navController.navigate(Routes.PantallaHospitals.route) },
                onDocTapped = { navController.navigate(Routes.PantallaDocs.route) })
            Text(text = "Hospitals")
            Column(verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize())
            {
                // LazyRow con la lista de hospitales
                LazyHospital(
                    viewModel = viewModel,
                    arrangement = Arrangement.Center,
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                )
            }
            if (menuDesplegado) {
                DialogMenu(viewModel = viewModel)
            }
            if (userDesplegado) {
                DialogSesion(viewModel = viewModel)
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Suppress("UNUSED_PARAMETER")
@Composable
fun LazyHospital(
    viewModel: KrankenwagenViewModel,
    arrangement: Arrangement.HorizontalOrVertical,
    modifier: Modifier
) {
    val editar by viewModel.editHosp.collectAsState()
    val selectedHospital = remember { mutableStateOf<Hospital?>(null) }
    val context = LocalContext.current
    val miListaHosp by viewModel.listHospitals.collectAsState()
    LazyRow {
        items(miListaHosp) { hospital ->
            Card(modifier = Modifier
                .padding(20.dp).size(250.dp)
                .clickable {
                    selectedHospital.value = hospital
                    viewModel.activaEditHosp()
                })
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.barra_lateral_hosp),
                        contentDescription = "Hosp avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
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
    if (editar) {
        selectedHospital?.value?.let { hospital ->
            EditarHosp(viewModel = viewModel, hospital = hospital)
        }
    }
}





