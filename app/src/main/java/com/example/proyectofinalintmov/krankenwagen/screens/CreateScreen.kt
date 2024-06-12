package com.example.proyectofinalintmov.krankenwagen.screens

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenuItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Switch
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.proyectofinalintmov.krankenwagen.data.AmbulanceTypes
import com.example.proyectofinalintmov.krankenwagen.viewModels.AmbulancesViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.HospitalViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.UrgenciesViewModel
import com.example.proyectofinalintmov.R


/**
 * Composable para la creación de ambulancias
 */
@Composable
fun CreateAmbulance(
    ambulancesViewModel: AmbulancesViewModel,
    viewModel: KrankenwagenViewModel
) {
    // Variables para la creación de ambulancias
    val idAmb by ambulancesViewModel.idAmb.collectAsState() // Id de la ambulancia
    val plate by ambulancesViewModel.plate.collectAsState() // Matrícula de la ambulancia
    val isFree by ambulancesViewModel.isFree.collectAsState() // Estado de la ambulancia
    val type by ambulancesViewModel.type.collectAsState() // Tipo de ambulancia
    val hosp by ambulancesViewModel.hosp.collectAsState() // Hospital de referencia de la ambulancia
    // Variable que se usa para controlar el DropdownMenu
    var expanded by remember { mutableStateOf(false) }
    // Variable que almacena el mensaje del sistema
    val message by ambulancesViewModel.ambulanceMessage.collectAsState()

    // Diálogo de creación de ambulancia
    Dialog(
        onDismissRequest = { viewModel.acCreateAmb() }
    ) {
        // Tarjeta para dar forma con esquinas redeondeadas a la ventana de diálogo
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Box(
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
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState(), true),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Campo de edición para el id
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ambulancia),
                            contentDescription = "icono amb",
                            modifier = Modifier
                                .size(60.dp)
                                .weight(0.2f)
                        )
                        TextField(
                            value = idAmb,
                            onValueChange = { newValue ->
                                ambulancesViewModel.setIdAmb(newValue)
                            },
                            textStyle = TextStyle(fontSize = 25.sp),
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(0.5f),
                            placeholder = { Text("Id", fontSize = 25.sp) }
                        )
                        // Campo de edición para la matrícula
                        TextField(
                            value = plate,
                            onValueChange = { newValue ->
                                ambulancesViewModel.setPlate(newValue)
                            },
                            textStyle = TextStyle(fontSize = 25.sp,
                                fontWeight = FontWeight.ExtraBold
                            ),
                            placeholder = { Text("Matrícula", fontSize = 25.sp) },
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(0.6f)
                        )
                    }
                    // Campo de edición para la disponibilidad
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Disponible", fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
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
                    // Campo de edición para el tipo de ambulancia
                    Row {
                        Text(
                            "Tipo de Ambulancia ->  ", fontSize = 25.sp,
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
                    Spacer(modifier = Modifier.padding(16.dp))
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
                            textStyle = TextStyle(fontSize = 25.sp),
                            label = { Text("Hospital de referencia", fontSize = 20.sp) },
                            modifier = Modifier
                                .padding(8.dp)
                                .sizeIn(minWidth = 300.dp, minHeight = 50.dp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(16.dp))
                    // Botones
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Botón crear ambulancia
                        Button(
                            onClick = {
                                // crea la ambulancia con los datos recibidos
                                ambulancesViewModel.saveAmbulance() {
                                    viewModel.getAllAmb {  }
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
                                "Crear", fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        // Botón borrar todos los campos
                        Button(
                            onClick = {
                                // Borra todos los campos
                                ambulancesViewModel.resetFields()
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
                                "Borrar todo", fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ){
                        // Botón cerrar el diálogo
                        Button(
                            onClick = {
                                // Cierra el diálogo
                                ambulancesViewModel.resetFields()
                                viewModel.acCreateAmb()
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
                                "Cerrar", fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black
                            )
                        }
                    }
                    // Mensaje del sistema
                    Row(horizontalArrangement = Arrangement.Center) {
                        Text(text = message)
                    }
                }
            }
        }
    }
}


/**
 * Composable para la creación de hospitales
 */
@Composable
fun CreateHospital(
    hospitalViewModel: HospitalViewModel,
    viewModel: KrankenwagenViewModel
) {
    // Variables para la creación de hospitales
    val id by hospitalViewModel.idHosp.collectAsState() // Id del hospital
    val name by hospitalViewModel.name.collectAsState() // Nombre del hospital
    val county by hospitalViewModel.county.collectAsState() // Provincia del hospital
    val city by hospitalViewModel.city.collectAsState() // Ciudad del hospital
    val address by hospitalViewModel.address.collectAsState() // Dirección del hospital
    // Variable que almacena el mensaje del sistema
    val message by hospitalViewModel.hospMessage.collectAsState()

    // Diálogo de creación de hospital
    Dialog(
        onDismissRequest = { viewModel.acCreateHosp() }
    ) {
        // Tarjeta para dar forma con esquinas redeondeadas a la ventana de diálogo
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Box(
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
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp)
                        .verticalScroll(rememberScrollState(), true),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Campo de edición para el id
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
                        TextField(
                            value = id,
                            onValueChange = { newValue ->
                                hospitalViewModel.setIdHosp(newValue)
                            },
                            textStyle = TextStyle(fontSize = 25.sp),
                            placeholder = { Text("Id", fontSize = 25.sp) },
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(0.4f)
                        )
                        // Campo de edición para el nombre
                        TextField(
                            value = name,
                            onValueChange = { newValue ->
                                hospitalViewModel.setName(newValue)
                            },
                            textStyle = TextStyle(fontSize = 25.sp),
                            placeholder = { Text("Nombre", fontSize = 25.sp) },
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.address),
                            contentDescription = "icono dirección",
                            modifier = Modifier
                                .size(60.dp)
                                .padding(end = 8.dp)
                        )
                        // Campo de edición para la provincia
                        TextField(
                            value = county,
                            onValueChange = { newValue ->
                                hospitalViewModel.setCounty(newValue)
                            },
                            textStyle = TextStyle(fontSize = 25.sp),
                            placeholder = { Text("Provincia", fontSize = 25.sp) },
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(0.5f)
                        )
                        // Campo de edición para la ciudad
                        TextField(
                            value = city,
                            onValueChange = { newValue ->
                                hospitalViewModel.setCity(newValue)
                            },
                            textStyle = TextStyle(fontSize = 25.sp),
                            placeholder = { Text("Ciudad", fontSize = 25.sp) },
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(0.5f)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(vertical = 16.dp)
                    ) {
                        // Campo de edición para la dirección
                        TextField(
                            value = address,
                            onValueChange = { newValue ->
                                hospitalViewModel.setAddress(newValue)
                            },
                            textStyle = TextStyle(fontSize = 25.sp),
                            placeholder = { Text("Dirección", fontSize = 25.sp) },
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                        )
                    }
                    // Botones
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(vertical = 16.dp)
                    ) {
                        // Botón crear hospital
                        Button(
                            onClick = {
                                // Guarda el hospital con los datos ingresados
                                hospitalViewModel.saveHospital() {}
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
                                "Crear", fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Botón borrar campos
                        Button(
                            onClick = {
                                // Borra todos los campos
                                hospitalViewModel.resetFields()

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
                                "Limpiar datos", fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Botón volver
                        Button(
                            onClick = {
                                // Cierra el diálogo
                                viewModel.acCreateHosp()
                                hospitalViewModel.resetFields()
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
                                "Cerrar", fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black
                            )
                        }
                    }
                    // Mensaje de respuesta del sistema
                    Row(horizontalArrangement = Arrangement.Center) {
                        Text(text = message)
                    }
                }
            }
        }
    }
}

/**
 * Composable para la creación de urgencias
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateUrgScreen(
    context: Context,
    viewModel: KrankenwagenViewModel,
    urgenciesViewModel: UrgenciesViewModel
) {
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

    Dialog(
        onDismissRequest = {
            viewModel.acCreateUrg()
            urgenciesViewModel.resetMiUrgencia()
        }
    ) {
        val message by urgenciesViewModel.message.collectAsState()
        // Tarjeta para dar forma con esquinas redeondeadas a la ventana de diálogo
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            shape = RoundedCornerShape(10.dp)
        ) {
            // Columna principal del diálogo
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
                        modifier = Modifier.weight(0.4f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    // Campo para la edición del documento de identidad
                    TextField(
                        value = doc,
                        onValueChange = { urgenciesViewModel.setDoc(it) },
                        label = { Text("Documento") },
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
                    // Botón para crear una urgencia
                    Button(
                        onClick = {
                            urgenciesViewModel.setAddress()
                            urgenciesViewModel.createUrg(
                                onSuccess = {
                                    viewModel.getUrgencies {
                                        viewModel.increaseUpdateInfo()
                                    }
                                    Toast.makeText(context, "Urgencia creda", Toast.LENGTH_LONG).show()
                                },
                                onFailure = {
                                    Toast.makeText(context, "Fallo al crear la urgencia", Toast.LENGTH_LONG).show()
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
                            "Crear",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    // Botón para borrar los datos
                    Button(
                        onClick = { urgenciesViewModel.resetMiUrgencia() },
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
                            "Borrar datos",
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

