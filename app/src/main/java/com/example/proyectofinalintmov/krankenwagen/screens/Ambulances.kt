package com.example.proyectofinalintmov.krankenwagen.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectofinalintmov.R
import com.example.proyectofinalintmov.barralateral.BarraLateral
import com.example.proyectofinalintmov.bienvenida.Bienvenida
import com.example.proyectofinalintmov.krankenwagen.data.Ambulance
import com.example.proyectofinalintmov.krankenwagen.data.Hospital
import com.example.proyectofinalintmov.krankenwagen.model.Routes
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun Ambulances(
    navController: NavHostController,
    viewModel: KrankenwagenViewModel,
    showMenu: Boolean,
    userRegisterd: Boolean

) {
    viewModel.getAmb()
    viewModel.getAmb()
    viewModel.getAmb()
    viewModel.getAmb()
    viewModel.getAmb()
    viewModel.getAmb()
    viewModel.getAmb()
    viewModel.getAmb()
    viewModel.getAmb()
    viewModel.getAmb()
    viewModel.getAmb()
    viewModel.getAmb()

    Scaffold(topBar = {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Bienvenida(
                bienvenidoADrHouseTextContent = "Bienvenido/a Dr ${viewModel.nombreDoc.value}"
            )
        }
    }, content = {
        ContenidoAmbulances(
            navController = navController,
            menuDesplegado = showMenu,
            userDesplegado = userRegisterd,
            viewModel = viewModel
        )
    }, bottomBar = {
        BarraMenu(viewModel = viewModel)
    })

}

@Composable
fun ContenidoAmbulances(
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
            Text(text = "Ambulances")
            Column (verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxSize()
            ) {
                // LazyVerticalGrid con la lista de ambulancias
                LazyAmbulance(
                    viewModel = viewModel,
                    arrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize() )
            }
        }
        if (menuDesplegado) {
            DialogMenu(viewModel = viewModel)
        }
        if (userDesplegado) {
            DialogSesion(viewModel = viewModel)
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Suppress("UNUSED_PARAMETER")
@Composable
fun LazyAmbulance(
    viewModel: KrankenwagenViewModel,
    arrangement: Arrangement.HorizontalOrVertical,
    modifier: Modifier
) {
    val editar by viewModel.editAmb.collectAsState()
    val selectedAmbulance = remember { mutableStateOf<Ambulance?>(null) }
    val context = LocalContext.current
    val miListaAmb by viewModel.listAmbulancias.collectAsState()
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
    ){
        items(miListaAmb) { ambulance ->
            Card(modifier = Modifier
                .padding(50.dp)
                .size(250.dp)
                .clickable {
                    selectedAmbulance.value = ambulance
                    viewModel.activaEditAmb()
                })
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.background(
                        color = if (!ambulance.isFree)
                            Color.Red
                        else
                            Color.Transparent
                    )) {
                    Image(
                        painter = painterResource(id = R.drawable.barra_lateral_amb),
                        contentDescription = "Hosp avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(200.dp)
                    )
                    Text(
                        text = ambulance.plate,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
    if (editar) {
        selectedAmbulance?.value?.let { ambulance ->
            EditarAmb(viewModel = viewModel, ambulance = ambulance)
        }
    }
}