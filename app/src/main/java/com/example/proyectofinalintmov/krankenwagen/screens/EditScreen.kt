package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenuItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Switch
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.proyectofinalintmov.R
import com.example.proyectofinalintmov.krankenwagen.data.AmbulanceTypes
import com.example.proyectofinalintmov.krankenwagen.viewModels.AmbulancesViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.HospitalViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.UrgenciesViewModel
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

/**
 * Composable para la edición de hospitales
 */
@Composable
fun EditarHosp(
    viewModel: KrankenwagenViewModel,
    hospitalViewModel: HospitalViewModel,
    ambulancesViewModel: AmbulancesViewModel
) {
    val context = LocalContext.current
    // Variables para la edición de hospitales
    val idHosp by hospitalViewModel.idHosp.collectAsState() // Id del hospital
    val name by hospitalViewModel.name.collectAsState() // Nombre del hospital
    val county by hospitalViewModel.county.collectAsState() // Provincia del hospital
    val city by hospitalViewModel.city.collectAsState() // Ciudad del hospital
    val address by hospitalViewModel.address.collectAsState() // Dirección del hospital
    val listAmbs by viewModel.listAmbulancias.collectAsState() // Listado de ambulancias asociadas al hospital
    // Variable que se usa para indicar al sistema que busque las ambulancias asociadas y las muestre
    val muestrAmbs = remember { mutableStateOf(false) }
    // Variable que muestra la respuesta del sistema
    val message by hospitalViewModel.hospMessage.collectAsState()
    // Variable que muestra la provincia por la que se han filtrado los hospitales
    val prov by viewModel.provinciaFiltrar.collectAsState()

    // Diálogo de edición de hospitales
    Dialog(
        onDismissRequest = {
            viewModel.activaEditHosp()
            hospitalViewModel.resetFields()
        }
    ) {
        // Tarjeta paradar forma con las esquinas redondeadas al diálogo
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .fillMaxSize()
                        // Degradado del fondo de la pantalla
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFE0FFFF), // Light Cyan (rgb(224, 255, 255))
                                    Color(0xFF87CEEB), // Light Sky Blue (rgb(135, 206, 235))
                                    Color(0xFF4682B4)  // Steel Blue (rgb(70, 130, 180))
                                ),
                                start = Offset(0f, 0f),
                                end = Offset.Infinite
                            )
                        )
                        .verticalScroll(rememberScrollState(), true),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hospital),
                            contentDescription = "icono amb",
                            modifier = Modifier
                                .size(60.dp)
                                .padding(10.dp)
                        )
                        // Texto id del hospital, no editable
                        Text(
                            text = "Id -> $idHosp",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    }

                    // Texto provincia del hospital, no editable
                    Text(
                        text = "Provincia -> $county",
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold
                    )
                    // Texto ciudad del hospital, no editable
                    Text(
                        text = "Ciudad: $city",
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de edición para el nombre del hospital
                    TextField(
                        value = name,
                        onValueChange = { newValue ->
                            hospitalViewModel.setName(newValue)
                        },
                        label = {
                            Text(
                                "Nombre",
                                fontSize = 20.sp
                            )
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(0.9f),
                        textStyle = TextStyle(fontSize = 30.sp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.address),
                            contentDescription = "icono amb",
                            modifier = Modifier
                                .size(60.dp)
                                .padding(10.dp)
                        )
                        // Campo de edición para la dirección del hospital
                        TextField(
                            value = address,
                            onValueChange = { newValue ->
                                hospitalViewModel.setAddress(newValue)
                            },
                            label = {
                                Text(
                                    "Dirección",
                                    fontSize = 20.sp
                                )
                            },
                            modifier = Modifier.padding(8.dp),
                            textStyle = TextStyle(fontSize = 30.sp)
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        // Botón para actualizar el hospital
                        Button(
                            onClick = {
                                try {
                                    hospitalViewModel.updateHosp() {
                                        if(prov.isNotEmpty()){
                                            viewModel.getHosp(prov){
                                                viewModel.increaseUpdateInfo()
                                            }
                                        } else {
                                            viewModel.getAllHosp {
                                                viewModel.increaseUpdateInfo()
                                            }
                                        }
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Hubo un fallo en la operación",
                                        Toast.LENGTH_SHORT
                                    ).show()
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
                                .sizeIn(minWidth = 150.dp, minHeight = 40.dp),
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )
                        ) {
                            Text(
                                text = " Guardar ",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        // Botón para borrar el hospital actual
                        Button(
                            onClick = {
                                try {
                                    hospitalViewModel.deleteHosp(idHosp) {
                                        hospitalViewModel.ambulanceCoherence(idHosp)
                                        viewModel.increaseUpdateInfo()
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Hubo un fallo en la operación",
                                        Toast.LENGTH_SHORT
                                    ).show()
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
                                .sizeIn(minWidth = 150.dp, minHeight = 40.dp),
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )
                        ) {
                            Text(
                                text = " Borrar ",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Botón para mostrar la lista de ambulancias asociadas
                        Button(
                            onClick = {
                                try {
                                    viewModel.getAmb(idHosp) {
                                        muestrAmbs.value = true
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Hubo un fallo en la operación",
                                        Toast.LENGTH_SHORT
                                    ).show()
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
                                .sizeIn(minWidth = 150.dp, minHeight = 40.dp),
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )
                        ) {
                            Text(
                                text = "Ver ambulancias",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black
                            )
                        }
                        // Botón para volver a la pantalla anterior
                        Button(
                            onClick = {
                                try {
                                    if (viewModel.provinciaFiltrar.value.isEmpty()) {
                                        // Si el campo provincia está vacío se recuperan todos los hospitales
                                        viewModel.getAllAmb {
                                            viewModel.getAllHosp {
                                                hospitalViewModel.resetFields()
                                            }
                                        }
                                        viewModel.activaEditHosp()
                                    } else {
                                        // Recarga la lista de ambulancias y luego la de hospitales, resetea los campos de edición
                                        viewModel.getAllAmb {
                                            viewModel.getHosp(prov) {
                                                hospitalViewModel.resetFields()
                                                viewModel.increaseUpdateInfo()
                                            }
                                        }
                                        viewModel.activaEditHosp()
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Hubo un fallo en la operación",
                                        Toast.LENGTH_SHORT
                                    ).show()
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
                                .sizeIn(minWidth = 150.dp, minHeight = 40.dp),
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )
                        ) {
                            Text(
                                text = "Volver",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Row(horizontalArrangement = Arrangement.Center) {
                        // Mensaje del sistema
                        Text(
                            text = message,
                            fontStyle = FontStyle.Italic,
                            color = Color.Red
                        )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxSize()
                )
                {
                    Spacer(modifier = Modifier.padding(10.dp))
                    // Columna que muestra la lista de ambulancias asociadas al hospital
                    if (muestrAmbs.value) {
                        Row(verticalAlignment = Alignment.Top)
                        {
                            Text(
                                text = "Listado de ambulancias:",
                                textAlign = TextAlign.Center,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                        {
                            // LazyColumn de la lista de ambulancias
                            LazyColumn {
                                items(listAmbs) { ambulance ->
                                    Button(
                                        // Al clickar sobre cada botón nos abre el diálogo de edición de la ambulancia correspondiente
                                        onClick = {
                                            try {
                                                ambulancesViewModel.asignAmbFields(ambulance)
                                                viewModel.activaEditAmb()
                                                viewModel.activaEditHosp()
                                            } catch (e: Exception) {
                                                Toast.makeText(
                                                    context,
                                                    "Hubo un fallo en la operación",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            // Si la ambulancia está ocupada se muestra en rojo
                                            if (!ambulance.free)
                                                Color.Gray
                                            else
                                                Color(70, 130, 180)
                                        ),
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .padding(6.dp)
                                            .border(
                                                width = 4.dp, color = Color.Black,
                                                shape = RoundedCornerShape(
                                                    topStart = 8.dp,
                                                    topEnd = 8.dp,
                                                    bottomStart = 8.dp,
                                                    bottomEnd = 8.dp
                                                )
                                            )
                                            .sizeIn(minWidth = 150.dp, minHeight = 40.dp),
                                        shape = RoundedCornerShape(
                                            topStart = 8.dp,
                                            topEnd = 8.dp,
                                            bottomStart = 8.dp,
                                            bottomEnd = 8.dp
                                        )
                                    ) {
                                        Text(text = ambulance.plate,
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 20.sp
                                            )
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}


/**
 * Composable para la edición de ambulancias
 */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditarAmb(
    viewModel: KrankenwagenViewModel,
    ambulancesViewModel: AmbulancesViewModel
) {
    val context = LocalContext.current
    // Variables para la edición de ambulancias
    val plate by ambulancesViewModel.plate.collectAsState() // Matrícula de la ambulancia
    val isFree by ambulancesViewModel.isFree.collectAsState() // Estado de la ambulancia
    val type by ambulancesViewModel.type.collectAsState() // Tipo de ambulancia
    val hosp by ambulancesViewModel.hosp.collectAsState() // Hospital de referencia de la ambulancia
    // Estado para controlar la expansión del DropdownMenu
    var expanded by remember { mutableStateOf(false) }
    // Variable que muestra el mensaje de respuesta del sistema
    val message by ambulancesViewModel.ambulanceMessage.collectAsState()

    // Diálogo de edición de ambulancias
    Dialog(
        onDismissRequest = {
            viewModel.activaEditAmb()
            ambulancesViewModel.resetFields()
        },
    ) {
        // Tarjeta para dar forma con las esquinas redondeadas al diálogo
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            shape = RoundedCornerShape(10.dp)
        ) {
            // Columna principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    // Degradado del fondo de la pantalla
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFE0FFFF), // Light Cyan (rgb(224, 255, 255))
                                Color(0xFF87CEEB), // Light Sky Blue (rgb(135, 206, 235))
                                Color(0xFF4682B4)  // Steel Blue (rgb(70, 130, 180))
                            ),
                            start = Offset(0f, 0f),
                            end = Offset.Infinite
                        )
                    )
                    .verticalScroll(rememberScrollState(), true),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.padding(top = 8.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ambulancia),
                        contentDescription = "icono amb",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(10.dp)
                    )
                    // Campo de texto de la matrícula, no editable
                    Text(
                        text = "Matrícula -> $plate",
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hospital),
                        contentDescription = "icono hospital",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(end = 8.dp)
                    )
                    // Campo de edición para el hospital de referencia
                    TextField(
                        value = hosp,
                        onValueChange = { newValue ->
                            ambulancesViewModel.setHosp(newValue)
                        },
                        label = {
                            Text(
                                "Hospital de referencia",
                                fontSize = 20.sp
                            )
                        },
                        modifier = Modifier.padding(8.dp),
                        textStyle = TextStyle(fontSize = 30.sp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Campo de edición para la disponibilidad
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Disponible", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = isFree,
                        onCheckedChange = { newValue ->
                            ambulancesViewModel.setIsFree(newValue)
                        },
                        modifier = Modifier.padding(8.dp),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Blue, // Color del interruptor activado
                            checkedTrackColor = Color.Blue.copy(alpha = 0.5f), // Color de la pista del interruptor activado
                            uncheckedThumbColor = Color.White, // Color del interruptor desactivado
                            uncheckedTrackColor = Color.White.copy(alpha = 0.5f) // Color de la pista del interruptor desactivado
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                // Campo de edición para el tipo de ambulancia
                Row {
                    Text(
                        "Tipo de Ambulancia ->  ", fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Column(modifier = Modifier.clickable(onClick = { expanded = true })) {
                        Text(
                            text = type.name, // Mostrar el tipo de ambulancia actual
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .background(Color.White, RoundedCornerShape(2.dp)),
                            fontSize = 25.sp
                        )
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                        // Desplegable de los tipos de ambulancia
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.width(IntrinsicSize.Max)
                        ) {
                            AmbulanceTypes.entries.forEach { type ->
                                DropdownMenuItem(onClick = {
                                    ambulancesViewModel.setType(type)
                                    expanded = false
                                }) {
                                    Text(text = type.name, fontSize = 25.sp)
                                }
                            }
                        }
                    }

                }

                Spacer(modifier = Modifier.height(30.dp))

                // Botones
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    // Botón guardar ambulancia
                    Button(
                        onClick = {
                            try {
                                // Se guarda la ambulancia actual
                                ambulancesViewModel.updateAmbulance() {}
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    "Hubo un fallo en la operación",
                                    Toast.LENGTH_SHORT
                                ).show()
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
                            .sizeIn(minWidth = 150.dp, minHeight = 40.dp),
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    ) {
                        Text(
                            text = "Guardar",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))

                    // Botón borrar ambulancia
                    Button(
                        onClick = {
                            try {
                                // Se borra la ambulancia actual
                                ambulancesViewModel.deleteAmbulance() {
                                    viewModel.getAllAmb {  }
                                }
                                viewModel.activaEditAmb()
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    "Hubo un fallo en la operación",
                                    Toast.LENGTH_SHORT
                                ).show()
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
                            .sizeIn(minWidth = 150.dp, minHeight = 40.dp),
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    ) {
                        Text(
                            text = "Borrar ambulancia",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Row(horizontalArrangement = Arrangement.Center) {
                    // Botón volver
                    Button(
                        onClick = {
                            try {
                                // Se recarga la lista de ambulancias y se resetean los valores editables
                                viewModel.getAllAmb {
                                    ambulancesViewModel.resetFields()
                                    viewModel.activaEditAmb()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    "Hubo un fallo en la operación",
                                    Toast.LENGTH_SHORT
                                ).show()
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
                            .sizeIn(minWidth = 150.dp, minHeight = 40.dp),
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    ) {
                        Text(
                            text = "Volver",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Row(horizontalArrangement = Arrangement.Center) {
                    Text(
                        // Mensaje de respuesta del sistema
                        text = message,
                        fontStyle = FontStyle.Italic,
                        color = Color.Red
                    )
                }
            }
        }
    }
}

/**
 * Composable para la edición de urgencias
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditUrg(
    context: Context,
    viewModel: KrankenwagenViewModel,
    urgenciesViewModel: UrgenciesViewModel
) {
    val id by urgenciesViewModel.id.collectAsState()
    val name by urgenciesViewModel.name.collectAsState()
    val doc by urgenciesViewModel.doc.collectAsState()
    val age by urgenciesViewModel.age.collectAsState()
    val priority by urgenciesViewModel.priority.collectAsState()
    val typeOfStreet by urgenciesViewModel.typeOfStreet.collectAsState()
    val streetName by urgenciesViewModel.streetName.collectAsState()
    val streetNumber by urgenciesViewModel.streetNumber.collectAsState()
    val city by urgenciesViewModel.city.collectAsState()
    val province by urgenciesViewModel.province.collectAsState()
    val postalCode by urgenciesViewModel.postalCode.collectAsState()
    val issues by urgenciesViewModel.issues.collectAsState()
    val date by urgenciesViewModel.date.collectAsState()

    Dialog(
        onDismissRequest = {
            viewModel.activaEditUrg()
            urgenciesViewModel.resetMiUrgencia()
        }
    ) {
        // Tarjeta para dar forma con las esquinas redondeadas al diálogo
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            shape = RoundedCornerShape(10.dp)
        ) {
            // Columna principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    // Degradado del fondo de la pantalla
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFE0FFFF), // Light Cyan (rgb(224, 255, 255))
                                Color(0xFF87CEEB), // Light Sky Blue (rgb(135, 206, 235))
                                Color(0xFF4682B4)  // Steel Blue (rgb(70, 130, 180))
                            ),
                            start = Offset(0f, 0f),
                            end = Offset.Infinite
                        )
                    )
                    .verticalScroll(rememberScrollState(), true),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.urgencia),
                        contentDescription = "icono urgencia",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(end = 8.dp)
                    )
                    // Campo para la edición de la prioridad
                    TextField(
                        value = priority.toString(),
                        onValueChange = { urgenciesViewModel.setPriority(it) },
                        label = { Text("Prioridad") },
                        modifier = Modifier.weight(0.45f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    // Campo para la edición del documento de identidad
                    TextField(
                        value = doc,
                        onValueChange = { urgenciesViewModel.setDoc(it) },
                        label = { Text("Documento") },
                        modifier = Modifier.weight(0.5f)
                    )
                    Spacer(modifier = Modifier.padding(start = 40.dp))
                    Text(
                        text = "Fecha y hora: \n ${dateToString(date!!)}",
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 20.dp, top = 6.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.patient),
                        contentDescription = "icono datos",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(end = 8.dp)
                    )
                    // Campo para la edición del nombre
                    TextField(
                        value = name,
                        onValueChange = { urgenciesViewModel.setName(it) },
                        label = { Text("Nombre") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    // Campo para la edición de la edad
                    TextField(
                        value = age.toString(),
                        onValueChange = { urgenciesViewModel.setAge(it) },
                        label = { Text("Edad") },
                        modifier = Modifier.weight(0.2f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Campos de la dirección divididos
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.address),
                        contentDescription = "icono dirección",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(end = 8.dp)
                    )
                    // Campo para la edición del tipo de calle
                    TextField(
                        value = typeOfStreet,
                        onValueChange = { urgenciesViewModel.setTypeOfStreet(it) },
                        label = { Text("Tipo de vía") },
                        modifier = Modifier.weight(0.4f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    // Campo para la edición del nombre de la calle
                    TextField(
                        value = streetName,
                        onValueChange = { urgenciesViewModel.setStreetName(it) },
                        label = { Text("Nombre de la vía") },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Campo para la edición del número de la calle
                    TextField(
                        value = streetNumber,
                        onValueChange = { urgenciesViewModel.setStreetNumber(it) },
                        label = { Text("Número") },
                        modifier = Modifier.weight(0.3f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    // Campo para la edición de la ciudad
                    TextField(
                        value = city,
                        onValueChange = { urgenciesViewModel.setCity(it) },
                        label = { Text("Ciudad") },
                        modifier = Modifier.weight(0.5f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Campo para la edición de la provincia
                    TextField(
                        value = province,
                        onValueChange = { urgenciesViewModel.setProvince(it) },
                        label = { Text("Provincia") },
                        modifier = Modifier.weight(0.5f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    // Campo para la edición del código postal
                    TextField(
                        value = postalCode,
                        onValueChange = { urgenciesViewModel.setPostalCode(it) },
                        label = { Text("Código postal") },
                        modifier = Modifier.weight(0.5f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.report),
                        contentDescription = "icono clínica",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(end = 8.dp)
                    )
                    // Campo para la edición de la sintomatología del paciente
                    TextField(
                        value = issues,
                        onValueChange = { urgenciesViewModel.setIssues(it) },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Botones
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    // Botón para actualizar al urgencia
                    Button(
                        onClick = {
                            urgenciesViewModel.setAddress()
                            urgenciesViewModel.updateUrgency(
                                id,
                                onSuccess = {
                                    viewModel.getUrgencies {
                                        viewModel.increaseUpdateInfo()
                                    }
                                    Toast.makeText(
                                        context,
                                        "Urgencia actualizada correctamente",
                                        Toast.LENGTH_LONG
                                    ).show()
                                },
                                onFailure = {
                                    Toast.makeText(
                                        context,
                                        "Hubo un fallo al actualizar la urgencia",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            )
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
                            .sizeIn(minWidth = 150.dp, minHeight = 40.dp),
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    ) {
                        Text(
                            "Guardar cambios",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    // Botón para eliminar la urgencia
                    Button(
                        onClick = {
                            urgenciesViewModel.deleteUrgency(
                                id,
                                onSuccess = {
                                    viewModel.getUrgencies {
                                        viewModel.increaseUpdateInfo()
                                    }
                                    urgenciesViewModel.resetMiUrgencia()
                                    Toast.makeText(
                                        context,
                                        "Urgencia eliminada",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onFailure = {
                                    Toast.makeText(
                                        context,
                                        "Hubo un fallo al eliminar la urgencia",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
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
                            .sizeIn(minWidth = 150.dp, minHeight = 40.dp),
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    ) {
                        Text(
                            "Borrar urgencia",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                    }
                }
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