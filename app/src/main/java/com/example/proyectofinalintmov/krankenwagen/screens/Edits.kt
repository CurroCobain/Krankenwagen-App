package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Switch
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.proyectofinalintmov.krankenwagen.data.AmbulanceTypes
import com.example.proyectofinalintmov.krankenwagen.viewModels.AmbulancesViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.HospitalViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import kotlin.math.min

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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .fillMaxSize()
                        .background(color = Color(225, 241, 222)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Texto id del hospital, no editable
                    Text(
                        text = "Id: $idHosp",
                        fontSize = 30.sp,
                        modifier = Modifier.background(color = Color.LightGray)
                            .sizeIn(minWidth = 260.dp, minHeight = 40.dp),
                        textAlign = TextAlign.Center
                    )
                    // Texto provincia del hospital, no editable
                    Text(
                        text = "Provincia: $county",
                        fontSize = 30.sp,
                        modifier = Modifier.background(color = Color.LightGray)
                            .sizeIn(minWidth = 260.dp, minHeight = 40.dp),
                        textAlign = TextAlign.Center
                    )
                    // Texto ciudad del hospital, no editable
                    Text(
                        text = "Ciudad: $city",
                        fontSize = 30.sp,
                        modifier = Modifier.background(color = Color.LightGray)
                            .sizeIn(minWidth = 260.dp, minHeight = 40.dp),
                        textAlign = TextAlign.Center
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
                                fontSize = 30.sp
                            )
                        },
                        modifier = Modifier.padding(8.dp),
                        textStyle = TextStyle(fontSize = 30.sp)
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
                                fontSize = 30.sp
                            )
                        },
                        modifier = Modifier.padding(8.dp),
                        textStyle = TextStyle(fontSize = 30.sp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        // Botón para actualizar el hospital
                        Button(
                            onClick = {
                                try {
                                    hospitalViewModel.updateHosp() {}
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Hubo un fallo en la operación",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            },
                            colors = ButtonDefaults.buttonColors(Color(74, 121, 66)),
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )
                        ) {
                            Text(
                                text = " Guardar ",
                                fontSize = 20.sp
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        // Botón para borrar el hospital actual
                        Button(
                            onClick = {
                                try {
                                    hospitalViewModel.deleteHosp(idHosp) {
                                        hospitalViewModel.ambulanceCoherence(idHosp)
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Hubo un fallo en la operación",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color(74, 121, 66)),
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )
                        ) {
                            Text(
                                text = " Borrar ",
                                fontSize = 20.sp
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
                            colors = ButtonDefaults.buttonColors(Color(74, 121, 66)),
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )
                        ) {
                            Text(
                                text = "Ver ambulancias",
                                fontSize = 20.sp
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
                            colors = ButtonDefaults.buttonColors(Color(74, 121, 66)),
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )
                        ) {
                            Text(
                                text = "Volver",
                                fontSize = 20.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Row(horizontalArrangement = Arrangement.Center) {
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
                    // Columna que muestra la lista de ambulancias asociadas al hospital
                    if (muestrAmbs.value) {
                        Row(verticalAlignment = Alignment.Top)
                        {
                            Text(
                                text = "Listado de ambulancias:",
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp
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
                                        modifier = Modifier.wrapContentSize(),
                                        colors = ButtonDefaults.buttonColors(
                                            // Si la ambulancia está ocupada se muestra en rojo
                                            if (!ambulance.free)
                                                Color.Red
                                            else
                                                Color(74, 121, 66)
                                        ),
                                        shape = RoundedCornerShape(
                                            topStart = 8.dp,
                                            topEnd = 8.dp,
                                            bottomStart = 8.dp,
                                            bottomEnd = 8.dp
                                        )
                                    ) {
                                        Text(text = ambulance.plate)
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(225, 241, 222)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Campo de texto de la matrícula, no editable
                Text(
                    text = "Matrícula: $plate",
                    fontSize = 30.sp,
                    modifier = Modifier.background(color = Color.LightGray)
                        .sizeIn(minWidth = 260.dp, minHeight = 40.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de edición para el hospital de referencia
                TextField(
                    value = hosp,
                    onValueChange = { newValue ->
                        ambulancesViewModel.setHosp(newValue)
                    },
                    label = {
                        Text(
                            "Hospital de referencia",
                            fontSize = 30.sp
                        )
                    },
                    modifier = Modifier.padding(8.dp),
                    textStyle = TextStyle(fontSize = 30.sp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de edición para la disponibilidad
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Disponible",
                        fontSize = 30.sp,
                        modifier = Modifier.background(color = Color.LightGray)
                            .sizeIn(minWidth = 260.dp, minHeight = 40.dp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = isFree,
                        onCheckedChange = { newValue ->
                            ambulancesViewModel.setIsFree(newValue)
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                // Campo de edición del tipo de ambulanacia
                Row {
                    Text(
                        "Tipo de Ambulancia   ",
                        fontSize = 30.sp,
                        modifier = Modifier.background(color = Color.LightGray)
                            .sizeIn(minWidth = 260.dp, minHeight = 40.dp),
                        textAlign = TextAlign.Center
                    )
                    Column(modifier = Modifier.clickable(onClick = { expanded = true })) {
                        Text(
                            text = type.name, // Mostrar el tipo de ambulancia actual
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterHorizontally),
                            fontSize = 30.sp
                        )
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                        // Desplegable que muestra las distintas opciones entre los tipos de ambulancia
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
                                    Text(
                                        text = type.name,
                                        fontSize = 30.sp
                                    )
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
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66)),
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    ) {
                        Text(
                            text = "Guardar",
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))

                    // Botón borrar ambulancia
                    Button(
                        onClick = {
                            try {
                                // Se borra la ambulancia actual
                                ambulancesViewModel.deleteAmbulance() {}
                                viewModel.activaEditAmb()
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    "Hubo un fallo en la operación",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66)),
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    ) {
                        Text(
                            text = "Borrar ambulancia",
                            fontSize = 20.sp
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
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66)),
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    ) {
                        Text(
                            text = "Volver",
                            fontSize = 20.sp
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
